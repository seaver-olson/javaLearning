import javax.swing.*;

public class fetchPage {
    public fetchPage(String username){
        JFrame frame = new JFrame("Welcome " + username);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new java.awt.Color(230, 230, 240));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
