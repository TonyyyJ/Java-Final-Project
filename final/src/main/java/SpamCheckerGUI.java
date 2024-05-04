import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpamCheckerGUI extends JFrame {
    private JTextField hostField, usernameField, passwordField;
    private JButton checkButton;

    public SpamCheckerGUI() {
        setTitle("Spam Email Checker");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Host:"));
        hostField = new JTextField();
        add(hostField);

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JTextField();
        add(passwordField);

        checkButton = new JButton("Check Emails");
        checkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String host = hostField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                EmailFetcher fetcher = new EmailFetcher(host, "pop3s", username, password);
                fetcher.checkEmails();
            }
        });
        add(checkButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        new SpamCheckerGUI();
    }
}
