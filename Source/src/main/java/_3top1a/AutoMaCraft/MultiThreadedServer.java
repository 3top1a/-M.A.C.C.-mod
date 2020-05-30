package _3top1a.AutoMaCraft;

import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MultiThreadedServer implements Runnable {
    NumberFormat formatter = null;
    private ServerSocket server;

    @Override
    public void run() {
        formatter = new java.text.DecimalFormat("#0.0");
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
                    108 - S - (Response to 106) We're in the main menu retard
                    109 - S - We just executed the command with {x} prefix (110, etc..)
                    110 - C - Send this in the local chat you pillock

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
                    if (data != null) {
                        //Remove tailing whitespaces
                        String regex = "^\\s+";
                        data = data.replaceAll(regex, "");

                        //Parse the data
                        if (data.startsWith("106")) {
                            //Send the data
                            sendPlayerData(out);
                        }
                        else if (data.startsWith("110"))
                        {
                            //Remove the 109 part
                            String msg = data.substring(4);
                            System.out.println(msg);

                            //Send this in the local chat
                            Minecraft.getMinecraft().player.sendChatMessage(msg);

                            //Send 109
                            out.println(" 110 109");
                        }
                        else
                        {
                            System.out.println("Some other data: " + data);
                        }
                    }
                }

                //Close
                executor.shutdown();
                in.close();
                out.close();
                socket.close();
            }
        } catch (Exception e) {
            try {
                Utils.PrintError(e);
            } catch (Exception exception) {
                e.printStackTrace();
            }
        }
    }

    void sendPlayerData(PrintWriter out) {
        if (Minecraft.getMinecraft().player == null) {
            out.println(" 108 ");
        } else {
            String x = formatter.format(Minecraft.getMinecraft().player.posX);
            String y = formatter.format(Minecraft.getMinecraft().player.posY);
            String z = formatter.format(Minecraft.getMinecraft().player.posZ);

            String hp = formatter.format(Minecraft.getMinecraft().player.getHealth());
            String maxHp = formatter.format(Minecraft.getMinecraft().player.getMaxHealth());

            String name = Minecraft.getMinecraft().player.getName();

            int dimension = Minecraft.getMinecraft().player.dimension;

            int expLevel = Minecraft.getMinecraft().player.experienceLevel;

            out.println(" 107 " + x + " " + y + " " + z + " " + hp + " " + maxHp + " " + name + " "
                    + dimension + " " + expLevel + " ");
        }
    }
}