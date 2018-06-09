package com.example.bill.androidlabs_summer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected ListView chatList;
    protected EditText chatText;
    protected Button sendButton;
    protected ArrayList<String> chatLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        chatList = (ListView) findViewById(R.id.chatList);
        chatText = (EditText) findViewById(R.id.chatText);
        sendButton = (Button) findViewById(R.id.sendButton);
        chatLog = new ArrayList<>();

        ChatAdapter messageAdapter = new ChatAdapter( this );
        chatList.setAdapter (messageAdapter);

        sendButton.setOnClickListener(t->{
            chatLog.add(chatText.getText().toString());
            messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()

            chatText.setText("");
        });




    }

    private class ChatAdapter extends ArrayAdapter<String>
    {
        public ChatAdapter(Context ctx){
            super (ctx, 0);
        }

        public int getCount(){
            return chatLog.size();
        }

        public String getItem(int position){
            return chatLog.get(position);
        }

       public View getView(int position, View convertView, ViewGroup parent){
           LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
           View result = null ;
           if(position%2 == 0) {
               result = inflater.inflate(R.layout.chat_row_incoming, null);
           }
           else {
               result = inflater.inflate(R.layout.chat_row_outgoing, null);
           }
           TextView message = (TextView)result.findViewById(R.id.messageText);
           message.setText(   getItem(position)  ); // get the string at position
           return result;

       }

    }
}
