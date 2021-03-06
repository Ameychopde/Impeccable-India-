package com.example.impeccable_india;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.impeccable_india.Model.Message;
import com.example.impeccable_india.Model.MessageAdapter;
import com.example.impeccable_india.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class chat extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference messagedb;
    MessageAdapter messageAdapter;
    User u;
    List<Message> messages ;
    DataSnapshot dataSnapshot;

    RecyclerView rvMessage;
    EditText etMessage;
    ImageButton imgButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();
    }

    private void init() {
        auth = FirebaseAuth.getInstance();
        database =FirebaseDatabase.getInstance();
        u = new User();

        rvMessage =(RecyclerView)findViewById(R.id.rvMessage);
        etMessage= (EditText)findViewById(R.id.etMessage);
        imgButton =(ImageButton)findViewById(R.id.btnsend);
        imgButton.setOnClickListener(this::onClick);
        messages= new ArrayList<>();

    }

    @Override
    public void onClick(View view ) {

        if (!TextUtils.isEmpty(etMessage.getText().toString()))
        {
            Message message = new Message(etMessage.getText().toString(),u.getName());
            etMessage.setText("");
            messagedb.push().setValue(message);
        }
        else{
            Toast.makeText(getApplicationContext(),"blank message",Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = auth.getCurrentUser();

        u.setUid(currentUser.getUid());
        u.setEmail(currentUser.getEmail());


        database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                u = dataSnapshot.getValue(User.class);
                u.setUid(currentUser.getUid());
                AllMethod.name =u.getName();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        messagedb = database.getReference("massages");
        messagedb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                   Message message = dataSnapshot.getValue(Message.class);
                   message.setKey(dataSnapshot.getKey());
                   messages.add(message);
                   displayMessages(messages);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                  Message message = dataSnapshot.getValue(Message.class);
                  message.setKey(dataSnapshot.getKey());

                  List<Message> newMessages = new ArrayList<Message>();

                  for(Message m: messages)
                  {
                      if (m.getKey().equals(message.getKey())){
                          newMessages.add(message);
                      }else
                          {
                          newMessages.add(m);


                      }
                  }
                  messages = newMessages;

                  displayMessages(messages);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Message message = dataSnapshot.getValue(Message.class);
                message.setKey(dataSnapshot.getKey());

                List<Message> newMessages = new ArrayList<Message>();

                for (Message m:messages){
                    if (m.getKey().equals(message.getKey())){
                        newMessages.add(m);
                    }
                }
                messages =newMessages;
                displayMessages(messages);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        messages = new ArrayList<>();
    }

    private void displayMessages(List<Message> messages) {
        rvMessage.setLayoutManager(new LinearLayoutManager(chat.this));
        messageAdapter = new MessageAdapter(chat.this,messages,messagedb);
        rvMessage.setAdapter(messageAdapter);
    }
}