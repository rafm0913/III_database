package tw.org.iii.androidlittlehappy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by iii on 2017/11/29.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<CMessage> trans;

    public ChatAdapter(List<CMessage> myTrans){
        this.trans = myTrans;
    }

    private static ClickListener clickListener;
    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        ChatAdapter.clickListener = clickListener;
    }


    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.actchat_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        //CMessage cmsg = trans.get(0);
        holder.numberView.setText("1");
        holder.nameView.setText("黃曉明");
        holder.msgView.setText("你好");
        holder.dateView.setText("12:09");

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    //繼承recycleVIew.View Holder
//實作點擊監聽器
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final ImageView imgView;
        private final TextView numberView;
        private final TextView nameView;
        private final TextView msgView;
        private final TextView dateView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            imgView = (ImageView) itemView.findViewById(R.id.iv_user_photo);
            numberView = (TextView) itemView.findViewById(R.id.iv_user_number);
            nameView = (TextView) itemView.findViewById(R.id.tv_user_name);
            msgView = (TextView) itemView.findViewById(R.id.tv_last_chat);
            dateView = (TextView) itemView.findViewById(R.id.tv_time);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return true;
        }

    }
}
