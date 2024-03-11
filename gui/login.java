import javax.swing.*;
import java.io.*;

public class login{
    public login(){
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

        JButton LoginButton = new JButton("Login");
        LoginButton.setBounds(150, 150, 100, 30);
        frame.add(LoginButton);

        JButton RegisterButton = new JButton("Register");
        RegisterButton.setBounds(250, 230, 100, 30);
        frame.add(RegisterButton);
        RegisterButton.addActionListener(e -> {
            frame.dispose();
            new register();
        });
        
        //when login button is clicked
        LoginButton.addActionListener(e -> {
            String username = UsernameField.getText();
            String password = new String(PasswordField.getPassword());
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            if (LoginAttempt(username, password)){
                //open new window
                JOptionPane.showMessageDialog(frame, "Login Successful");
                frame.dispose();
                new fetchPage(username);
            }
            else 
            {
                JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
                UsernameField.setText("");
                PasswordField.setText("");
                username = "";
                password = "";
            }
        });

        frame.setLayout(null);
        frame.getContentPane().setBackground(new java.awt.Color(230, 230, 240));


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static boolean LoginAttempt(String username, String password){
        String file = "accounts.csv";
        csvReader reader = new csvReader(file);
        String[][] data = reader.read();
        for (int i = 0; i < reader.getRowSize(); i++){
            if (data[i][0].equals(username) && data[i][1].equals(password)){
                return true;
            }
        }
        return false;
    }
    
}
