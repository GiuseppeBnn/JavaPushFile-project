import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class PushFileThread extends Thread {

    private Socket clientSocket;
    private ServerSocket serverSocket;
    private byte[] Buffer;

    public PushFileThread(ServerSocket serverS) {
        try {
            this.serverSocket= serverS;
            this.clientSocket = serverSocket.accept();

        } catch (IOException e) {e.printStackTrace();}


    }

    private void getFile(FileInputStream fileInputStream) {
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        try {
            this.Buffer = new byte[dataInputStream.available()];
            dataInputStream.readFully(this.Buffer);
            dataInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void sendFile() {
        try {
            Socket sendSocket=this.serverSocket.accept();
            DataOutputStream dataOutputStream = new DataOutputStream(sendSocket.getOutputStream());
            dataOutputStream.write(this.Buffer);
            dataOutputStream.flush();
            sendSocket.close();

        } catch (IOException e) {
            System.out.println("errore SendFile");
        }
    }

    private void sendNomeFile(String nome) {
        if (this.clientSocket.isClosed()) {
            System.out.println("SOCKET CHIUSA");
            return;
        }
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.clientSocket.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter, true);

            printWriter.println(nome);

            this.clientSocket.close();
        } catch (IOException e) {
            System.out.println("errore sendNomeFile");
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        FileInputStream fileInputStream;
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        String nomeFile;

        while (flag) {
            System.out.println("Inserisci il nome del file da pushare oppure /exit per uscire");
            nomeFile = scanner.nextLine();
            if (nomeFile.compareTo("/exit") == 0) {
                flag = false;
                continue;
            }
            try {
                fileInputStream = new FileInputStream(nomeFile);

            } catch (FileNotFoundException e) {
                System.out.println("file non presente");
                continue;
            }
            sendNomeFile(nomeFile);
            getFile(fileInputStream);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendFile();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();}



            try {
                Thread.sleep(500);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }


        }


    }
}

