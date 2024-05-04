import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailFetcher {
    private String host;
    private String storeType;
    private String username;
    private String password;

    public EmailFetcher(String host, String storeType, String username, String password) {
        this.host = host;
        this.storeType = storeType;
        this.username = username;
        this.password = password;
    }

    public void checkEmails() {
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
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
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

