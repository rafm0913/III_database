package tw.org.iii.androidlittlehappy.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import tw.org.iii.androidlittlehappy.ActHistoryInitiate;
import tw.org.iii.androidlittlehappy.ActMain;
import tw.org.iii.androidlittlehappy.R;

/**
 * Created by iii on 2017/11/24.
 */

public class TabHistoryInitiate extends Fragment {

    List<LinkedHashMap<String ,Object >> hashmaplist = new ArrayList<>();
    List<String> titlelist = new ArrayList<String>();
    List<String> createtime = new ArrayList<String>();
    List<Integer> type = new ArrayList<Integer>();
    ListView listView;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acthistory_i_initiate,container,false);


        for (int i = 0;i<ActMain.iv_activitylist_I_initiate.size();i++)

        {
            int listSize = ActMain.iv_activitylist_I_initiate.size();

            LinkedHashMap<String,Object> hashMap = new LinkedHashMap<String, Object>();
            int picTypeIndex = Integer.parseInt(ActMain.iv_activitylist_I_initiate.get(listSize-1-i).getType());
            int picTypeImgID = ActMain.typelistImg[picTypeIndex-1];

            titlelist.add(ActMain.iv_activitylist_I_initiate.get(listSize-1-i).getTitle().toString());


            Date date = null;
            try {
                date =  sdf.parse(ActMain.iv_activitylist_I_initiate.get(listSize-1-i).getCreateTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            createtime.add(sdf.format(date));
            type.add(picTypeImgID);
            hashMap.put("titlelist",titlelist.get(i));
            hashMap.put("createtime",createtime.get(i));
            hashMap.put("type",type.get(i));


            hashmaplist.add(hashMap);

        }




        listView = (ListView)view.findViewById(R.id.initiateList);




        SimpleAdapter adapter= new SimpleAdapter(getActivity(), hashmaplist,R.layout.customlistviewitem,

                new String[]{"titlelist","createtime","type"},new int[]{R.id.lbl1,R.id.lbl2,R.id.imgseen});


        listView.setAdapter(adapter);

      /*  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),ActHistoryInitiate.class);
                intent.putExtra("title",listView.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });*/


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String title = hashmaplist.get(i).get("titlelist").toString();




                Intent intent = new Intent();
                intent.setClass(getActivity(),ActHistoryInitiate.class);
                intent.putExtra("123",title);
                startActivity(intent);
            }
        });



        return view;
    }



}
