import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EmailUI extends JFrame {
    private JButton refreshButton;
    private JLabel spamInfoLabel, nonSpamInfoLabel;
    private int spamCount, nonSpamCount;
    

    public EmailUI() {
        setTitle("Email Statistics");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        refreshButton = new JButton("Refresh Data");
        refreshButton.addActionListener(e -> updateChart());
        add(refreshButton, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        spamInfoLabel = new JLabel();
        nonSpamInfoLabel = new JLabel();
        spamInfoLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                displayEmailDetails("spam");
            }
        });
        nonSpamInfoLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                displayEmailDetails("not_spam");
            }
        });
        infoPanel.add(spamInfoLabel);
        infoPanel.add(nonSpamInfoLabel);
        add(infoPanel, BorderLayout.SOUTH);

        ///updateChart();  // Draw chart initially
    }

    private void updateChart() {
        List<Email> emails = DatabaseManager.fetchEmails(); // Assume this method fetches all emails
        DefaultPieDataset dataset = new DefaultPieDataset();

        spamCount = 0;
        nonSpamCount = 0;
        for (Email email : emails) {
            if ("spam".equalsIgnoreCase(email.getIsSpam())) {
                spamCount++;
            } else {
                nonSpamCount++;
            }
        }

        int total = spamCount + nonSpamCount;
        dataset.setValue("Spam Emails", spamCount);
        dataset.setValue("Not Spam Emails", nonSpamCount);

        JFreeChart chart = ChartFactory.createPieChart(
                "Email Classification", dataset, true, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        getContentPane().add(chartPanel, BorderLayout.CENTER);

        spamInfoLabel.setText(spamCount + " spam emails out of " + total + " total emails.");
        nonSpamInfoLabel.setText(nonSpamCount + " not_spam emails out of " + total + " total emails.");

        pack(); // Resize frame to fit components
    }

    private void displayEmailDetails(String isSpam) {
        JFrame detailsFrame = new JFrame("Email Details");
        detailsFrame.setSize(600, 400);
        detailsFrame.setLayout(new BorderLayout());

        List<Email> emails = DatabaseManager.fetchEmailsBySpamStatus(isSpam); // Implement this method in DatabaseManager
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Email email : emails) {
            model.addElement(email.getSubject());
        }

        JList<String> emailList = new JList<>(model);
        emailList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click to see details
                    String selectedSubject = emailList.getSelectedValue();
                    displayEmailContent(selectedSubject); // Implement this method to show content and sender
                }
            }
        });

        detailsFrame.add(new JScrollPane(emailList), BorderLayout.CENTER);
        detailsFrame.setVisible(true);
    }

    private void displayEmailContent(String subject) {
        List<Email> emails = DatabaseManager.fetchEmails(); // Use a method to fetch specific email by subject if large data
        for (Email email : emails) {
            if (email.getSubject().equals(subject)) {
                JTextArea emailDetails = new JTextArea(10, 30);
                emailDetails.setText("Subject: " + email.getSubject() + "\nSender: " + email.getSender() + "\nContent:\n" + email.getContent());
                emailDetails.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(emailDetails);
    
                JDialog detailsDialog = new JDialog();
                detailsDialog.setTitle("Email Details");
                detailsDialog.add(scrollPane);
                detailsDialog.pack();
                detailsDialog.setLocationRelativeTo(null);
                detailsDialog.setVisible(true);
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmailUI frame = new EmailUI();
            frame.setVisible(true);
        });
    }
}
