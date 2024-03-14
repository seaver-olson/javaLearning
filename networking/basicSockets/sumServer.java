import java.net.*;
import java.io.*;

//made for custom server options
public class sumServer extends calcServer {

    public sumServer(int port) {
        super(port);
    }
    
    public static void main(String[] args){
        sumServer s = new sumServer(8080);
        s.startServer();
        s.closeServer();
        s.disconnectClient();
    }
}
