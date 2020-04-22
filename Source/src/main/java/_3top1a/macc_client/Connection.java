package _3top1a.macc_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;

public class Connection {
	static ServerSocket ss = null;
	static Socket socket = null;
	static Thread th = null;
	static NumberFormat formatter = null;
	
	public static void run() throws Exception {
		ss = new ServerSocket(6667);
		
		while (true) {
			socket = ss.accept();

			do {
				ReceiveData();
				SendData();
			} 
			while(!ss.isClosed());
		}
	}

	public static void Connect() throws Exception {
		formatter = new DecimalFormat("#0.0");
		
		run();
	}

	public static void SendData() throws Exception, IOException {
		ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());

		String x = formatter.format(Minecraft.getMinecraft().player.posX);
		String y = formatter.format(Minecraft.getMinecraft().player.posY);
		String z = formatter.format(Minecraft.getMinecraft().player.posZ);

		String hp = formatter.format(Minecraft.getMinecraft().player.getHealth());
		String maxhp = formatter.format(Minecraft.getMinecraft().player.getMaxHealth());

		String name = Minecraft.getMinecraft().player.getName();

		int dimension = Minecraft.getMinecraft().player.dimension;

		int expLevel = Minecraft.getMinecraft().player.experienceLevel;

		dOut.writeUTF(" " + x + " " + y + " " + z + " " + hp + " " + maxhp + " " + name + " " + dimension + " "
				+ expLevel + " ");
		dOut.flush();
	}

	public static void ReceiveData() throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader((socket.getInputStream())));
		String Data;
		if ((Data = in.readLine().toString()) != null) 
		{
			System.out.println("\r\nMessage" + Data);
			
			PrintWriter writer = new PrintWriter("DEBUG.txt", "UTF-8");
			writer.println(Data);
			writer.close();
			
			
			if(regex("goto.*", Data))
			{
				System.out.println("GOING TO FUCK MYSELF");
				
				Minecraft.getMinecraft().player.sendChatMessage(".b goto 0 0 0");
			}
		}

	}
	
	public static boolean regex(String re, String to)
	{
		return Pattern.compile(re).matcher(to).matches();
	}
}