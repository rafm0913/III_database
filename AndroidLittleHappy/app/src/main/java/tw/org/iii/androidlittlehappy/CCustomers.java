package tw.org.iii.androidlittlehappy;

/**
 * Created by kirisolin on 2017/9/23.
 */

public class CCustomers {
    private int int_pri_fID;//會員server編號
    private String str_pri_fUserName;//會員ID
    private String str_pri_fPassword;//會員密碼

    public CCustomers() {

    }

    public CCustomers(int ID, String Name, String passwd)
    {
        this.int_pri_fID = ID;
        this.str_pri_fUserName = Name;
        this.str_pri_fPassword = passwd;
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

}
