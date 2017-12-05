package tw.org.iii.androidlittlehappy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tw.org.iii.androidlittlehappy.fcm.Msg;

/**
 * Created by kirisolin on 2017/12/5.
 */

public class FragmentRoomListAdapter extends ArrayAdapter {
    private LayoutInflater myInflater;

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
                    (TextView) convertView.findViewById(R.id.user_NickName)
            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Msg msg = (Msg)getItem(position);

        holder.actTitle.setText(msg.getRoomName());
        holder.txtTime.setText(msg.getUpdateTime());
        return convertView;
    }


//    private class ViewHolder {
//        ImageView user_fMascot;
//        TextView actTitle;
//        TextView username;
//        public ViewHolder(ImageView user_fMascot, TextView actTitle, TextView username){
//            this.user_fMascot = user_fMascot;
//            this.actTitle = actTitle;
//            this.username = username;
//        }
//    }

    private class ViewHolder {
        TextView actTitle;
        TextView txtTime;
        public ViewHolder(TextView actTitle, TextView txtTime){
            this.actTitle = actTitle;
            this.txtTime = txtTime;
        }

    }
}
