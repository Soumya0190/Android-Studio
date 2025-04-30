package com.UCI.redditsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recyclerViewHelper {
    private Context mContext;
    private MsgsAdapter mMsgAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<Msg> msgs, List<String> keys) {
        mContext = context;
        mMsgAdapter = new MsgsAdapter(msgs, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mMsgAdapter);
    }

    class MsgItemView extends RecyclerView.ViewHolder{
        private TextView mId;
        private TextView mTitle;
        private TextView mContent;
        private TextView mParentID;
        private String key;
        private TextView mScore;

        public MsgItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext)
                    .inflate(R.layout.msg_list_item, parent, false));
            mTitle = (TextView) itemView.findViewById(R.id.textView_title);
            mContent = (TextView) itemView.findViewById(R.id.textView_content);
            mId = (TextView) itemView.findViewById(R.id.textView_id);
            mParentID = (TextView) itemView.findViewById(R.id.textView_parentid);
            mScore = (TextView) itemView.findViewById(R.id.textView_score);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ReplyMsgActivity.class);
                    intent.putExtra("key", key);
                    intent.putExtra("title", mTitle.getText().toString());
                    intent.putExtra("parentId", mId.getText().toString());

                    mContext.startActivity(intent);
                }
            });

        }




        public void bind(Msg msg, String key){
            mTitle.setText(msg.getTitle());
            mContent.setText(msg.getContent());
            mParentID.setText(msg.getParentid());
           mId.setText(msg.getId());
            this.key = key;

        }
    }

class MsgsAdapter extends RecyclerView.Adapter<MsgItemView>{
        private List<Msg> mMsgLst;
        private List<String> mKeys;

    public MsgsAdapter(List<Msg> mMsgLst, List<String> mKeys) {
        this.mMsgLst = mMsgLst;
        this.mKeys = mKeys;
    }

    @NonNull
    @Override
    public MsgItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MsgItemView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgItemView holder, int position) {
        holder.bind(mMsgLst.get(position), mKeys.get(position));
    }

    @Override
    public int getItemCount() {
        return mMsgLst.size();
    }
}

}
