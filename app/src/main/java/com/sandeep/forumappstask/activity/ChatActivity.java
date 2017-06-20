package com.sandeep.forumappstask.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sandeep.forumappstask.R;
import com.sandeep.forumappstask.adapter.ConversationAdapter;
import com.sandeep.forumappstask.helper.Constants;
import com.sandeep.forumappstask.helper.DatabaseHelper;
import com.sandeep.forumappstask.helper.PrefrenceManager;
import com.sandeep.forumappstask.helper.RetrofitInstance;
import com.sandeep.forumappstask.interfaces.ApiClass;
import com.sandeep.forumappstask.model.ChatResponse;
import com.sandeep.forumappstask.model.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "chatactivity";
    private EditText etMessage;
    private Button btnSend;
    private String receivertoken, email, sendertoken, title, message, currenttime;
    private List<Message> messageList;
    private int smsType = 0;
    DatabaseHelper dataBase_helper;
    SQLiteDatabase db;

    private RecyclerView recylerView;
    private ConversationAdapter conversationAdapter;
    private static boolean isRunning = false;

    private static ChatActivity runningInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        runningInstance = this;

        sendertoken = PrefrenceManager.getInstance(this).getDeviceToken();
        etMessage = (EditText) findViewById(R.id.etChatMessage);
        btnSend = (Button) findViewById(R.id.btnSendMessage);


        receivertoken = getIntent().getExtras().getString(Constants.TOKEN);
        email = getIntent().getExtras().getString(Constants.EMAIL);

        dataBase_helper = new DatabaseHelper(this);

        recylerView = (RecyclerView) findViewById(R.id.recylerView);
        RecyclerView.LayoutManager lym = new LinearLayoutManager(this);
        recylerView.setLayoutManager(lym);

        messageList = getMessageList();
        conversationAdapter = new ConversationAdapter(this, messageList);
        recylerView.setAdapter(conversationAdapter);
        recylerView.scrollToPosition(runningInstance.conversationAdapter.getItemCount() - 1);

        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.etChatMessage:
                break;
            case R.id.btnSendMessage:

                title = "Title";
                message = etMessage.getText().toString();

                if (message.matches("")) {
                    etMessage.setError("Enter message please");
                } else {
                    db = dataBase_helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    currenttime = getSystemTime();

                    values.put(DatabaseHelper.TYPE, smsType);
                    values.put(DatabaseHelper.EMAIL, email);
                    values.put(DatabaseHelper.SENDER_ID, sendertoken);
                    values.put(DatabaseHelper.RECEIVER_ID, receivertoken);
                    values.put(DatabaseHelper.MESSAGE, message);
                    values.put(DatabaseHelper.TIME_STAMP, currenttime);

                    etMessage.setText("");
                    Message message1 = new Message(message);
                    message1.setReceiverid(receivertoken);
                    notifyMessageAdded(message1);

                    long check = db.insert(DatabaseHelper.TABLE_NAME, null, values);

                    if (check != -1) {
//                        Toast.makeText(ChatActivity.this, "inserted successfully", Toast.LENGTH_LONG).show();
                        sendMessage(message);
                        Log.d(TAG, "onClick: sending message");
                    } else {
                        //                       Toast.makeText(ChatActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onClick: something wrong happened");
                    }
                }


                break;
        }
    }

    private String getSystemTime() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH:mm a");

        date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

        String localTime = date.format(currentLocalTime);
        return localTime;
    }


    private void sendMessage(final String message) {

        ApiClass apiclass = RetrofitInstance.getRetrofit().create(ApiClass.class);
        apiclass.sendMessage(title, sendertoken, message, receivertoken, new Callback<ChatResponse>() {
            @Override
            public void success(ChatResponse chatResponse, Response response) {
                Log.d(TAG, "success: ");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: ");
                Toast.makeText(ChatActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private List<Message> getMessageList() {

        SQLiteDatabase demo = dataBase_helper.getReadableDatabase();
        messageList = new ArrayList<>();

        Cursor cursor = demo.rawQuery("select * from " + DatabaseHelper.TABLE_NAME, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int type = cursor.getInt(1);
            String email = cursor.getString(2);
            String senderid = cursor.getString(3);
            String receiverid = cursor.getString(4);
            String message = cursor.getString(5);
            String timestamp = cursor.getString(6);

            boolean shouldAdd = false;
            if (type == 0) {
                if (receiverid.equals(receivertoken))
                    shouldAdd = true;
            } else if (type == 1) {
                if (senderid.equals(receivertoken))
                    shouldAdd = true;
            }

            if (shouldAdd) {
                Message messageModel = new Message(id, type, email, senderid, receiverid, message, timestamp);
                messageList.add(messageModel);

            }


        }
        return messageList;
    }


    public static void notifyMessageAdded(final Message message1) {

        if (message1.getReceiverid().equals(runningInstance.receivertoken))
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

                    runningInstance.messageList.add(message1);
                    runningInstance.conversationAdapter.notifyItemInserted(runningInstance.conversationAdapter.getItemCount());
                    runningInstance.recylerView.scrollToPosition(runningInstance.conversationAdapter.getItemCount() - 1);
                }
            });

    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    public static boolean isRunning() {
        return isRunning;
    }


}
