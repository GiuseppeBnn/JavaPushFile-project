import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Pusher{
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(6060);


        Thread sendRequesThread;
        while(true) {

            sendRequesThread = new Thread(new PushFileThread(serverSocket));
            sendRequesThread.start();

            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        } catch (IOException e) {e.printStackTrace();}



    }
}