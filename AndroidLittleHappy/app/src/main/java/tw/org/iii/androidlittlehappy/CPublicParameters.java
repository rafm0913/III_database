package tw.org.iii.androidlittlehappy;

/**
 * Created by kirisolin on 2017/11/22.
 */

public class CPublicParameters {

    //登入的使用者資料
    public static CCustomers user = new CCustomers();
    //note: 在登入時，裡面的user ID(ID = mySQL自動生成的primary key) 因為判斷後續不會用到，因此被伯誠拿來做別的用途，後須如果造成混淆，叫伯誠修掉。

    //大頭貼的data base
    public final static int[] images = {
            R.drawable.fish01,
            R.drawable.fish02,
            R.drawable.fish03,
            R.drawable.fish04
    };

}
