package _3top1a.macc_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.lwjgl.Sys;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Connection {
    static ServerSocket ss = null;
    static Socket socket = null;
    static Thread th = null;
    static NumberFormat formatter = null;
    
    public static class MyClass implements Runnable {
		public void run(){
			try {
				ss = new ServerSocket(6667);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			while(true)
			{

		    	try {
					socket = ss.accept();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					break;
				}

		    	
				ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		    	exec.scheduleAtFixedRate(new Runnable() {
		    	  @Override
		    	  public void run() {
		    	    try {	    	    	
						SendData();
					}
		    	    catch (Exception e) {}
		    	  }
		    	}, 0, (1000/15), TimeUnit.MILLISECONDS);
			}
		} 
	}
    
    public static void Connect() throws Exception {
    	formatter = new DecimalFormat("#0.0"); 
    	
    	th = new Thread(new MyClass ());
    	th.start();
    }
    
    public static void SendData() throws Exception, IOException
    {
    	ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
    	
    	
        String x = formatter.format(Minecraft.getMinecraft().player.posX);
        String y = formatter.format(Minecraft.getMinecraft().player.posY);
        String z = formatter.format(Minecraft.getMinecraft().player.posZ);
        
        String hp =  formatter.format(Minecraft.getMinecraft().player.getHealth());
        String maxhp = formatter.format(Minecraft.getMinecraft().player.getMaxHealth());
        
        String name = Minecraft.getMinecraft().player.getName();        
              
        int dimension = Minecraft.getMinecraft().player.dimension;
        
        int expLevel = Minecraft.getMinecraft().player.experienceLevel;
        
		dOut.writeUTF(" " + x + " " + y + " " + z + " " + hp + " " + maxhp + " " + name + " " + dimension + " " + expLevel + " ");
        dOut.flush();
    }
}