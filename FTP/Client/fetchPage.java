import javax.swing.*;
import java.awt.*;
import java.io.DataOutputStream;
import java.io.DataInputStream;

public class fetchPage {
    public fetchPage(String username, DataOutputStream out, DataInputStream in) {
        JFrame frame = new JFrame(username + "'s Page");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(230, 230, 240));

        JLabel usernameLabel = new JLabel("Welcome " + username);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(usernameLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 10)); // Rows, Columns, Horizontal Gap, Vertical Gap
        buttonPanel.setBackground(new Color(230, 230, 240));

        JButton fetchButton = new JButton("Fetch File");
        fetchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        fetchButton.addActionListener(e -> {
            try {
                new fetchFile(username, out, in);
            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
            frame.dispose();
        });
        buttonPanel.add(fetchButton);

        JButton storeButton = new JButton("Store/Remove File from Server");
        storeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        storeButton.addActionListener(e -> {
            new storeFile(username, out, in);
            frame.dispose();
        });
        buttonPanel.add(storeButton);

        JButton transferButton = new JButton("Transfer File to/from Another User");
        transferButton.setFont(new Font("Arial", Font.PLAIN, 14));
        transferButton.addActionListener(e -> {
            new transferFile(username, out, in);
            frame.dispose();
        });
        buttonPanel.add(transferButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
