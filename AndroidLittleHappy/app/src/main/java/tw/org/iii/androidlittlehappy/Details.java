package tw.org.iii.androidlittlehappy;

/**
 * Created by iii on 2017/11/13.
 */

public class Details {
    int aid;
    String pid;
    boolean status;

    public Details(){

    }


    public Details(int aid, String pid, boolean status){
        this.aid = aid;
        this.pid = pid;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
