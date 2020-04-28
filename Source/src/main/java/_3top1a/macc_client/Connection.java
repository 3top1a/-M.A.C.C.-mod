package _3top1a.macc_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.minecraft.client.Minecraft;
import net.minecraft.profiler.Profiler;

public class Connection {
	static ServerSocket ss = null;
	static Socket socket = null;
	static Thread th = null;
	static NumberFormat formatter = null;
	static Profiler p = Minecraft.getMinecraft().mcProfiler;

	public static class MyClass implements Runnable {
		@Override
		public void run() {
			try {
				ss = new ServerSocket(6667);
			} catch (IOException e1) {
			}

			while (true) {

				try {
					socket = ss.accept();
				} catch (IOException e1) {
				}

				ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
				exec.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						try {
							SendData();
							ReceiveData();
						} catch (Exception e) {
						}
					}
				}, 0, 300, TimeUnit.MILLISECONDS);
			}
		}
	}

	public static void Connect() throws Exception {
		formatter = new DecimalFormat("#0.0");

		th = new Thread(new MyClass());
		th.start();
	}

	public static void SendData() throws Exception, IOException {
		p.startSection("Output");

		ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());

		if (Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().player == null) {
			// We are in the main menu

			dOut.writeUTF(" 101 ");
		} else {
			String x = formatter.format(Minecraft.getMinecraft().player.posX);
			String y = formatter.format(Minecraft.getMinecraft().player.posY);
			String z = formatter.format(Minecraft.getMinecraft().player.posZ);

			String hp = formatter.format(Minecraft.getMinecraft().player.getHealth());
			String maxhp = formatter.format(Minecraft.getMinecraft().player.getMaxHealth());

			String name = Minecraft.getMinecraft().player.getName();

			int dimension = Minecraft.getMinecraft().player.dimension;

			int expLevel = Minecraft.getMinecraft().player.experienceLevel;

			dOut.writeUTF(" 100 " + x + " " + y + " " + z + " " + hp + " " + maxhp + " " + name + " " + dimension + " "
					+ expLevel + " ");
		}
		dOut.flush();

		p.endSection();
	}

	public static void ReceiveData() throws Exception {
		p.startSection("Input");

		BufferedReader in = new BufferedReader(new InputStreamReader((socket.getInputStream())));
		String data;
		if ((data = in.readLine()) != null) {
			if (data.startsWith("exit")) {
				// TODO
				// Minecraft.getMinecraft().player.
			}

			// Commands starting with this prefix are for Impact client / Baritone
			//e.g. ". toggle freecam"
			if (data.startsWith(".")) {
				Minecraft.getMinecraft().player.sendChatMessage(data);
			}

			if (data.startsWith("say")) {
				// TODO
			}
		}

		p.endSection();
	}
}