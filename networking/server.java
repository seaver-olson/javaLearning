package javaLearning.networking;


//made for custom server options
public class server {
    private int port;
    private Socket clientSocket;
    private String ip;
    private String name;

    public void startServer(){
        try{
            System.out.println("Waiting for connections...");
            ServerSocket socketServer = new ServerSocket(this.port);
            clientSocket = socketServer.accept();
        }
        catch(Exception e){
            System.out.println(e);//catch errors
        }
    }
    
    /*since this server is basic and can only have one connection at a time
     closeServer() and disconnectClient() do the same thing but for abstractions sake 
     I created both*/
    public void closeServer(){
        socketServer.close();
    }
    public void disconnectClient(){
        clientSocket.close();
    }
    public server(int port, String ip, String name) {
        this.port = port;
        this.ip = ip;
        this.name = name;
        startServer(port);
    }


    //getters
    public int getPort() {
        return port;
    }
    public String getIp() {
        return ip;
    }
    public String getName() {
        return name;
    }
    //setters
    public void setPort(int port) {
        this.port = port;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setName(String name) {
        this.name = name;
    }
    //toString
    @Override
    public String toString() {
        return "server{ip='" + ip + "', name='" + name + "', port=" + port + "}";
    }

    
}
