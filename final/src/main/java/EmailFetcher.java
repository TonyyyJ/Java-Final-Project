import javax.mail.*;
import javax.mail.internet.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class EmailFetcher {
    private String host;
    private String storeType;
    private String username;
    private String password;
    private EmailClassifier model;

    public EmailFetcher(String host, String storeType, String username, String password)throws Exception { 
        this.host = host;
        this.storeType = storeType;
        this.username = username;
        this.password = password;
        this.model = new EmailClassifier();
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
    

    public void checkEmails()throws Exception {
        try {
            Properties properties = new Properties();
            properties.put("mail.pop3.host", host); // Host should be "pop.gmail.com" for Gmail
            properties.put("mail.pop3.port", "995"); // Secure port for POP3
            properties.put("mail.pop3.ssl.enable", "true"); // Enable SSL
            properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // Use SSL
            properties.put("mail.pop3.socketFactory.fallback", "false");
            properties.put("mail.pop3.socketFactory.port", "995");
            
            Session emailSession = Session.getDefaultInstance(properties);
            
            Store store = emailSession.getStore(storeType); // 'pop3s' for secure POP3
            store.connect(host, username, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            System.out.println("Total Message: " + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                String contentString = getTextFromMessage(message);
                boolean isSpam = model.isSpam(contentString);
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("content: " + contentString);
                System.out.println("Is Spam: " + isSpam);
            }

            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

