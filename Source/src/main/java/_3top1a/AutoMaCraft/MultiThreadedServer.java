package _3top1a.AutoMaCraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.NumberFormat;

import net.minecraft.client.Minecraft;

public class MultiThreadedServer implements Runnable {

	private ServerSocket ss;
	NumberFormat formatter = null;

	@Override
	public void run() {
		formatter = new java.text.DecimalFormat("#0.0");
		while (true) {
			try {
				ss = new ServerSocket(6667);
			} catch (IOException ioe) {
				System.out.println("Can't connect to port.");
			}

			Socket sock = null;
			try {
				sock = ss.accept();
			} catch (IOException ioe) {
				System.out.println("Can't connect to client.");
			}
			System.out.println("Connection successful.");

			PrintWriter out = null;
			BufferedReader in = null;

			try {
				out = new PrintWriter(sock.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			} catch (Exception e) {
				System.out.println("Error.");
			}

			String line;
			String lines = "";

			while (true) {
				try {
					// SEND

					/*
					 * 1xx - SENDING
					 * 
					 * 200 OK AFTER COMMAND
					 * 
					 */

					if (Minecraft.getMinecraft().player == null) {
						out.println(" 101 ");
					} else {
						String x = formatter.format(Minecraft.getMinecraft().player.posX);
						String y = formatter.format(Minecraft.getMinecraft().player.posY);
						String z = formatter.format(Minecraft.getMinecraft().player.posZ);

						String hp = formatter.format(Minecraft.getMinecraft().player.getHealth());
						String maxhp = formatter.format(Minecraft.getMinecraft().player.getMaxHealth());

						String name = Minecraft.getMinecraft().player.getName();

						int dimension = Minecraft.getMinecraft().player.dimension;

						int expLevel = Minecraft.getMinecraft().player.experienceLevel;

						out.println(" 100 " + x + " " + y + " " + z + " " + hp + " " + maxhp + " " + name + " "
								+ dimension + " " + expLevel + " ");

					}

					// RECIEVE

					String data;
					while (true) {
						data = in.readLine();
						if (data != null) {
							if (data.startsWith("110")) {
								break;
							}
							if (data.startsWith(".")) {
								Minecraft.getMinecraft().player.sendChatMessage(data);
							}
							break;
						}
					}
				} catch (Exception e) {
				}

				if (ss.isClosed()) {
					break;
				}

			}
			try {
				in.close();
				out.close();
				sock.close();
			} catch (IOException ioe) {
				System.out.println("Error closing the socket");
			}
		}

	}

}
