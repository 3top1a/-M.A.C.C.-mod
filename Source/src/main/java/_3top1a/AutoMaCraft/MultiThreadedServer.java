package _3top1a.AutoMaCraft;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MultiThreadedServer implements Runnable {

    private ServerSocket server;

    @Override
    public void run() {
        try {
            server = new ServerSocket(6667);

            while (true) {
                //Prepare
                Socket socket = server.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //Run

                /* To communicate we use a bunch of prefixes
                    pf. val | Who sends it (we are the server) | a description

                    105 - S - Just testing the connection se that we can insult each other.
                    105 - S - Sent every 2 seconds since out.checkError() can only return true if it sends data
                    106 - C - Send me the data
                    107 - S - (Response to 106) Here's the data you faggot


                 */

                //This is the task that sends 105 every 2 seconds
                Runnable heartbeatRun = new Runnable() {
                    public void run() {
                        out.println(" 105 ");
                    }
                };
                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                executor.scheduleAtFixedRate(heartbeatRun, 0, 2, TimeUnit.SECONDS);

                while (!out.checkError()) {
                    //Here will be the main system that responds to messages
                    String data = null;
                    data = in.readLine();
                    if(data != null) {
                        System.out.println(data);
                    }
                }

                //Close
                executor.shutdown();
                in.close();
                out.close();
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}