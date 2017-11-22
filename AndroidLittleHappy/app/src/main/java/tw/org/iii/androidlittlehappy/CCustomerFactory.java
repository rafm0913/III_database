package tw.org.iii.androidlittlehappy;

import java.util.ArrayList;

/**
 * Created by kirisolin on 2017/9/23.
 */

public class CCustomerFactory {
    private ArrayList<CCustomers> list=new ArrayList<>();
    private int position;
    public CCustomerFactory(){
        LoadData();
    }

    // CCustomers(int ID, String Name, String passwd, String Email, String NickName, String Mascot, int Star, int Time)
    private void LoadData(){
        list.add(new CCustomers(1,"","","test001@gmail.com","Marco","0",3,5));
        list.add(new CCustomers(2,"ggg123","321","test002@gmail.com","JSON","0",3,5));
    }

    public CCustomers[] GetAll(){
        return list.toArray( new CCustomers[list.size()]);
    }
}
