package tw.org.iii.androidlittlehappy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirisolin on 2017/10/4.
 */

public class CActivityFactory{
    private List<CActivitys> list=new ArrayList<CActivitys>();
    private int id = 0;
    public CActivityFactory(){
        LoadData();
    }

    // CActivitys(int id, int type, String title, String content, String creatTime, String limitTime, int limitStar, String gps, String state, String creator)
    //type: 0:留空, 1:咖啡, 2:...?
    //state: 0:留空, 1.招募中


    private void LoadData(){
        list.add(new CActivitys(1,1,"7-11咖啡買一送一","461巷口7-11","2017/10/04T16:30:00","12",3,"22.628216, 120.293043","1","001"));
        list.add(new CActivitys(2,1,"萊爾富咖啡第二杯半價","","2017/10/04T16:30:00","12",3,"22.627230, 120.292534","1","001"));
    }

    public CActivitys GetCurrent(int id, String title, String content){
        return new CActivitys(id, title, content);
        //return list[id];
    }

    public CActivitys[] GetAll(){
        return list.toArray( new CActivitys[list.size()]);
    }
}
