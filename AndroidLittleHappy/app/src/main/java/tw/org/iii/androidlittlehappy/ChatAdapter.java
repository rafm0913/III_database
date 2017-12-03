package tw.org.iii.androidlittlehappy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        Log.i("Async", "ChatAdapter 53 "+ String.valueOf( message.length));
        return message.length;
    }


    //繼承recycleVIew.View Holder
//實作點擊監聽器
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final ImageView imgView;
        private final TextView numberView;
        private final TextView actView;
        private final TextView nameView;
        private final TextView msgView;
        private final TextView msgFromUserView;
        private final TextView dateView;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            imgView = (ImageView) itemView.findViewById(R.id.user_fMascot);
            nameView = (TextView) itemView.findViewById(R.id.user_NickName);
            numberView = (TextView) itemView.findViewById(R.id.user_UnreadNumber);
            actView =  (TextView) itemView.findViewById(R.id.act_Title);
            msgView = (TextView) itemView.findViewById(R.id.message);
            msgFromUserView = (TextView) itemView.findViewById(R.id.messagefromUser);
            dateView = (TextView) itemView.findViewById(R.id.message_UpdateTime);
        }

        public void bindView(int position){

            //確認第二參與人
            String user2usesName ="";
            CCustomers user2uses =new CCustomers();
            user2usesName = (message[position].getfChatFrom().equals(CPublicParameters.user.getfUserName()))? message[position].getfChatTo():message[position].getfChatFrom();
            //計算有幾則未讀 (key = (faID)withUser(2nd username))
            int numberUnRead =0;

            if (ActMain.Hashtable_UserNameToCust.size()>0 && CPublicParameters.Hashtable_UserNameToCMessage.size()>0)
            {
                user2uses = ActMain.Hashtable_UserNameToCust.get(user2usesName);
                if (CPublicParameters.Hashtable_UserNameToCMessage.get(message[position].getfaID() + "withUser" + user2usesName).size() > 0) {
                for (int i=0;i<CPublicParameters.Hashtable_UserNameToCMessage.get(message[position].getfaID()+"withUser"+user2usesName).size();i++)
                {
                    if ("0".equals(CPublicParameters.Hashtable_UserNameToCMessage.get(message[position].getfaID()+"withUser"+user2usesName).get(i).getfRead()))
                    {
                        numberUnRead+=1;
                    }
                }
                }
                imgView.setImageResource(CPublicParameters.images[Integer.parseInt(user2uses.getfMascot())]);
                numberView.setText(String.valueOf(numberUnRead));
                actView.setText("咖啡買一送一");
                nameView.setText(user2uses.getfNickName());
                if (message[position].getfChatFrom().equals(user2usesName))
                {
                    msgView.setText(message[position].getfMessage());
                    msgFromUserView.setText("");
                }
                else
                {
                    msgView.setText("");
                    msgFromUserView.setText(message[position].getfMessage());
                }
                dateView.setText(message[position].getfUpdateTime());
            }
            else
            {
                imgView.setImageResource(0);
                numberView.setText("0");
                actView.setText("");
                nameView.setText("");
                msgView.setText("");
                msgFromUserView.setText("");
                dateView.setText("");
            }
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
