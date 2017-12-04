package tw.org.iii.androidlittlehappy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kirisolin on 2017/12/5.
 */

public class FragmentRoomListAdapter extends ArrayAdapter {
    private LayoutInflater myInflater;
    private List<String> cMessageList;

    public FragmentRoomListAdapter(Context context, List<String> cMessageList){
        super(context,cMessageList.size());
        myInflater = LayoutInflater.from(context);
        this.cMessageList = cMessageList;
    }


    @Override
    public int getCount() {
        return cMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return cMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cMessageList.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.fragmentroomlist_item, null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.act_Title)

            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String actTitle = (String)getItem(position);

        holder.actTitle.setText(actTitle.toString());

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

        public ViewHolder(TextView actTitle){
            this.actTitle = actTitle;
        }

    }
}
