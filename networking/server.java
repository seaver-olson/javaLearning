import java.net.*;
import java.io.*;

//made for custom server options
public class server {
    private int port;//implict port
    private ServerSocket socketServer;//Server-side Socket
    private Socket clientSocket;//Client-side Socket
    private DataOutputStream out;//output stream
    private DataInputStream in;//input stream


    public void startServer(){
        try{
            System.out.println("Waiting for connections...");
            socketServer = new ServerSocket(this.port);
            clientSocket = socketServer.accept();
            //welcomes client
            out = new DataOutputStream(clientSocket.getOutputStream());
            out.writeUTF("Welcome to the server, you are connected to the server on port " + this.port);
            //closes connection after sending message
            out.writeUTF("Server Closing...");
            clientSocket.close();
            socketServer.close();
            out.close();
            System.out.println("Server Closed");
        }
        catch(Exception e){
            System.out.println(e);//catch errors
        }
    }
    
    /*since this server is basic and can only have one connection at a time
     closeServer() and disconnectClient() do the same thing but for abstractions sake 
     I created both*/
    public void closeServer(){
        try{
            socketServer.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void disconnectClient(){
        try{
            clientSocket.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public server(int port) {
        this.port = port;
    }
    
    public static void main(String[] args){
        server s = new server(8080);
        s.startServer();
        s.closeServer();
        s.disconnectClient();
    }
}
