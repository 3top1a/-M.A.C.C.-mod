package _3top1a.AutoMaCraft;

import net.minecraft.client.Minecraft;
import scala.tools.util.SocketServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.Objects;

public class MultiThreadedServer implements Runnable {

    private ServerSocket server;

    @Override
    public void run() {
        try {
            server = new ServerSocket(6667);

            while(true)
            {
                System.out.println("Waiting for the client request");
                //creating socket and waiting for client connection
                Socket socket = server.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                //write object to Socket
                out.println("Hi Client ");
                //close resources
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}