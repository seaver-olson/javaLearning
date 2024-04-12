import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import javax.swing.*;
import java.io.FileOutputStream;


public class fetchFile{
    //using username as a parameter find folder 
    public fetchFile(String username, DataOutputStream out, DataInputStream in){
        JFrame frame = new JFrame("File Fetcher");
        frame.setSize(400, 300);
        try{
            out.writeUTF("FetchDir:" + username);
            String fileList = in.readUTF();
            if (fileList.contains("Directory:")){
                String[] files = fileList.split(":")[1].split(",");
                JList<String> list = new JList<String>(files);
                list.setBounds(50, 50, 300, 200);
                frame.add(list);

                JButton fetchButton = new JButton("Fetch");
                fetchButton.setBounds(150, 250, 100, 30);
                frame.add(fetchButton);

                JButton backButton = new JButton("Back");
                backButton.setBounds(250, 250, 100, 30);
                frame.add(backButton);

                backButton.addActionListener(e -> {
                    frame.dispose();
                    new fetchPage(username, out, in);
                });

                //when fetch button is clicked
                fetchButton.addActionListener(e -> {
                    String selectedFile = list.getSelectedValue();
                    try{
                        out.writeUTF("FetchFile:" + username + ":" + selectedFile);
                        byte[] fileData = new byte[1024];
                        int bytesRead = in.read(fileData);
                        File file = new File(selectedFile);
                        file.createNewFile();
                        FileOutputStream fileOut = new FileOutputStream(file);
                        fileOut.write(fileData, 0, bytesRead);
                        fileOut.close();
                        JOptionPane.showMessageDialog(frame, "File Fetched");
                    }
                    catch(Exception ex){
                        System.out.println(ex);
                    }
                });
            }
            else{
                JLabel noFiles = new JLabel("No Files Found");
                noFiles.setBounds(150, 150, 100, 30);
                frame.add(noFiles);

                JButton backButton = new JButton("Back");
                backButton.setBounds(250, 250, 100, 30);
                frame.add(backButton);
                backButton.addActionListener(e -> {
                    frame.dispose();
                    new fetchPage(username, out, in);
                });
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
