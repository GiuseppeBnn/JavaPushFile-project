import javax.xml.crypto.Data;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Receiver {


    private static String recvStringFromSocket(Socket socket){
        try {
            InputStreamReader inputStreamReader=new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String stringa=bufferedReader.readLine();
            return stringa;
        } catch (IOException e) {System.out.println("errore recvStringFromScoket");return null;}
    }


    public static void main(String[] args){
        new File("FileRicevuti").mkdir();
        InetAddress pusherAddress;
        try {
            pusherAddress= InetAddress.getByName(args[0]);
            Socket pusherSocket= new Socket(pusherAddress,6060);
            while(true) {
                DataInputStream dataInputStream = new DataInputStream(pusherSocket.getInputStream());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(dataInputStream);
                byte[] buffer=bufferedInputStream.readAllBytes();

                String nome= recvStringFromSocket(pusherSocket);


                FileOutputStream fileOutputStream=new FileOutputStream("/FileRicevuti/"+nome);
                fileOutputStream.write(buffer);
                fileOutputStream.close();


            }
        }catch (UnknownHostException e) {e.printStackTrace();
        }catch (IOException e) {System.exit(2);}




    }
}
