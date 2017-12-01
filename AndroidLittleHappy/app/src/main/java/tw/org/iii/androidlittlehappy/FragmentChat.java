package tw.org.iii.androidlittlehappy;


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


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChat extends Fragment {

    public FragmentChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String URLwithName = "http://192.168.3.1:8080/DemoServer/UrlCustController?action=UrlChatController&username=" + CPublicParameters.user.getfUserName();
        AsyncTaskSelectChat task = new AsyncTaskSelectChat();
        task.execute(new String[]{URLwithName});

        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listRecycleListView);

        ChatAdapter chatAdapter = new ChatAdapter();
        recyclerView.setAdapter(chatAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



        return view;
    }
}
