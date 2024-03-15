import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class clients {
    private String ip;
    private int port;
    private Scanner scanner;
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public clients(String ip, int port){
        this.ip = ip;
        this.port = port;
        connectToServer(ip, port);
    }
    
    public void connectToServer(String ip, int port){
        try{
            //connect to server on localhost
            clientSocket = new Socket(ip, port);
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new DataInputStream(clientSocket.getInputStream());
            chatWithServer();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public class login{
        public login(){
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
                new register();
            });

            //when login button is clicked
            LoginButton.addActionListener(e -> {
                String username = UsernameField.getText();
                String password = new String(PasswordField.getPassword());
                try{
                    out.writeUTF("loginAttempt:" + username + ":" + password);
                    
                    String reply = in.readUTF();
                    if (reply.contains("true")){
                        JOptionPane.showMessageDialog(frame, "Login Successful");
                        frame.dispose();
                        new fetchPage(username);
                    }
                    else if (reply.contains("false")){
                        JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
                        UsernameField.setText("");
                        PasswordField.setText("");
                        username = "";
                        password = "";
                    }
                    else{
                        System.out.println("Error: " + reply);
                    }

                } catch(Exception ex){
                    System.out.println(ex);
                }
            });

            frame.setLayout(null);
            frame.getContentPane().setBackground(new java.awt.Color(230, 230, 240));


            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
        
    }

    //open chat with server
    public void chatWithServer(){
        scanner = new Scanner(System.in);
        try {
            while (true) {
                String message = scanner.nextLine();
                out.writeUTF(message);
                if (message.equals("exit")) {
                    out.writeUTF("Client has disconnected");
                    out.close(); 
                    in.close(); 
                    clientSocket.close();
                    System.exit(0);
                }
                else if (message.equals("login")){
                    new login();
                }
                else if (message.equals("register")){
                    new register();
                }
                else{
                    System.out.println("Server:" + in.readUTF());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close(); 
        }
        
    }

    public static void main(String[] args){
        //update line above to have system argument for port
        clients c = new clients("127.0.0.1", Integer.parseInt(args[0]));
    }
}
