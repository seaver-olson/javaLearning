import javax.swing.*;
import java.io.DataOutputStream;
import java.io.DataInputStream;

public class fetchPage{
    public fetchPage(String username, DataOutputStream out, DataInputStream in){
        logging log = new logging();
        JFrame frame = new JFrame(username+"'s Page");
        frame.setSize(400, 300);

        JLabel UsernameLabel = new JLabel("Welcome " + username);
        UsernameLabel.setBounds(50, 50, 100, 30);
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
                log.log("Error while fetching file", "localhost", "8080");
            }
            frame.dispose();
        });

        JButton StoreButton = new JButton("Store File");
        StoreButton.setBounds(150, 150, 100, 30);
        frame.add(StoreButton);
        StoreButton.addActionListener(e -> {
            frame.dispose();
            //new storeFile(username);
        });

        JButton TransferButton = new JButton("Transfer File");
        TransferButton.setBounds(150, 200, 100, 30);
        frame.add(TransferButton);
        TransferButton.addActionListener(e -> {
            frame.dispose();
            //new transferFile(username);
        });


        frame.setLayout(null);
        frame.getContentPane().setBackground(new java.awt.Color(230, 230, 240));
        frame.setVisible(true);
    }
}