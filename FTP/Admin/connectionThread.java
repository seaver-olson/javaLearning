import java.io.*;
import java.net.*;

public class connectionThread extends Thread{
    private Socket socket;
    private boolean readyToDie = false;
    private DataOutputStream out;//output stream
    private DataInputStream in;//input stream
    private logging log = new logging();

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
                log.log(message, socket.getInetAddress().toString(), Integer.toString(socket.getPort()));
                if (message.equals("exit")){
                    break;
                }
                else if (message.contains("loginAttempt")){
                    String[] loginInfo = message.split(":");
                    String username = loginInfo[1];
                    String password = loginInfo[2];
                    if (LoginAttempt(username, password)){
                        log.log("Successful Login Attempt from " + socket.getInetAddress() + " on port " + socket.getPort() + " to account "  + username, socket.getInetAddress().toString(), Integer.toString(socket.getPort()));
                        csvReader reader = new csvReader("transferRequests.csv");
                        String[][] data = reader.read();
                        int flag = 0;
                        for (int i = 0; i < data.length; i++){
                            if (data[i][1].equals(username) && flag == 0){
                                System.out.println("Transfer Request Found");
                                out.writeUTF("LOGIN:SUCCESS:transferRequest:" + data[i][0] + ":" + data[i][2]);
                                flag++;
                            }
                        }
                        if (flag == 0){
                            out.writeUTF("LOGIN:SUCCESS");
                        }
                    }
                    else{
                        out.writeUTF("LOGIN:FAILED");
                    }
                }
                else if (message.contains("FetchDir")){
                    try{
                        String username = message.split(":")[1];
                        File dir = new File("/Users/Admin/Desktop/Projects/data/" + username + "/");
                        String[] files = dir.list();
                        if (files == null){
                            log.log("Error: No files found for " + username, socket.getInetAddress().toString(), Integer.toString(socket.getPort()));
                            int x = 5/ 0;//kill process
                        }

                        StringBuilder sb = new StringBuilder("Directory:");
                        for (int i = 0; i < files.length; i++){
                            if (i == files.length - 1){
                                sb.append(files[i]);
                            }
                            else{
                                sb.append(files[i]);
                                sb.append(",");
                            }
                        }
                        out.writeUTF(sb.toString());
                    }
                    catch(Exception e){
                        out.writeUTF("Error: " + e);
                    }
                }
                else if (message.contains("DenyTransfer")){
                    String[] fileInfo = message.split(":");
                    String fromUser = fileInfo[1];
                    String toUser = fileInfo[2];
                    String fileName = fileInfo[3];
                    String[][] data = new csvReader("transferRequests.csv").read();
                    for (int i = 0; i < data.length; i++){
                        if (data[i][0].equals(fromUser) && data[i][1].equals(toUser) && data[i][2].equals(fileName)){
                            csvWriter writer = new csvWriter("transferRequests.csv", false);
                            writer.removeRow(i);
                            log.log("Transfer Request Denied from " + fromUser + " to " + toUser + " for file " + fileName, socket.getInetAddress().toString(), Integer.toString(socket.getPort()));
                        }
                    }
                }
                else if (message.contains("ConfirmTransfer")){
                    String[] fileInfo = message.split(":");
                    String fromUser = fileInfo[2];
                    String toUser = fileInfo[1];
                    String fileName = fileInfo[3];
                    File file = new File("/Users/Admin/Desktop/Projects/data/" + fromUser + "/" + fileName);
                    FileInputStream fileIn = new FileInputStream(file);
                    byte[] fileData = new byte[(int) file.length()];
                    fileIn.read(fileData);
                    fileIn.close();
                    File newFile = new File("/Users/Admin/Desktop/Projects/data/" + toUser + "/" + fileName);
                    newFile.createNewFile();
                    FileOutputStream fileOut = new FileOutputStream(newFile);
                    fileOut.write(fileData);
                    fileOut.close();
                    csvWriter writer = new csvWriter("transferRequests.csv", false);
                    String[][] transferData = new csvReader("transferRequests.csv").read();
                    for (int i = 0; i < transferData.length; i++){  
                        if (transferData[i][0].equals(fromUser) && transferData[i][1].equals(toUser) && transferData[i][2].equals(fileName)){
                            writer.removeRow(i);
                            break;
                        }
                    }
                    log.log("Transfer Request Confirmed from " + fromUser + " to " + toUser + " for file " + fileName, socket.getInetAddress().toString(), Integer.toString(socket.getPort()));
                    
                }
                else if (message.contains("TransferFile")){
                    String[] fileInfo = message.split(":");
                    String fromUser = fileInfo[1];
                    String toUser = fileInfo[2];
                    String fileName = fileInfo[3];
                    File file = new File("/Users/Admin/Desktop/Projects/data/" + fromUser + "/" + fileName);
                    if (file.exists()){
                        csvWriter writer = new csvWriter("transferRequests.csv", true);
                        String[] row = {fromUser, toUser, fileName};
                        writer.writeRow(row, new csvReader("transferRequests.csv").read().length);
                        log.log("File Transfer Request from " + fromUser + " to " + toUser + " for file " + fileName, socket.getInetAddress().toString(), Integer.toString(socket.getPort()));
                        out.writeUTF("true");
                    }
                    else{
                        out.writeUTF("false");
                    }
                }
                else if (message.contains("RemoveFile")){
                    String[] fileInfo = message.split(":");
                    String username = fileInfo[1];
                    String fileName = fileInfo[2];
                    File file = new File("/Users/Admin/Desktop/Projects/data/" + username + "/" + fileName);
                    if (file.exists()){
                        file.delete();
                        out.writeUTF("true");
                    }
                    else{
                        out.writeUTF("false");
                    }
                }
                else if (message.contains("FetchFile")){
                    String[] fileInfo = message.split(":");
                    String username = fileInfo[1];
                    String fileName = fileInfo[2];
                    System.out.println("Fetching File: " + fileName + " for " + username);
                    File file = new File("/Users/Admin/Desktop/Projects/data/" + username + "/" + fileName);
                    if (file.exists()){
                        FileInputStream fileIn = new FileInputStream(file);
                        byte[] fileData = new byte[(int) file.length()];
                        fileIn.read(fileData);
                        out.write(fileData);
                        fileIn.close();
                    }
                    else{
                        System.out.println("Error file not found");
                        out.writeUTF("false");
                    }
                }
                else if (message.contains("StoreFile")){
                    String username = message.split(":")[1];
                    String filename = message.split(":")[2];
                    byte[] fileData = in.readAllBytes();
                    File file = new File("/Users/Admin/Desktop/Projects/data/" + username + "/" + filename);
                    file.createNewFile();
                    FileOutputStream fileOut = new FileOutputStream(file);
                    fileOut.write(fileData);
                    fileOut.close();
                }
                else if (message.contains("registerAttempt")){
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
                            log.log("Account Created for " + username + " by " + socket.getInetAddress() + " on port " + socket.getPort(), socket.getInetAddress().toString(), Integer.toString(socket.getPort()));
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
            log.log("Error: " + e, socket.getInetAddress().toString(), Integer.toString(socket.getPort()));
            
        }
    }
    public boolean isReadyToDie(){
        return readyToDie;
    }
}