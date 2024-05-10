
import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import org.jsoup.Jsoup;

public class EmailCheckerThread extends Thread {
    private Message message;
    private EmailClassifier model;
    private DatabaseManager dbManager;

    public EmailCheckerThread(Message message, EmailClassifier model, DatabaseManager dbManager) {
        this.message = message;
        this.model = model;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        try {
            String contentString = getTextFromMessage(message);
            boolean isSpam = model.isSpam(contentString);
            String result = isSpam ? "spam" : "not_spam";
            dbManager.insertEmail(message.getSubject(), message.getFrom()[0].toString(), contentString, result);

            /* 
            System.out.println("---------------------------------");
            System.out.println("Email Number " + getName());
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            System.out.println("content: " + contentString);
            System.out.println("Is Spam: " + isSpam);
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("text/html")) {
            String html = (String) message.getContent();
            return Jsoup.parse(html).text();  // Converts HTML to plain text
        } else if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();
            String plainText = null;
            String htmlText = null;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    plainText = bodyPart.getContent().toString();
                } else if (bodyPart.isMimeType("text/html")) {
                    String html = (String) bodyPart.getContent();
                    htmlText = Jsoup.parse(html).text();  // Converts HTML to plain text
                }
            }
            // Prefer plain text over HTML if both are present
            return plainText != null ? plainText : htmlText;
        }
        return "";
    }
}