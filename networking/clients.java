import java.io.*;
import java.net.*;
import java.util.Scanner;

public class clients {
    private String ip;
    private int port;
    private Scanner scanner;
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public clients(String ip, int port){
        this.ip = ip;
        this.port = port;
        connectToServer(ip, port);
    }
    
    public void connectToServer(String ip, int port){
        try{
            //connect to server on localhost
            clientSocket = new Socket(ip, port);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            //print server message
            System.out.println(in.readUTF());
            chatWithServer();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    //open chat with server
    public void chatWithServer(){
        scanner = new Scanner(System.in);
        //if message is not "exit" keep sending messages
        try {
            while (true) {
                String message = scanner.nextLine();
                out.writeUTF(message);
                if (message.equals("exit")) {
                    out.writeUTF("Client has disconnected");
                    out.close(); // Close the output stream
                    break;
                }
                System.out.println(in.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close(); 
        }
        
    }

    public static void main(String[] args){
        //update line above to have system argument for port
        clients c = new clients("127.0.0.1", Integer.parseInt(args[0]));
    }
}
