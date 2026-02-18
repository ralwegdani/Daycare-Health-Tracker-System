
public class Message {
    String content;
    String sender;
    String recipient;
    boolean isRead;

    public Message(String content, String sender, String recipient) {
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
        this.isRead = false;
    }
    

}
