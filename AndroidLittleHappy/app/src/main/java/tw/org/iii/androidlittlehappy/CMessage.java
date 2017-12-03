package tw.org.iii.androidlittlehappy;

/**
 * Created by iii on 2017/11/29.
 */

public class CMessage {
    public CMessage(){};

    public CMessage(String faID, String fChatFrom, String fChatTo, String fMessage, String fRead, String fUpdateTime) {
        super();
        this.faID = faID;//活動id
        this.fChatFrom = fChatFrom;
        this.fChatTo = fChatTo;
        this.fMessage = fMessage;
        this.fRead = fRead;
        this.fUpdateTime = fUpdateTime;

    }
    private String faID;
    private String fChatFrom;
    private String fChatTo;
    private String fMessage;
    private String fRead;
    private String fUpdateTime;

    ////////alice
    public boolean left;
    public String message;

    public CMessage(boolean left, String message) {
        super();
        this.left = left;
        this.message = message;
    }



    public String getfaID() {
        return faID;
    }

    public void setfaID(String faID) {
        this.faID = faID;
    }

    public String getfChatFrom() {
        return fChatFrom;
    }

    public void setfChatFrom(String fChatFrom) {
        this.fChatFrom = fChatFrom;
    }

    public String getfChatTo() {
        return fChatTo;
    }

    public void setfChatTo(String fChatTo) {
        this.fChatTo = fChatTo;
    }

    public String getfMessage() {
        return fMessage;
    }

    public void setfMessage(String fMessage) {
        this.fMessage = fMessage;
    }

    public String getfRead() {
        return fRead;
    }

    public void setfRead(String fRead) {
        this.fRead = fRead;
    }

    public String getfUpdateTime() {
        return fUpdateTime;
    }

    public void setfUpdateTime(String fUpdateTime) {
        this.fUpdateTime = fUpdateTime;
    }
}