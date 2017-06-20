package com.sandeep.forumappstask.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sandeep.forumappstask.R;
import com.sandeep.forumappstask.activity.ChatActivity;
import com.sandeep.forumappstask.activity.UsersListActivity;
import com.sandeep.forumappstask.helper.Constants;
import com.sandeep.forumappstask.helper.DatabaseHelper;
import com.sandeep.forumappstask.model.Message;
import com.sandeep.forumappstask.model.ResultGetUsers;

import java.util.List;

/**
 * Created by sandeep on 16-06-2017.
 */

public class FireBaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "onMessageReceived: ");
        if (remoteMessage.getData() != null) {
            Log.d(TAG, "onMessageReceived: not nulll");
            String message = remoteMessage.getData().get("message");
            String senderId = remoteMessage.getData().get("sender");
            String senderEmail = getSenderEmail(senderId);

            if (senderEmail == null) {
                Log.e(TAG, "onMessageReceived: user doesnot exist" + senderId);
                return;
            }

            Message message1 = insertMessageInDb(senderEmail, senderId, message);

            if (ChatActivity.isRunning()) {
                ChatActivity.notifyMessageAdded(message1);
            } else {
                createNotification(senderEmail, senderId, message);
            }
        }
    }

    private Message insertMessageInDb(String senderEmail, String senderId, String message) {
        Log.d(TAG, "insertMessageInDb: ");
        DatabaseHelper dataBase_helper = new DatabaseHelper(this);

        SQLiteDatabase db = dataBase_helper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.TYPE, 1);
        values.put(DatabaseHelper.EMAIL, senderEmail);
        values.put(DatabaseHelper.SENDER_ID, senderId);
        values.put(DatabaseHelper.RECEIVER_ID, "");
        values.put(DatabaseHelper.MESSAGE, message);
        values.put(DatabaseHelper.TIME_STAMP, System.currentTimeMillis());
        db.insert(DatabaseHelper.TABLE_NAME, null, values);

        Message message1 = new Message(message);
        message1.setType(1);
        message1.setReceiverid(senderId);

        return message1;

    }

    private String getSenderEmail(String senderId) {
        Log.d(TAG, "getSenderEmail: ");
        List<ResultGetUsers> usersList = UsersListActivity.getSmsList(this);
        for (ResultGetUsers resultGetUsers : usersList) {
            if (resultGetUsers.getToken().equals(senderId)) {
                return resultGetUsers.getEmail();
            }
        }

        return null;
    }

    private void createNotification(String senderEmail, String senderId, String message) {
        Log.d(TAG, "createNotification: ");
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(senderEmail)
                        .setContentText(message);

        Intent resultIntent = new Intent(this, ChatActivity.class);
        resultIntent.putExtra(Constants.EMAIL, senderEmail);
        resultIntent.putExtra(Constants.TOKEN, senderId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
