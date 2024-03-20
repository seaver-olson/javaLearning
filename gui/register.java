import javax.swing.*;
import java.io.*;

public class register{
    public register(DataOutputStream out, DataInputStream in){
        JFrame frame = new JFrame("Java GUI Test");
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

        JButton RegisterButton = new JButton("Register");
        RegisterButton.setBounds(150, 150, 100, 30);
        frame.add(RegisterButton);

        //login page
        JButton BackButton = new JButton("Back");
        BackButton.setBounds(10, 230, 100, 30);
        frame.add(BackButton);
        BackButton.addActionListener(e -> {
            frame.dispose();
            new login(out, in);
        });


        //note: I was able to make this function so much less complex by creating my own csvKit
        RegisterButton.addActionListener(e -> {
            String username = UsernameField.getText();
            String password = new String(PasswordField.getPassword());
            try{
                out.writeUTF("registerAttempt:" + username + ":" + password);
                //time sleep for 1 second
                String reply = in.readUTF();
                reply = in.readUTF();
                if (reply.contains("true")){
                    JOptionPane.showMessageDialog(frame, "Registration Successful");
                    frame.dispose();
                    new fetchPage(username, out, in);
                }
                else if (reply.contains("Failed")){
                    if (reply.contains("Empty")){
                        JOptionPane.showMessageDialog(frame, "Username or Password cannot be empty");
                    }
                    else if (reply.contains("NotUnique")){
                        JOptionPane.showMessageDialog(frame, "Username already exists");
                    }
                    else if (reply.contains("Short")){
                        JOptionPane.showMessageDialog(frame, "Password must be at least 5 characters long");
                    }
                    else if (reply.contains("Complex")){
                        JOptionPane.showMessageDialog(frame, "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character");
                    }
                    else{
                        System.out.println("Error at Line 66: " + reply);
                    }
                }
                else{
                    System.out.println("Error at Line 70: " + reply);
                }
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        });

        frame.setLayout(null);
        frame.getContentPane().setBackground(new java.awt.Color(230, 230, 240));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}