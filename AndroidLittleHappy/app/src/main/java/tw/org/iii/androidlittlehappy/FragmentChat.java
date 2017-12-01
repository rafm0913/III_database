package tw.org.iii.androidlittlehappy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChat extends Fragment {

    CMessageFactory CMF = new CMessageFactory();

    public FragmentChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listRecycleListView);

        ChatAdapter chatAdapter = new ChatAdapter();
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

        return view;
    }

}
