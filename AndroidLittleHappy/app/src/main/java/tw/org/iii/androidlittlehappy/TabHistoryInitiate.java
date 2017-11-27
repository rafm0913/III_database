package tw.org.iii.androidlittlehappy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iii on 2017/11/24.
 */

public class TabHistoryInitiate extends Fragment {

    List<String> data = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acthistory_i_initiate,container,false);

        final ListView listView = (ListView)view.findViewById(R.id.initiateList);

        for (int i = 0;i<ActMain.iv_activitylist_I_can_see.size();i++)
        {
            data.add(ActMain.iv_activitylist_I_can_see.get(i).getTitle().toString());


        }





        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_expandable_list_item_1,data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),ActHistoryInitiate.class);
                intent.putExtra("title",listView.getItemAtPosition(i).toString());
                startActivity(intent);
            }
        });












        return view;
    }


}
