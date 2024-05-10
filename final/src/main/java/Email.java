public class Email {
    private int id;
    private String subject;
    private String sender;
    private String content;
    private String isSpam;

    public Email(int id, String subject, String sender, String content, String isSpam) {
        this.id = id;
        this.subject = subject;
        this.sender = sender;
        this.content = content;
        this.isSpam = isSpam;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getIsSpam() {
        return isSpam;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIsSpam(String isSpam) {
        this.isSpam = isSpam;
    }
}

