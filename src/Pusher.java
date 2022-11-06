import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Pusher{
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket clientSocket;
        Thread sendRequesThread;
        try {
            serverSocket=new ServerSocket(6161);
            clientSocket = serverSocket.accept();
            sendRequesThread=new Thread(new PushFileThread(clientSocket));
            sendRequesThread.start();
        } catch (IOException e) {e.printStackTrace();}






    }
}