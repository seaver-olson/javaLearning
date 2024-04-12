import java.io.*;
import javax.swing.*;


public class transferFile {
    public transferFile(String username, DataOutputStream out, DataInputStream in){
        JFrame frame = new JFrame("File Transfer");
        frame.setSize(400, 400);
        try{
            out.writeUTF("FetchDir:" + username);
            String fileList = in.readUTF();
            if (fileList.contains("Directory:")){
                String[] files = fileList.split(":")[1].split(",");
                JList<String> list = new JList<String>(files);
                list.setBounds(50, 50, 300, 200);
                frame.add(list);

                JLabel userLabel = new JLabel("User to Transfer to");
                userLabel.setBounds(50, 250, 150, 30);
                frame.add(userLabel);

                JTextField userField = new JTextField();
                userField.setBounds(200, 250, 150, 30);
                frame.add(userField);

                JButton transferButton = new JButton("Transfer");
                transferButton.setBounds(150, 300, 100, 30);
                frame.add(transferButton);

                JButton backButton = new JButton("Back");
                backButton.setBounds(250, 300, 100, 30);
                frame.add(backButton);

                backButton.addActionListener(e -> {
                    frame.dispose();
                    new fetchPage(username, out, in);
                });

                transferButton.addActionListener(e -> {
                    String toUser = userField.getText();
                    String fileName = list.getSelectedValue();
                    if (toUser.equals("") || toUser == null || fileName.equals("") || fileName == null){
                        throw new IllegalArgumentException("Username or File Name cannot be empty");
                    }
                    try{
                        out.writeUTF("TransferFile:" + username + ":" + toUser + ":" + fileName);
                        String response = in.readUTF();
                        if (response.contains("true")){
                            JOptionPane.showMessageDialog(frame, "Next time user is online file will may be approved");
                        }
                        else{
                            JOptionPane.showMessageDialog(frame, "Error while transferring file");
                        }
                    }
                    catch(Exception ex){
                        System.out.println(ex);
                    }
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
