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
        } catch (IOException e) {System.out.println("errore recvStringFromSocket");return null;}
    }


    public static void main(String[] args){

        InetAddress pusherAddress;
        if(!(new File("FileRicevuti").isDirectory())){
            new File("FileRicevuti").mkdir();
        }

        try {
            pusherAddress= InetAddress.getByName("192.168.1.221");

            while(true) {
                Socket pusherSocket= new Socket(pusherAddress,6060);
                String nome= recvStringFromSocket(pusherSocket);
                System.out.println("sto ricevendo: "+nome );
                pusherSocket.close();
                Socket dataSocket=new Socket(pusherAddress,6060);

                DataInputStream dataInputStream = new DataInputStream(dataSocket.getInputStream());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(dataInputStream);
                FileOutputStream fileOutputStream=new FileOutputStream("./FileRicevuti/"+nome);


                fileOutputStream.write(bufferedInputStream.readAllBytes());
                fileOutputStream.close();
                System.out.println("ricevuto "+nome);
                dataSocket.close();

            }
        }catch (UnknownHostException e) {e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();System.exit(2);}




    }
}
