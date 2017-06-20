package com.sandeep.forumappstask.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sandeep.forumappstask.R;
import com.sandeep.forumappstask.adapter.UsersAdapter;
import com.sandeep.forumappstask.helper.DatabaseHelper;
import com.sandeep.forumappstask.helper.PrefrenceManager;
import com.sandeep.forumappstask.helper.RetrofitInstance;
import com.sandeep.forumappstask.interfaces.ApiClass;
import com.sandeep.forumappstask.model.GetRegisteredUsers;
import com.sandeep.forumappstask.model.ResultGetUsers;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UsersListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ResultGetUsers> list;
    private UsersAdapter usersAdapter;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private boolean isFisrtTimeLaunch;
    private String unqiue_id;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        databaseHelper = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);

        unqiue_id = PrefrenceManager.getInstance(this).getUniqueId();


        pbLoading.setVisibility(View.VISIBLE);
        if (MainActivity.isNetworkAvailable(this)) {
            getUsersFromServer(unqiue_id);
        } else {
            getUsersFromLocalDatabase();
            pbLoading.setVisibility(View.GONE);
        }

    }

    private void getUsersFromLocalDatabase() {
        list = getSmsList(this);
        usersAdapter = new UsersAdapter(UsersListActivity.this, list);
        recyclerView.setAdapter(usersAdapter);
    }

    private void getUsersFromServer(String unqiue_id) {
        ApiClass apiclass = RetrofitInstance.getRetrofit().create(ApiClass.class);
        apiclass.getUser(unqiue_id, new Callback<GetRegisteredUsers>() {
            @Override
            public void success(GetRegisteredUsers getRegisteredUsers, Response response) {
    pbLoading.setVisibility(View.GONE);
                PrefrenceManager.getInstance(UsersListActivity.this).setFirstTimeLaunch(false);

                list = getRegisteredUsers.getResult();
                for (int i = 0; i < list.size(); i++) {
                    String token = list.get(i).getToken().toString();
                    String email = list.get(i).getEmail();
                    String unique_id = list.get(i).getUniqueId();

                    sqLiteDatabase = databaseHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();

                    values.put(DatabaseHelper.USER_EMAIL, email);
                    values.put(DatabaseHelper.USER_TOKEN, token);
                    values.put(DatabaseHelper.USER_UNIQUE_ID, unique_id);

                    long check = sqLiteDatabase.insert(DatabaseHelper.TABLE_USERS, null, values);

                    /*if (check != -1) {
                        Toast.makeText(UsersListActivity.this, "inserted successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(UsersListActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                    }*/


                }
                usersAdapter = new UsersAdapter(UsersListActivity.this, list);
                recyclerView.setAdapter(usersAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                pbLoading.setVisibility(View.GONE);

                Toast.makeText(UsersListActivity.this, "Failed to get User list", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public static List<ResultGetUsers> getSmsList(Context context) {

        SQLiteDatabase demo = new DatabaseHelper(context).getReadableDatabase();
        ArrayList<ResultGetUsers> list = new ArrayList<>();

        Cursor cursor = demo.rawQuery("select * from " + DatabaseHelper.TABLE_USERS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String email = cursor.getString(1);
            String uniqurid = cursor.getString(2);
            String usertoken = cursor.getString(3);

            ResultGetUsers resultGetUsers = new ResultGetUsers(String.valueOf(id), email, usertoken, uniqurid);
            list.add(resultGetUsers);
        }

        return list;

    }
}
