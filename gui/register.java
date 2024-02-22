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

        //when login button is clicked
        RegisterButton.addActionListener(e -> {
            String username = UsernameField.getText();
            String password = new String(PasswordField.getPassword());
            //search csv username row, if username exists clear fields and tell user to try again
            //check username from accounts.csv first column using buffered reader
            String[] UsernameList = new String[100];//NOTE: Change this when you release project to account for more accounts
            try (BufferedReader br = new BufferedReader(new FileReader("accounts.csv"))) {
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    UsernameList[i] = values[0];
                    i++;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            for (int i = 0; i < UsernameList.length; i++){
                if (username.equals(UsernameList[i])){
                    JOptionPane.showMessageDialog(frame, "Username already exists");
                    UsernameField.setText("");
                    PasswordField.setText("");
                    username = "";
                    password = "";
                    break;
                }
                else{
                    //create new row in accounts.csv
                    //write username and password to accounts.csv
                    //open new window
                    try {
                        FileWriter writer = new FileWriter("accounts.csv", true);
                        writer.write(username + "," + password + "\n");
                        writer.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(frame, "Account Created");
                    frame.dispose();
                    new fetchPage(username);

                }
            }
        });

        frame.setLayout(null);
        frame.getContentPane().setBackground(new java.awt.Color(230, 230, 240));


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static boolean LoginAttempt(String username, String password){
        if (username.equals("admin") && password.equals("admin")){
            System.out.println("Login Successful");
            return true;
        }
        else{
            System.out.println("Login Failed");
            return false;
        }
    }
}