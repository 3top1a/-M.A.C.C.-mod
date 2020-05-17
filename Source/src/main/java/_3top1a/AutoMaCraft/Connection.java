package _3top1a.AutoMaCraft;

public class Connection {

    public static void Start() throws InterruptedException {
        MultiThreadedServer server = new MultiThreadedServer();
        new Thread(server).start();
    }
}