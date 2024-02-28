import java.io.File;
import javax.swing.*;


public class fetchFile {
    //using username, fetch file from server
    public fetchFile(String username){
        System.out.println("Fetching file for " + username);
        String directory = "userData/" + username + "/";
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        //create table to display files
        String[][] data = new String[listOfFiles.length][2];
        for (int i = 0; i < listOfFiles.length; i++){
            data[i][0] = listOfFiles[i].getName();
            data[i][1] = "Download";
        }
        String[] column = {"File Name", "Action"};
        JTable table = new JTable(data, column);
        table.setBounds(50, 50, 300, 200);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 50, 300, 200);
        JFrame frame = new JFrame(username + "'s Files");
        frame.setSize(400, 300);
        frame.add(scrollPane);
        frame.setLayout(null);
        frame.setVisible(true);
        
        
    }  

}
