package com.UCI.redditsystem;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.snapshot.Node;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRefMsgs;
    private List<Msg> msgs = new ArrayList<>();
  //  private ArrayList<ArrayList<Msg> > msgs = new ArrayList<ArrayList<Msg> >();
    private List<Msg> replymsgs = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Msg> msgs, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public DatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mRefMsgs = mDatabase.getReference("Msgs");
    }


    public void readMsgs(final DataStatus dataStatus) {

        mRefMsgs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                msgs.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    Msg msg = keyNode.getValue(Msg.class);
                    msgs.add(msg);
                    keys.add(keyNode.getKey());
                    if (keyNode.hasChildren()){
                        List<String> childkeys = new ArrayList<>();
                        for (DataSnapshot childkeyNode : dataSnapshot.getChildren()) {
                            Msg replymsg = childkeyNode.getValue(Msg.class);
                            replymsgs.add(replymsg);
                            childkeys.add(keyNode.getKey());
                        }
                    }
                }
                dataStatus.DataIsLoaded(msgs, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
        public void addMsg(Msg msg,final DataStatus dataStatus) {
            String key = mRefMsgs.push().getKey();
            msg.setId(key);
            mRefMsgs.child(key).setValue(msg)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dataStatus.DataIsInserted();
                        }
                    });
        }
            public void deleteMsg(String key, final DataStatus dataStatus) {

                mRefMsgs.child(key).setValue(null)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                dataStatus.DataIsDeleted();
                            }
                        });
            }

    public void updateMsg(String key, Msg msg,final DataStatus dataStatus) {

        mRefMsgs.child(key).setValue(msg)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });

    }


    public void replyMsg(Msg msg,final DataStatus dataStatus) {

        String parentId = msg.getParentid().toString();
        DatabaseReference mNode = mRefMsgs.child(parentId);  //mDatabase.getReference("Msgs/"+parentId);

        String key = mRefMsgs.push().getKey();
        msg.setId(key);

        mNode.child(key).setValue(msg)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dataStatus.DataIsInserted();
                    }
                });

    }
}
