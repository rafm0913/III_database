package tw.org.iii.androidlittlehappy.fcm;

/**
 * Created by Alice on 2017/12/5.
 */

public class ChatMessage {
    private String content;
    private boolean isMine;

    public ChatMessage(String content, boolean isMine) {
        this.content = content;
        this.isMine = isMine;
    }

    public String getContent() {
        return content;
    }

    public boolean isMine() {
        return isMine;
    }
}
