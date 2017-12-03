package tw.org.iii.androidlittlehappy;



import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChat extends Fragment implements FragmentBackHandler {


    public FragmentChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String URLwithName = "http://52.198.163.90:8080/DemoServer/UrlChatController?action=selectChatByName&username=" + CPublicParameters.user.getfUserName();
        AsyncTaskSelectChat task = new AsyncTaskSelectChat();
        task.execute(new String[]{URLwithName});

        ChatAdapter chatAdapter = new ChatAdapter();
        RecyclerView recyclerView = null;

        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.listRecycleListView);

        recyclerView.setAdapter(chatAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        chatAdapter.setOnItemClickListener(new ChatAdapter.ClickListener()
        {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(getContext(), "短按到第 "+position +"項", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(int position, View v) {
                Toast.makeText(getContext(), "長按到第 "+position +"項", Toast.LENGTH_SHORT).show();
            }
        });
        Log.i("Async", String.valueOf(CPublicParameters.List_CMessage.size()));
        if (CPublicParameters.List_CMessage.size()>0)
        {
            for (int i=0;i<CPublicParameters.List_CMessage.size();i++)
            {
                JsonFactoryForChat jsonFactoryForChat = new JsonFactoryForChat();
                Log.i("Async", jsonFactoryForChat.stringify(CPublicParameters.List_CMessage.get(i)));
            }


        }

        //Fragment reload
//        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.contact);
//        if (currentFragment instanceof FragmentChat) {
//            FragmentTransaction fragTransaction =   (getActivity()).getFragmentManager().beginTransaction();
//            fragTransaction.detach(currentFragment);
//            fragTransaction.attach(currentFragment);
//            fragTransaction.commit();}



        return view;

    }


    @Override
    public boolean onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            //外理返回键
            ActMain.navigation.setSelectedItemId(R.id.navigation_home);
            return true;
        } else {
            // 如果不包含子Fragment
            // 或子Fragment没有外理back需求
            // 可如直接 return false;
            // 注：如果Fragment/Activity 中可以使用ViewPager 代替 this
            //
            return BackHandlerHelper.handleBackPress(this);
        }
    }


    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction fragmentTransaction;


}
