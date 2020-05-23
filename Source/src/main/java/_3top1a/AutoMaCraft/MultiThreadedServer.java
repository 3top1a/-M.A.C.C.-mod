package _3top1a.AutoMaCraft;

import org.apache.http.conn.socket.ConnectionSocketFactory;

import java.io.*;
import java.net.*;
import java.lang.Object.*;

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
                    104 - Is this connection still alive?
                    105 - Im's still alive you retard
                    106 - Send me the data
                    107 - Here's the data you faggot


                 */

                /*while (true) {

                    in.readLine();
                }*/

                //Close
                in.close();
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}