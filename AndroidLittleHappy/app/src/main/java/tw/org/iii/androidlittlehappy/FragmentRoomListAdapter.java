package tw.org.iii.androidlittlehappy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tw.org.iii.androidlittlehappy.fcm.FragmentRoomList;
import tw.org.iii.androidlittlehappy.fcm.Msg;

/**
 * Created by kirisolin on 2017/12/5.
 */

public class FragmentRoomListAdapter extends ArrayAdapter {
    private LayoutInflater myInflater;
    private DatabaseReference root ;

    //
    private List<Msg> msgList;

    public FragmentRoomListAdapter(Context context, List<Msg> msgList){
        super(context,msgList.size());
        myInflater = LayoutInflater.from(context);
        this. msgList =  msgList;
    }


    @Override
    public int getCount() {
        return msgList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return msgList.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return msgList.indexOf(getItem(position));
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.fragmentroomlist_item, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.act_Title),
                    (TextView) convertView.findViewById(R.id.user_NickName),
                    (ImageView) convertView.findViewById(R.id.user_fMascot)
            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final Msg msg = (Msg)getItem(position);
        String actTitle = "can't see title";
        if (ActMain.iv_activitylist_I_can_see.size()>0)
        {
            for (int i=0;i<ActMain.iv_activitylist_I_can_see.size();i++)
            {
                if (ActMain.iv_activitylist_I_can_see.get(i).getId() == Integer.parseInt(msg.getActId()))
                {
                    actTitle = ActMain.iv_activitylist_I_can_see.get(i).getTitle();
                }
            }

        }


        String user2Name = "can't see username";
        user2Name = msg.getuser2Name();
        int user_fMascot = 0;
        String user2NickName="someone ...";
        if (user2Name != null && !user2Name.isEmpty())
        {
            if (ActMain.Hashtable_UserNameToCust.containsKey(user2Name))
            {
            user2NickName = ActMain.Hashtable_UserNameToCust.get(user2Name).getfNickName();
            user_fMascot = Integer.parseInt(ActMain.Hashtable_UserNameToCust.get(user2Name).getfMascot());
            }
        }

        holder.actTitle.setText(actTitle+" (ID:"+msg.getActId()+")");
        holder.txtUser2Name.setText(user2NickName);
        holder.user_fMascot.setImageResource(CPublicParameters.images[user_fMascot]);

        return convertView;
    }

    private class ViewHolder {
        TextView actTitle;
        TextView txtUser2Name;
        ImageView user_fMascot;
        public ViewHolder(TextView actTitle, TextView txtUser2Name, ImageView user_fMascot){
            this.actTitle = actTitle;
            this.txtUser2Name = txtUser2Name;
            this.user_fMascot = user_fMascot;
        }

    }
}
