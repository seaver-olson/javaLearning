import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class login{
    public login(DataOutputStream out, DataInputStream in){
        JFrame frame = new JFrame("Login Page");
        frame.setSize(400, 300);

        JLabel UsernameLabel = new JLabel("Username");
        UsernameLabel.setBounds(50, 50, 100, 30);
        frame.add(UsernameLabel);

        JTextField UsernameField = new JTextField();
        UsernameField.setBounds(150, 50, 200, 30);
        frame.add(UsernameField);

        JLabel PasswordLabel = new JLabel("Password");
        PasswordLabel.setBounds(50, 100, 100, 30);
        frame.add(PasswordLabel);

        JPasswordField PasswordField = new JPasswordField();
        PasswordField.setBounds(150, 100, 200, 30);
        frame.add(PasswordField);

        JButton LoginButton = new JButton("Login");
        LoginButton.setBounds(150, 150, 100, 30);
        frame.add(LoginButton);

        JButton RegisterButton = new JButton("Register");
        RegisterButton.setBounds(250, 230, 100, 30);
        frame.add(RegisterButton);
        RegisterButton.addActionListener(e -> {
            frame.dispose();
            new register(out, in);
        });

        //when login button is clicked
        LoginButton.addActionListener(e -> {
            String username = UsernameField.getText();
            String password = new String(PasswordField.getPassword());
            try{
                out.writeUTF("loginAttempt:" + username + ":" + password);
                
                String reply = in.readUTF();
                reply = in.readUTF();
                if (reply.contains("LOGIN:SUCCESS")){
                    JOptionPane.showMessageDialog(frame, "Login Successful");
                    if (reply.contains("transferRequest")){
                        String[] parts = reply.split(":");
                        String toUser = parts[3];
                        String fileName = parts[4];
                        int dialogResult = JOptionPane.showConfirmDialog(frame, "User " + toUser + " wants to transfer file " + fileName + " to you. Do you accept?");
                        if (dialogResult == JOptionPane.YES_OPTION){
                            out.writeUTF("ConfirmTransfer:" + username + ":" + toUser + ":" + fileName);
                        }
                        else{
                            out.writeUTF("DenyTransfer:" + username + ":" + toUser + ":" + fileName);
                        }
                    }
                    frame.dispose();
                    new fetchPage(username, out, in);
                }
                else if (reply.contains("LOGIN:FAILED")){
                    JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
                    UsernameField.setText("");
                    PasswordField.setText("");
                    username = "";
                    password = "";
                }
                else{
                    System.out.println("Error during Login Attempt: " + reply);
                }

            } catch(Exception ex){
                System.out.println("Error: " + ex);
            }
        });

        frame.setLayout(null);
        frame.getContentPane().setBackground(new java.awt.Color(230, 230, 240));


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}