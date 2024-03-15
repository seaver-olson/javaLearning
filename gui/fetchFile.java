import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import javax.swing.*;


public class fetchFile{
    //using username as a parameter find folder 
    public fetchFile(String username, DataOutputStream out, DataInputStream in){
        File file = new File("/Users/Admin/Desktop/Projects/data/" + username + "/");
        if (file.exists()){
            //get list of files 
            String[] files = file.list();
            //create a new frame
            JFrame frame = new JFrame("File Fetcher");
            frame.setSize(400, 300);
            //create a new list
            JList<String> list = new JList<String>(files);
            list.setBounds(50, 50, 300, 200);
            frame.add(list);
            //create a new button
            JButton fetchButton = new JButton("Fetch");
            fetchButton.setBounds(150, 250, 100, 30);
            frame.add(fetchButton);
            //when fetch button is clicked
            fetchButton.addActionListener(e -> {
                String selectedFile = list.getSelectedValue();
                try{
                    out.writeUTF("fetchFile:" + username + ":" + selectedFile);
                    String reply = in.readUTF();
                    if (reply.contains("true")){
                        JOptionPane.showMessageDialog(frame, "File Fetched");
                    }
                    else if (reply.contains("false")){
                        JOptionPane.showMessageDialog(frame, "File Fetch Failed");
                    }
                    else{
                        System.out.println("Error: " + reply);
                    }
                }
                catch(Exception ex){
                    System.out.println(ex);
                }
            });
        }
    }
}
