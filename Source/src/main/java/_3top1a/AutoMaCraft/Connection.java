package _3top1a.AutoMaCraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.minecraft.client.Minecraft;
import net.minecraft.profiler.Profiler;

public class Connection {
	
	public static void Start() throws InterruptedException
	{
		MultiThreadedServer server = new MultiThreadedServer();
		new Thread(server).start();	
	}
}