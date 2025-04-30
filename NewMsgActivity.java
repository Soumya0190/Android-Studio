package com.UCI.redditsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class NewMsgActivity extends AppCompatActivity {

    private EditText mTitle_EditText;
    private EditText mContent_EditText;
    private Button mbtn_sendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_msg);
        mTitle_EditText = (EditText)findViewById(R.id.EditText_newMsgTitle);
        mContent_EditText = (EditText)findViewById(R.id.editText2_MsgContent);
        mbtn_sendMsg = (Button)findViewById(R.id.btn_sendMsg);

        mbtn_sendMsg.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Msg msg = new Msg();
                msg.setTitle(mTitle_EditText.getText().toString());
                msg.setContent(mContent_EditText.getText().toString());
                msg.setParentid("parent");
                new DatabaseHelper().addMsg(msg, new DatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Msg> msgs, List<String> keys) {
                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NewMsgActivity.this, "The msg is send successfully", Toast.LENGTH_LONG).show();
                      //  finish(); return;
                        Intent intent = new Intent(NewMsgActivity.this, MainActivity.class );
                        NewMsgActivity.this.startActivity(intent);
                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }


                });


            }

        });



    }

}
