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

    // CCustomers(String id, String name, String email, String password)
    private void LoadData(){
        list.add(new CCustomers(1,"",""));
        list.add(new CCustomers(2,"Dale","test123"));

    }

    public CCustomers[] GetAll(){
        return list.toArray( new CCustomers[list.size()]);
    }
}
