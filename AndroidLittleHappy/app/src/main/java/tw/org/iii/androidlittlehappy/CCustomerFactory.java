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
        list.add(new CCustomers("","marco001","test001@gmail.com",""));
        list.add(new CCustomers("rafm0913","Dale","rafm0913@gmail.com","test123"));

    }

    public CCustomers[] GetAll(){
        return list.toArray( new CCustomers[list.size()]);
    }
}
