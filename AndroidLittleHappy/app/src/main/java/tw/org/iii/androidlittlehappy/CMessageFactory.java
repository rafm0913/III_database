package tw.org.iii.androidlittlehappy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iii on 2017/11/29.
 */

public class CMessageFactory {
    private ArrayList<CMessage> list=new ArrayList<>();
    private int position;

    public CMessageFactory(){
        LoadData();
    }

    //改SQL指令
    //CMessage(String faID, String fChatFrom, String fChatTo, String fMessage, String fRead, String fUpdateTime)
    private void LoadData(){
        list.add(new CMessage("48","model003","model002","Hello from 003", "0","2017/11/28 02:48:19"));
        list.add(new CMessage("48","model004","model002","Hello from 004", "0","2017/11/28 02:49:19"));
        list.add(new CMessage("48","model005","model002","Hello from 005", "0","2017/11/28 02:50:19"));

    }

    public CMessage[] GetAll(){
        return list.toArray( new CMessage[list.size()]);
    }

    public List<CMessage> GetAllCM(){
        return list;
    }


}
