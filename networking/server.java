package javaLearning.networking;


//made for custom server options
public class server {
    private int port;
    private String ip;
    private String name;

    public server(int port, String ip, String name) {
        this.port = port;
        this.ip = ip;
        this.name = name;
	try{
		System.out.println("Waiting for connections...");
		ServerSocket socketServer = new ServerSocket(this.port);
		
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
