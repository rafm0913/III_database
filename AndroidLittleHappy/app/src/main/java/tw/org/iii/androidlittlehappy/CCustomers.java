package tw.org.iii.androidlittlehappy;

/**
 * Created by kirisolin on 2017/9/23.
 */

public class CCustomers {
    private int int_pri_fID;//會員server編號
    private String str_pri_fUserName;//會員ID
    private String str_pri_fPassword;//會員密碼
    private String str_pri_fEmail;
    private String str_pri_fNickName;
    private String str_pri_fMascot;
    private String str_pri_fDefaultStar;
    private String str_pri_fDefaultTime;
    private String str_pri_fStar;


    public CCustomers() {

    }

    public CCustomers(int ID, String Name, String passwd, String Email, String NickName, String Mascot, String DefaultStar, String DefaultTime, String Star)
    {
        this.int_pri_fID = ID;
        this.str_pri_fUserName = Name;
        this.str_pri_fPassword = passwd;
        this.str_pri_fEmail = Email;
        this.str_pri_fNickName = NickName;
        this.str_pri_fMascot = Mascot;
        this.str_pri_fDefaultStar = DefaultStar;
        this.str_pri_fDefaultTime = DefaultTime;
        this.str_pri_fStar = Star;
    }


    public int getfID() {
        return int_pri_fID;
    }

    public void setfID(int fID) {
        this.int_pri_fID = fID;
    }

    public String getfUserName() {
        return str_pri_fUserName;
    }

    public void setfUserName(String fUserName) {
        this.str_pri_fUserName = fUserName;
    }

    public String getfPassword() {
        return str_pri_fPassword;
    }

    public void setfPassword(String fPassword) {
        this.str_pri_fPassword = fPassword;
    }

    public String getfEmail() { return str_pri_fEmail; }

    public void setfEmail(String str_pri_fEmail) { this.str_pri_fEmail = str_pri_fEmail;  }

    public String getfNickName() { return str_pri_fNickName; }

    public void setfNickName(String str_pri_fNickName) { this.str_pri_fNickName = str_pri_fNickName; }

    public String getfMascot() { return str_pri_fMascot; }

    public void setfMascot(String str_pri_fMascot) { this.str_pri_fMascot = str_pri_fMascot; }

    public String getfDefaultStar() { return str_pri_fDefaultStar; }

    public void setfDefaultStar(String str_pri_fDefaultStar) { this.str_pri_fDefaultStar = str_pri_fDefaultStar; }

    public String getfDefaultTime() { return str_pri_fDefaultTime; }

    public void setfDefaultTime(String str_pri_fDefaultTime) { this.str_pri_fDefaultTime = str_pri_fDefaultTime; }

    public String getfStar() { return str_pri_fStar; }

    public void setfStar(String str_pri_fStar) { this.str_pri_fStar = str_pri_fStar; }
}