import java.io.*;
import java.net.*;

public class connectionThread extends Thread{
    private Socket socket;
    private boolean readyToDie = false;
    private DataOutputStream out;//output stream
    private DataInputStream in;//input stream

    public connectionThread(Socket socket){
        this.socket = socket;
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
    public boolean isUnique(String username){
        String file = "accounts.csv";
        csvReader reader = new csvReader(file);
        String[][] data = reader.read();
        for (int i = 0; i < reader.getRowSize(); i++){
            if (data[i][0].equals(username)){
                return false;
            }
        }
        return true;
    }

    public void run(){
        try{
            System.out.println("Connected to " + socket.getInetAddress() + " on port " + socket.getPort());
            out = new DataOutputStream(this.socket.getOutputStream());
            in = new DataInputStream(this.socket.getInputStream());
            while (true){
                String message = in.readUTF();
                //command line input
                System.out.println("Client[" + socket.getInetAddress() + " : " + socket.getPort() +"]: " + message);
                if (message.equals("exit")){
                    break;
                }
                else if (message.contains("loginAttempt")){
                    String[] loginInfo = message.split(":");
                    String username = loginInfo[1];
                    String password = loginInfo[2];
                    System.out.println(username);
                    System.out.println(password);
                    if (LoginAttempt(username, password)){
                        System.out.println("Successful Login Attempt from " + socket.getInetAddress() + " on port " + socket.getPort() + " to account "  + username);
                        out.writeUTF("true");
                    }
                    else{
                        out.writeUTF("false");
                    }
                }
                else if (message.contains("registerAttempt")){
                    System.out.println("Now processing Register Attempt");
                    String[] registerInfo = message.split(":");
                    String username = registerInfo[1];
                    String password = registerInfo[2];
                    if (isUnique(username)){
                        if (username.equals("") || password.equals("")){
            
                            out.writeUTF("Failed:Empty");
                        }
                        else if (password.length() < 5){
                            out.writeUTF("Failed:Short");
                        }
                        else if (!(password.matches(".*[a-z].*")) || !(password.matches(".*[A-Z].*")) || !(password.matches(".*[0-9].*"))){
                            out.writeUTF("Failed:Complex");
                        }
                        else{
                            String file = "accounts.csv";
                            csvWriter writer = new csvWriter(file, true);
                            String[] row = {username, password};
                            writer.writeRow(row, new csvReader(file).read().length);
                            File dir = new File("data/"+username);
                            dir.mkdir();//create directory for user
                            System.out.println("Account Created for " + username + " by " + socket.getInetAddress() + " on port " + socket.getPort());
                            out.writeUTF("true");
                        }
                    }
                    else{
                        out.writeUTF("Failed:NotUnique");
                    }
                }
                else{
                    out.writeUTF("Server: " + message);
                }
            }
            System.out.println("Closing connection");
            this.socket.close();
            readyToDie = true;



        } catch (Exception e){
            System.out.println("Error: " + e);
            
        }
    }
    public boolean isReadyToDie(){
        return readyToDie;
    }
}