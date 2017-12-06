package tw.org.iii.androidlittlehappy.fcm;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tw.org.iii.androidlittlehappy.ActMain;
import tw.org.iii.androidlittlehappy.CPublicParameters;
import tw.org.iii.androidlittlehappy.R;

/**
 * Created by Alice on 2017/12/5.
 */

public class MessageAdapter extends ArrayAdapter<ChatMessage> {
    private Activity activity;
    private List<ChatMessage> messages;

    public MessageAdapter(Activity context, int resource, List<ChatMessage> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.messages = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("chat",String.valueOf(position)+"position");
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        ChatMessage chatMessage = getItem(position);

        int viewType = getItemViewType(position);
        String userNickName = "someone ...";
        //Log.v("chat",chatMessage.getUsername());
        if (chatMessage.getUsername().equals(CPublicParameters.user.getfUserName())) {

            layoutResource = R.layout.right_messege;
            userNickName = CPublicParameters.user.getfNickName();
            //Log.v("chat",chatMessage.getUsername()+"+"+CPublicParameters.user.getfUserName()+"if");
        } else {
            layoutResource = R.layout.left_messege;
            if (ActMain.Hashtable_UserNameToCust.containsKey(chatMessage.getUsername())) {
                userNickName = ActMain.Hashtable_UserNameToCust.get(chatMessage.getUsername()).getfNickName();
            }
            //Log.v("chat",chatMessage.getUsername()+"+"+CPublicParameters.user.getfUserName()+"else");
        }



            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            //convertView.setTag(holder);


        //set message content
        holder.msg.setText(chatMessage.getContent());
        holder.username.setText(userNickName);
        return convertView;
    }

  /*  @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }*/



    private class ViewHolder {
        private TextView msg;
        private TextView username;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.txt_msg);
            username=(TextView)v.findViewById(R.id.txt_username);
        }
    }
}
