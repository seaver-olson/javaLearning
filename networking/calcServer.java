import java.net.*;
import java.io.*;

//made for custom server options
public class calcServer {
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
            in = new DataInputStream(clientSocket.getInputStream());
            out.writeUTF("Welcome to the server, you are connected to the server on port " + this.port);
            //listen for messages
            while(true){
                try{
                    //if message is not "exit" keep sending messages
                    String message = in.readUTF();
                    String[] numbers = message.split(" ");
                    System.out.println("Client: " + numbers[0] + " " + numbers[1]);
                    if(message.equals("exit")){
                        out.writeUTF("Server has disconnected");
                        break;
                    }               
                    int sum = Integer.parseInt(numbers[0]) + Integer.parseInt(numbers[1]);
                    out.writeUTF("Sum: " + sum);
                }
                catch(Exception e){
                    System.out.println(e);
                    break;
                }
            }
        }
        catch(Exception e){
            System.out.println(e);//catch errors
        }
        finally{
            try{
                //close all connections
                in.close();
                out.close();
                clientSocket.close();
                socketServer.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
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

    public calcServer(int port) {
        this.port = port;
    }
    
    public static void main(String[] args){
        calcServer s = new calcServer(8080);
        s.startServer();
        s.closeServer();
        s.disconnectClient();
    }
}
