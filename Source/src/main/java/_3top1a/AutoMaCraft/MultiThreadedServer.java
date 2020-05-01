package _3top1a.AutoMaCraft;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.Scanner;
import javax.swing.text.NumberFormatter;

import net.minecraft.client.Minecraft;

public class MultiThreadedServer implements Runnable{

    private ServerSocket ss;
    NumberFormat formatter = null;

	public void run() {
		formatter = new java.text.DecimalFormat("#0.0");
		while(true)
		{
			try
		    {
		        ss= new ServerSocket(6667);
		    }
		    catch(IOException ioe){System.out.println("Can't connect to port.");}
	
		    Socket sock = null;
		    try{sock = ss.accept();}
		    catch(IOException ioe){System.out.println("Can't connect to client.");}
		    System.out.println("Connection successful.");
	
		    PrintWriter out = null;
		    Scanner in = null;
	
		    try{
		        out = new PrintWriter(sock.getOutputStream(),true);
		        in = new Scanner(sock.getInputStream());
		    }catch(Exception e)
		    {
		        System.out.println("Error.");
		    }
	
		    String line;
		    String lines = "";
	
		    while(true)
		    {
		    	try {
			    	Thread.sleep(100);
			    	
			    	//SEND
			    	
			    	if (Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().player == null) {
			    		out.println(" 101 ");
			    	}
			    	else
			    	{
			    		String x = formatter.format(Minecraft.getMinecraft().player.posX);
						String y = formatter.format(Minecraft.getMinecraft().player.posY);
						String z = formatter.format(Minecraft.getMinecraft().player.posZ);

						String hp = formatter.format(Minecraft.getMinecraft().player.getHealth());
						String maxhp = formatter.format(Minecraft.getMinecraft().player.getMaxHealth());

						String name = Minecraft.getMinecraft().player.getName();

						int dimension = Minecraft.getMinecraft().player.dimension;

						int expLevel = Minecraft.getMinecraft().player.experienceLevel;
						
						out.println(" 100 " + x + " " + y + " " + z + " " + hp + " " + maxhp + " " + name + " " + dimension + " "
								+ expLevel + " ");

			    	}
			    	
			    	//RECIEVE
		    	}
				catch(Exception e) {}
		    	
		    	if(ss.isClosed())
		    	{
		    		break;
		    	}
		        
		    }
		    try{
	            in.close();
	            out.close();
	            sock.close();
	            ss.close();
	        }catch(IOException ioe){}
		}
        
    }
	
}
