import javax.swing.*;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.io.File;

public class storeFile {
    public storeFile(String username, DataOutputStream out, DataInputStream in){
        JFrame frame = new JFrame("File Storer");
        frame.setSize(400, 300);

        JLabel fileLabel = new JLabel("File Name");
        fileLabel.setBounds(50, 50, 100, 30);
        frame.add(fileLabel);

        JTextField fileField = new JTextField();
        fileField.setBounds(150, 50, 200, 30);
        frame.add(fileField);

        JButton storeButton = new JButton("Store");
        storeButton.setBounds(150, 150, 100, 30);
        frame.add(storeButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(250, 250, 100, 30);
        frame.add(backButton);

        //when store button is clicked
        storeButton.addActionListener(e -> {
            String fileName = fileField.getText();
            File file = new File(fileName);
            if (!file.exists()){
                JOptionPane.showMessageDialog(frame, "File does not exist");
                return;
            }
            try{
                out.writeUTF("StoreFile:" + username + ":" + fileName);
                byte[] fileContent = Files.readAllBytes(file.toPath());
                out.write(fileContent);
                JOptionPane.showMessageDialog(frame, "File Stored");
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new fetchPage(username, out, in);
        });

        frame.setLayout(null);
        frame.setVisible(true);
    }
}
