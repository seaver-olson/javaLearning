import javax.swing.*;
import java.io.*;

public class register{
    public register(){
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
            new login();
        });


        //note: I was able to make this function so much less complex by creating my own csvKit
        RegisterButton.addActionListener(e -> {
            String username = UsernameField.getText();
            String password = new String(PasswordField.getPassword());
            String file = "accounts.csv";
            csvReader reader = new csvReader(file);
            String[][] data = reader.read();
            for (int i = 0; i < reader.getColSize(); i++){
                if (username.equals(data[i][0])){
                    JOptionPane.showMessageDialog(frame, "Username already exists");
                    UsernameField.setText("");
                    PasswordField.setText("");
                    username = "";
                    password = "";
                    break;
                }
            }
            if (username.equals("") || password.equals("")){
                JOptionPane.showMessageDialog(frame, "Username or Password cannot be empty");
            }
            else{
                csvWriter writer = new csvWriter(file, true);
                String[] row = {username, password};
                writer.writeRow(row, reader.getRowSize());
                JOptionPane.showMessageDialog(frame, "Account Created, please login.");
                frame.dispose();
                new login();
            }
        });

        frame.setLayout(null);
        frame.getContentPane().setBackground(new java.awt.Color(230, 230, 240));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}