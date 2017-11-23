package tw.org.iii.androidlittlehappy;

/**
 * Created by kirisolin on 2017/10/4.
 */

public class CActivitys {
    private int id;//活動ID
    private String type;//活動種類
    private String title;//活動title
    private String content;//活動內容
    private String createTime;//活動發起時間
    private String limitTime;//活動有效時間
    private String limitStar;//星等限制
    private String state;//活動狀態(招募中、已完成、已過期)
    private String creator;//活動發起人
    private String GpsX;//活動GPSX位置
    private String GpsY;//活動GPSY位置
    private CActivityDetails details;

    public CActivitys(){
    }

    public CActivitys(int id, String type, String title, String content, String createTime, String limitTime, String limitStar, String gpsX, String gpsY, String state, String creator, CActivityDetails details) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.limitTime = limitTime;
        this.limitStar = limitStar;
        this.GpsX = gpsX;
        this.GpsY = gpsY;
        this.state = state;
        this.creator = creator;
        this.details = details;
    }
    
    public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}


	public String getGpsX() {
		return GpsX;
	}


	public void setGpsX(String gpsX) {
		GpsX = gpsX;
	}


	public String getGpsY() {
		return GpsY;
	}


	public void setGpsY(String gpsY) {
		GpsY = gpsY;
	}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String creatTime) {
        this.createTime = creatTime;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public String getLimitStar() {
        return limitStar;
    }

    public void setLimitStar(String limitStar) {
        this.limitStar = limitStar;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
