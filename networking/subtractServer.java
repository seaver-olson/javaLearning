import java.net.*;
import java.io.*;

//made for custom server options
public class subtractServer extends calcServer{

    @Override
    public int calculate(int a, int b){
        return a - b;
    }
    
    public subtractServer(int port) {
        super(port);
    }
    
    public static void main(String[] args){
        subtractServer s = new subtractServer(8080);
        s.startServer();
        s.closeServer();
        s.disconnectClient();
    }
}
