package com.UCI.redditsystem;

import java.util.ArrayList;

public class MsgListModel {
    String key;
    ArrayList<Msg> msgList;

    public String getKey(){
        return key;
    }

    public void setKey(String title){
        this.key = key;
    }

    public ArrayList<Msg>getArrayList(){
        return msgList;
    }

    public void setArrayList(ArrayList<Msg> msgArrayList){
        this.msgList = msgArrayList;
    }



}
