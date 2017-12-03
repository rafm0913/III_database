package tw.org.iii.androidlittlehappy.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tw.org.iii.androidlittlehappy.ActMain;
import tw.org.iii.androidlittlehappy.R;

/**
 * Created by iii on 2017/11/24.
 */

public class TabHistorySeen extends Fragment {
    List<HashMap<String ,Object >> hashmaplist = new ArrayList<>();
    List<String> titlelist = new ArrayList<String>();
    List<String> createtime = new ArrayList<String>();
    List<Integer> type = new ArrayList<Integer>();
    ListView listView;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acthistory_i_have_seen,container,false);

        for (int i = 0; i< ActMain.iv_activitylist_I_can_see.size(); i++)

        {
            HashMap<String,Object> hashMap = new HashMap<String, Object>();
            int picTypeIndex = Integer.parseInt(ActMain.iv_activitylist_I_can_see.get(i).getType());
            int picTypeImgID = ActMain.typelistImg[picTypeIndex-1];

            titlelist.add("活動標題:"+ActMain.iv_activitylist_I_can_see.get(i).getTitle().toString());
            createtime.add("活動創立時間:"+ActMain.iv_activitylist_I_can_see.get(i).getCreateTime().toString());
            type.add(picTypeImgID);
            hashMap.put("titlelist",titlelist.get(i));
            hashMap.put("createtime",createtime.get(i));
            hashMap.put("type",type.get(i));




            hashmaplist.add(hashMap);





        }

        listView = (ListView)view.findViewById(R.id.listseen) ;
        SimpleAdapter adapter= new SimpleAdapter(getActivity(), hashmaplist,R.layout.customlistviewitem,

                new String[]{"titlelist","createtime","type"},new int[]{R.id.lbl1,R.id.lbl2,R.id.imgseen});







        return view;
    }
}
