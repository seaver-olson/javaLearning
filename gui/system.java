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

        public static boolean LoginAttempt(String username, String password){
            String file = "accounts.csv";
            csvReader reader = new csvReader(file);
            String[][] data = reader.read();
            for (int i = 0; i < reader.getRowSize(); i++){
                if (data[i][0].equals(username) && data[i][1].equals(password)){
                    return true;
                }
            }
            return false;
        }

        public void run(){
            try{
                System.out.println("Connected to " + socket.getInetAddress() + " on port " + socket.getPort());
                out = new DataOutputStream(this.socket.getOutputStream());
                in = new DataInputStream(this.socket.getInputStream());
                while (true){
                    String message = in.readUTF();
                    //command line input
                    System.out.println("Client[" + socket.getInetAddress() + " : " + socket.getPort() +"]: " + message);
                    if (message.equals("exit")){
                        break;
                    }
                    if (message.equals("login")){
                        out.writeUTF("generating login page");
                    }
                    if (message.equals("register")){
                        out.writeUTF("generating register page");
                    }
                    if (message.contains("loginAttempt")){
                        String[] loginInfo = message.split(":");
                        String username = loginInfo[1];
                        String password = loginInfo[2];
                        System.out.println(username);
                        System.out.println(password);
                        if (LoginAttempt(username, password)){
                            System.out.println("Successful Login Attempt from " + socket.getInetAddress() + " on port " + socket.getPort() + " to account "  + username);
                            out.writeUTF("true");
                        }
                        else{
                            out.writeUTF("false");
                        }
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
        try{
            ServerSocket server = new ServerSocket(this.port);
            System.out.println("Server started on " + this.IP + " on port " + this.port);
            while (true){
                Socket socket = server.accept();
                ConnectionThread connection = new ConnectionThread(socket);
                connection.start();
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