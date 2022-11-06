import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class PushFileThread extends Thread {

    private final Socket clientSocket;
    private byte[] Buffer;

    public PushFileThread(Socket socket) {
        this.clientSocket = socket;

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
            DataOutputStream dataOutputStream = new DataOutputStream(this.clientSocket.getOutputStream());
            dataOutputStream.write(this.Buffer);
            dataOutputStream.flush();
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

            getFile(fileInputStream);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendFile();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();}

            sendNomeFile(nomeFile);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }

        }


    }
}

