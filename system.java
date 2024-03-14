import java.net.Socket;
import java.net.ServerSocket;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class system {
    private String IP;
    private int port;

    public system(String IP, int port){
        this.IP = IP;
        this.port = port;
    }
    //ports will be automatically set to 8080
    public system(){
        this.IP = "127.0.0.1";
        this.port = 8080;
    }
    
    public system(String IP){
        this.IP = IP;
        this.port = 8080;
    }
    
    public system(int port){
        this.IP = "127.0.0.1";
        this.port = port;
    }
    //create sub class for multiple connections to the same IP
    public class ConnectionThread extends Thread{
        private Socket socket;
        private boolean readyToDie = false;
        private DataOutputStream out;//output stream
        private DataInputStream in;//input stream

        public ConnectionThread(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try{
                System.out.println("Connected to " + socket.getInetAddress() + " on port " + socket.getPort());
                out = new DataOutputStream(this.socket.getOutputStream());
                in = new DataInputStream(this.socket.getInputStream());
                while (true){
                    out.writeUTF("--------------");
                    String message = in.readUTF();
                    //command line input
                    System.out.println("Client[" + socket.getInetAddress() + " : " + socket.getPort() +"]: " + message);
                    if (message == "exit"){
                        break;
                    }
                }
                System.out.println("Closing connection");
                this.socket.close();
                //set this oject to null
                readyToDie = true;



            } catch (Exception e){
                System.out.println("Error: " + e);
                
            }
        }
        public boolean isReadyToDie(){
            return readyToDie;
        }
    }
    public void startServer(){
        ConnectionThread[] threads = new ConnectionThread[10];
        //listen for connections if connection found pass it to a new thread
        try{
            while (true){
                    for (int i = 0; i < threads.length; i++){
                        if (threads[i] == null || threads[i].isReadyToDie()){
                            System.out.println("Thread " + i + " is ready to serve");
                            int newPort = port + i;
                            ServerSocket server = new ServerSocket(newPort);
                            threads[i] = new ConnectionThread(server.accept());
                            threads[i].start();
                        }
                        
                    }
                }
        } catch (Exception e){
            System.out.println("Error: " + e);
        }

    }
    //getters, setters, and toString
    public void setIP(String IP){
        this.IP = IP;
    }
    public void setPort(int port){
        this.port = port;
    }
    public String getIP(){
        return this.IP;
    }
    public int getPort(){
        return this.port;
    }

    public String toString(){
        return "IP: " + this.IP + " Port: " + this.port;
    }

    public static void main(String[] args){
        system n = new system();
        if (args.length < 2){
            if (!(args.length < 1)){
                try {
                    n = new system(Integer.parseInt(args[0]));
                } catch (NumberFormatException nfe) {
                    n = new system(args[0]);
            }
            }
        }   
        else{
            n = new system(args[0], Integer.parseInt(args[1]));
        }
        n.startServer();
    }
}
