package tw.org.iii.androidlittlehappy;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iii on 2017/11/29.
 */

public class CMessageFactory {
    public static String[] username = new String[]{
        "model002",
        "model003",
        "model004",
        "model005"
    };

    public static int[] image = CPublicParameters.images;

    private List<CMessage> list=new ArrayList<>();
    private int position;

    public CMessageFactory(){
        if (!(CPublicParameters.List_CMessage.size()>1)) {
            LoadData();
        }
        else
        {
            LoadDataFromSQL();
        }
    }

    //改SQL指令
    //CMessage(String faID, String fChatFrom, String fChatTo, String fMessage, String fRead, String fUpdateTime)
    private void LoadData(){
        list.clear();
        list.add(new CMessage("48","model003","model002","Hello from 003 old", "0","2017/11/28 02:48:19"));
        list.add(new CMessage("48","model004","model002","Hello from 004 old", "0","2017/11/28 02:49:19"));
        list.add(new CMessage("48","model005","model002","Hello from 005 old", "0","2017/11/28 02:50:19"));
    }


    private void LoadDataFromSQL()
    {
        list.clear();
        list = CPublicParameters.List_CMessage;
    }

    private void LoadDataFromSQL_onlyLastUpdate()//還沒寫完(O)
    {
        for (int i =0;i<CPublicParameters.List_CMessage.size();i++)
        {
            String user2usesName = (list.get(i).getfChatFrom().equals(CPublicParameters.user.getfUserName()))? list.get(i).getfChatTo():list.get(i).getfChatFrom();
            String key = CPublicParameters.List_CMessage.get(i).getfaID()+ "withUser" + user2usesName;



        }
        CPublicParameters.Hashtable_UserNameToCMessage.size();

        list.clear();
        list = CPublicParameters.List_CMessage;
    }


    public CMessage[] GetAll(){
        Log.i("Async", "CMessageFactory 49 "+ String.valueOf(list.size()));
        return list.toArray( new CMessage[list.size()]);
    }

    public List<CMessage> GetAllCM(){
        return list;
    }


}
