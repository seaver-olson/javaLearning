import java.io.*;
import java.net.*;

public class client {
    private String ip;
    private int port;
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public client(String ip, int port){
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
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public static void main(String[] args){
        client c = new client("127.0.0.1", 8080);
    }
}