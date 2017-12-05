package tw.org.iii.androidlittlehappy.fcm;

/**
 * Created by samblow2000 on 2017/12/5.
 */

public class Msg {
    public Msg(){}
    public String actId;
    public String roomName;
    public String updateTime;
    public String LastMsg;

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getLastMsg() {
        return LastMsg;
    }

    public void setLastMsg(String lastMsg) {
        LastMsg = lastMsg;
    }
}
