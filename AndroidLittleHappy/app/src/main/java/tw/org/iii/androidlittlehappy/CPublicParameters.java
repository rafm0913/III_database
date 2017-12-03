package tw.org.iii.androidlittlehappy;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by kirisolin on 2017/11/22.
 */

public class CPublicParameters {

    //登入的使用者資料
    public static CCustomers user = new CCustomers();
    //note: 在登入時，裡面的user ID(ID = mySQL自動生成的primary key) 因為判斷後續不會用到，因此被伯誠拿來做別的用途，後須如果造成混淆，叫伯誠修掉。

    //對話紀錄
    public  static Hashtable<String, List<CMessage>> Hashtable_UserNameToCMessage = new Hashtable<String, List<CMessage>>();
    public  static List<CMessage> List_CMessage = new ArrayList<>();


    //大頭貼的data base
    public final static int[] images = {
            R.drawable.profile_robot,
            R.drawable.fish02,
            R.drawable.fish03,
            R.drawable.fish04
    };

}
