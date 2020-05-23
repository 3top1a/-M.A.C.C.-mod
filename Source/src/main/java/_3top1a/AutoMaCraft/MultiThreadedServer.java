package _3top1a.AutoMaCraft;

import org.apache.http.conn.socket.ConnectionSocketFactory;

import java.io.*;
import java.net.*;

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

                while (!out.checkError() ) {
                    System.out.println("That fagget still didn't disconnect!");
                    out.println("You fagget still didn't disconnect!");
                    //in.readLine();
                    Thread.sleep(500);
                }

                //Close
                in.close();
                out.close();
                socket.close();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}