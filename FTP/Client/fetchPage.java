import javax.swing.*;
import java.io.DataOutputStream;
import java.io.DataInputStream;

public class fetchPage{
    public fetchPage(String username, DataOutputStream out, DataInputStream in){
        JFrame frame = new JFrame(username+"'s Page");
        frame.setSize(400, 300);

        JLabel UsernameLabel = new JLabel("Welcome " + username);
        UsernameLabel.setBounds(50, 50, 200, 30);
        frame.add(UsernameLabel);

        //First Screen should have 3 buttons, fetch file, store file, transfer file
        JButton FetchButton = new JButton("Fetch File");
        FetchButton.setBounds(150, 100, 100, 30);
        frame.add(FetchButton);
        FetchButton.addActionListener(e -> {
            try{
                new fetchFile(username, out, in);
            }
            catch(Exception ex){
                System.out.println("Error: " + ex);
            }
            frame.dispose();
        });

        JButton StoreButton = new JButton("Store/Remove File from Server");
        StoreButton.setBounds(50, 150, 300, 30);
        frame.add(StoreButton);
        StoreButton.addActionListener(e -> {
            frame.dispose();
            new storeFile(username, out, in);
        });

        JButton TransferButton = new JButton("Transfer File to/from Another User");
        TransferButton.setBounds(50, 200, 300, 30);
        frame.add(TransferButton);
        TransferButton.addActionListener(e -> {
            frame.dispose();
            new transferFile(username, out, in);
        });


        frame.setLayout(null);
        frame.getContentPane().setBackground(new java.awt.Color(230, 230, 240));
        frame.setVisible(true);
    }
}