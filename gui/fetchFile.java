import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import javax.swing.*;


public class fetchFile{
    //using username as a parameter find folder 
    public fetchFile(String username, DataOutputStream out, DataInputStream in){
        JFrame frame = new JFrame("File Fetcher");
        frame.setSize(400, 300);
        try{
            File file = new File("/Users/Admin/Desktop/Projects/data/" + username + "/");
            if (file.exists()){
                //get list of files 
                String[] files = file.list();
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
            else{
                logging log = new logging();
                log.log("Error while fetching file, could not find fetch folder", "localhost", "8080");
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        frame.setLayout(null);
        frame.getContentPane().setBackground(new java.awt.Color(230, 230, 240));
        frame.setVisible(true);
    }
}
