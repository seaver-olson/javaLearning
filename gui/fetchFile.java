import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import javax.swing.*;


public class fetchFile{
    //using username as a parameter find folder 
    public fetchFile(String username, DataOutputStream out, DataInputStream in){
        File file = new File("/Users/Admin/Desktop/Projects/data/" + username);
    }
}
