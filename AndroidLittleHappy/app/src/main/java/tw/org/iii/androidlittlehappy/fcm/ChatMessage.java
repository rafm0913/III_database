package tw.org.iii.androidlittlehappy.fcm;

/**
 * Created by Alice on 2017/12/5.
 */

public class ChatMessage {
    private String content;
    private boolean isMine;
    private String username;

    public ChatMessage(String username,String content) {
        this.content = content;
        //this.isMine = isMine;
        this.username=username;
    }

    public String getContent() {
        return content;
    }
    public String getUsername() {
        return username;
    }

    public boolean isMine() {
        return isMine;
    }
}
