import java.net.Socket;
import javax.swing.JOptionPane;
import java.net.ServerSocket;
import java.io.File;

public class system {
    private String IP;
    private int port;
    private String libraryPath = "/Users/Admin/Desktop/Projects/data/";

    public system(String IP, int port){
        //if libraryPath/ does not exist exit program
        File dir = new File(libraryPath);
        if (!dir.exists()){
            JOptionPane.showMessageDialog(null, "Library Path does not exist");
            System.exit(0);
        }
        this.IP = IP;
        this.port = port;
    }
    //ports will be automatically set to 8080
    public system(){
        File dir = new File(libraryPath);
        if (!dir.exists()){
            JOptionPane.showMessageDialog(null, "Library Path does not exist");
            System.exit(0);
        }
        this.IP = "127.0.0.1";
        this.port = 8080;
    }
    
    public system(String IP){
        File dir = new File(libraryPath);
        if (!dir.exists()){
            JOptionPane.showMessageDialog(null, "Library Path does not exist");
            System.exit(0);
        }
        this.IP = IP;
        this.port = 8080;
    }
    
    public system(int port){
        File dir = new File(libraryPath);
        if (!dir.exists()){
            JOptionPane.showMessageDialog(null, "Library Path does not exist");
            System.exit(0);
        }
        this.IP = "127.0.0.1";
        this.port = port;
    }

    public void startServer(){
        try{
            ServerSocket server = new ServerSocket(this.port);
            System.out.println("Server started on " + this.IP + " on port " + this.port);
            
            while (true){
                Socket socket = server.accept();
                connectionThread connection = new connectionThread(socket);
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