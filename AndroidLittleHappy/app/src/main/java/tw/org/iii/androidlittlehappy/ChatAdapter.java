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

//public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
public class ChatAdapter extends RecyclerView.Adapter {
    CMessageFactory cMessageFactory = new CMessageFactory();
    CMessage[] message = cMessageFactory.GetAll();
//    private List<CMessage> trans;
//
//    public ChatAdapter(List<CMessage> myTrans){
//        this.trans = myTrans;
//    }



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
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_chat_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bindView(position);

    }


    @Override
    public int getItemCount() {
        return message.length;
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
            imgView = (ImageView) itemView.findViewById(R.id.user_fMascot);
            numberView = (TextView) itemView.findViewById(R.id.user_UnreadNumber);
            nameView = (TextView) itemView.findViewById(R.id.user_NickName);
            msgView = (TextView) itemView.findViewById(R.id.message);
            dateView = (TextView) itemView.findViewById(R.id.message_UpdateTime);
        }

        public void bindView(int position){
//            imgView.setImageResource(CMessageFactory.image[position]);
//            nameView.setText(CMessageFactory.username[position]);
            imgView.setImageResource(CPublicParameters.images[0]);
            numberView.setText("1");
            nameView.setText(message[position].getfChatFrom());
            msgView.setText(message[position].getfMessage());
            dateView.setText(message[position].getfUpdateTime());
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
