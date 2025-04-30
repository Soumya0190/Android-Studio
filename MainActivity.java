package com.UCI.redditsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_msgs);
        new DatabaseHelper().readMsgs(new DatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Msg> msgs, List<String> keys) {
                new recyclerViewHelper().setConfig(mRecyclerView, MainActivity.this, msgs, keys);            }

            @Override
            public void DataIsInserted() { }

            @Override
            public void DataIsUpdated() {  }
            @Override
            public void DataIsDeleted() {      }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.msgpost_activity_meni, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.new_msg:
                startActivity(new Intent( this, NewMsgActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




}
