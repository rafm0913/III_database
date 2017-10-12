package tw.org.iii.androidlittlehappy;

/**
 * Created by kirisolin on 2017/10/4.
 */

public class CActivitys {
    private int id;//活動ID
    private int type;//活動種類
    private String title;//活動title
    private String content;//活動內容
    private String creatTime;//活動發起時間
    private String limitTime;//活動有效時間
    private int limitStar;//星等限制
    private String gps;//活動GPS位置
    private String state;//活動狀態(招募中、已完成、已過期)
    private String creator;//活動發起人

    public CActivitys(int id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public CActivitys(int id, int type, String title, String content, String creatTime, String limitTime, int limitStar, String gps, String state, String creator) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.creatTime = creatTime;
        this.limitTime = limitTime;
        this.limitStar = limitStar;
        this.gps = gps;
        this.state = state;
        this.creator = creator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public int getLimitStar() {
        return limitStar;
    }

    public void setLimitStar(int limitStar) {
        this.limitStar = limitStar;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreater() {
        return creator;
    }

    public void setCreater(String creater) {
        this.creator = creater;
    }
}
