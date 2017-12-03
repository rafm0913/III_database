package tw.org.iii.androidlittlehappy.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import tw.org.iii.androidlittlehappy.ActMain;
import tw.org.iii.androidlittlehappy.R;

/**
 * Created by iii on 2017/11/24.
 */

public class TabHistoryJoin extends Fragment {

    List<HashMap<String ,Object >> hashmaplist = new ArrayList<>();
    List<String> titlelist = new ArrayList<String>();
    List<String> createtime = new ArrayList<String>();
    List<String> creator = new ArrayList<String>();
    List<Integer> type = new ArrayList<Integer>();
    ListView listView;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acthistory_i_joined,container,false);


        Log.d("ini","我參加了"+ActMain.iv_activitylist_I_join.size()+"個活動");

        for (int i = 0; i< ActMain.iv_activitylist_I_join.size(); i++)

        {
//            Log.d("ini","活動名稱"+ActMain.iv_activitylist_I_join.get(i).getTitle());
//
//            HashMap<String,Object> hashMap = new HashMap<String, Object>();
//            int picTypeIndex = Integer.parseInt(ActMain.iv_activitylist_I_join.get(i).getType());
//            int picTypeImgID = ActMain.typelistImg[picTypeIndex-1];
//
//            titlelist.add("活動標題:"+ActMain.iv_activitylist_I_join.get(i).getTitle().toString());
//            createtime.add("活動創立時間:"+ActMain.iv_activitylist_I_join.get(i).getCreateTime().toString());
//            type.add(picTypeImgID);
//            hashMap.put("titlelist",titlelist.get(i));
//            hashMap.put("createtime",createtime.get(i));
//            hashMap.put("type",type.get(i));
//
//
//
//
//            hashmaplist.add(hashMap);

            int listSize = ActMain.iv_activitylist_I_join.size();

            LinkedHashMap<String,Object> hashMap = new LinkedHashMap<String, Object>();
            int picTypeIndex = Integer.parseInt(ActMain.iv_activitylist_I_join.get(listSize-1-i).getType());
            int picTypeImgID = ActMain.typelistImg[picTypeIndex-1];

            titlelist.add(ActMain.iv_activitylist_I_join.get(listSize-1-i).getTitle());


            Date date = null;
            try {
                date =  sdf.parse(ActMain.iv_activitylist_I_join.get(listSize-1-i).getCreateTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            createtime.add(sdf.format(date));
            creator.add("發起人："+ActMain.iv_activitylist_I_join.get(listSize-1-i).getCreator());
            type.add(picTypeImgID);
            hashMap.put("titlelist",titlelist.get(i));
            hashMap.put("createtime",createtime.get(i));
            hashMap.put("type",type.get(i));
            hashMap.put("creator",creator.get(i));

            hashmaplist.add(hashMap);





        }
        listView = (ListView)view.findViewById(R.id.listviewjoin) ;
        SimpleAdapter adapter= new SimpleAdapter(getActivity(), hashmaplist,R.layout.customlistviewitem,

                new String[]{"titlelist","createtime","type","creator"},new int[]{R.id.lbl1,R.id.lbl2,R.id.imgseen,R.id.lblcreator});

        listView.setAdapter(adapter);


        return view;
    }
}
