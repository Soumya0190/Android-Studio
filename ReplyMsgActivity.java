package com.UCI.redditsystem;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class ReplyMsgActivity extends AppCompatActivity {

    private EditText mreply_editText;
    private TextView mtxt_Title;
    private TextView mtxt_Id;
    private Button mReply_btn;
    private String key;
    private String parentId;
    private String title;
    private String replyContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_msg);

       title = getIntent().getStringExtra("title");
       parentId = getIntent().getStringExtra("key");
        mtxt_Id = (TextView) findViewById(R.id.viewText_replyId);
        mtxt_Title = (TextView) findViewById(R.id.viewText_replyTitle);
        mtxt_Title.setText(title);
        mreply_editText = (EditText) findViewById(R.id.editText_replyContent);
        mReply_btn = (Button) findViewById(R.id.btn_sendReplyMsg);

        mReply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Msg msg = new Msg();
               // msg.setTitle(mtxt_Title.getText().toString());
                msg.setTitle(title);
                msg.setContent(mreply_editText.getText().toString());
               // msg.setParentid(mtxt_Id.toString());
                msg.setParentid(parentId);
                new DatabaseHelper().replyMsg(msg, new DatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Msg> msgs, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(ReplyMsgActivity.this, "The msg is replied successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ReplyMsgActivity.this, MainActivity.class );
                        ReplyMsgActivity.this.startActivity(intent);
                    }

                    @Override
                    public void DataIsUpdated() {
                    }

                    @Override
                    public void DataIsDeleted() {
                    }
                });
                //set
            }

        });



    }


}

