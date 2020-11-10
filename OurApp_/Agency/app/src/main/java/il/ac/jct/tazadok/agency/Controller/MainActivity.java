package il.ac.jct.tazadok.agency.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import il.ac.jct.tazadok.agency.Model.backend.Contact;
import il.ac.jct.tazadok.agency.Model.backend.DB_Manager;
import il.ac.jct.tazadok.agency.Model.backend.DB_ManagerFactory;
import il.ac.jct.tazadok.agency.Model.backend.MyService;
import il.ac.jct.tazadok.agency.Model.datasource.PHP_Tools;
import il.ac.jct.tazadok.agency.Model.datasource.Tools;
import il.ac.jct.tazadok.agency.Model.datasource.Toolss;
import il.ac.jct.tazadok.agency.Model.entities.User;
import il.ac.jct.tazadok.agency.R;

public class MainActivity extends AppCompatActivity {

    private Button add_user_button;
    private Button log_in_button;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private User userInTheSystem = null;
    private ProgressBar pbar;
    final String TAG = "MainActivity";

    SharedPreferences sharedpreferences;

    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Password = "passwordKey";


    private void findViews() {
        add_user_button = (Button) findViewById(R.id.add_user_button);
        log_in_button = (Button) findViewById(R.id.logInButton);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getuserPreference();
        Log.d(TAG, "b4 pbar ");
        pbar = (ProgressBar) findViewById(R.id.progressBar);
        Log.d(TAG, "after pbar ");
        pbar.setVisibility(View.GONE);
        EditText editText = (EditText) findViewById(R.id.editTextUserName);
        editText.requestFocus();
        findViews();

            final Uri uri = Contact.User.USER_URI;
        final Uri urib = Contact.Business.BUSINESS_URI;
            log_in_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final String name = editTextUserName.getText().toString();
                    final String password = editTextPassword.getText().toString();
                    pbar.setVisibility(View.VISIBLE);
                    try {
                        if (editTextUserName.getText().toString().equals("") || editTextPassword.getText().toString().equals((""))) {
                            throw new Exception("you did not enter name or password");
                        }
                        new AsyncTask<Void, Void, Boolean>() {
                            @Override
                            protected Boolean doInBackground(Void... params) {
                                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                                Cursor cursor2 = getContentResolver().query(urib, null, null, null, null);
                                Toolss.list = Tools.CursorToBusinessList(cursor2);
                                Log.d(TAG, "the returned cursor: " + cursor);
                                Log.d(TAG, "the returned cursor number of cols: " + cursor.getCount());
                                boolean check = cursor.moveToNext();
                                Log.d(TAG, "the move to next return: " + check);
                                while (check) {
                                    Log.d(TAG, "in the while");
                                    if (cursor.getString(1).equals(name) && cursor.getString(2).equals(password)) {
                                        Log.d(TAG, "in the if that is in the while");
                                        userInTheSystem = new User(cursor.getString(1), cursor.getString(2));
                                        return true;
                                    }
                                    check = cursor.moveToNext();
                                    Log.d(TAG, "the move to next return: " + check);
                                }
                                Log.d(TAG, "out of the while");
                                return false;
                            }

                            @Override
                            protected void onPostExecute(Boolean correct) {

                                try {
                                    if (!correct) {

                                        editTextUserName.setText("");
                                        editTextPassword.setText("");

                                        pbar.setVisibility(View.GONE);
                                        throw new Exception("User name or password is incorrect or not exist");
                                    } else {
                                        sharedpref();
                                        editTextUserName.setText("");
                                        editTextPassword.setText("");
                                        Intent intent = new Intent(MainActivity.this, ActivityOptions.class);
                                        intent.putExtra("Current user", userInTheSystem);
                                        startActivity(intent);
                                        startService(new Intent(MainActivity.this, MyService.class));
                                    }
                                } catch (Exception e) {
                                    editTextUserName.setText("");
                                    editTextPassword.setText("");
                                    pbar.setVisibility(View.GONE);
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }.execute();

                    } catch (Exception e) {
                        pbar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            add_user_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        pbar.setVisibility(View.VISIBLE);
                        startActivity(new Intent(MainActivity.this, AddUser.class));
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });

        }


    public void getuserPreference()
    {
        SharedPreferences pref = getSharedPreferences(mypreference,MODE_PRIVATE);
        String username = pref.getString("username","");
        String password = pref.getString("password","");
        EditText editText = (EditText) findViewById(R.id.editTextUserName);
        EditText editText2 = (EditText) findViewById(R.id.editTextPassword);
        editText.setText(username);
        editText2.setText(password);
    }
    public void sharedpref() {
        Log.d(TAG, "in sharedpref");
        String name= editTextUserName.getText().toString();
        Log.d(TAG, "in sharedpref, username" + name);
        String password=editTextPassword.getText().toString();
        Log.d(TAG, "in sharedpref, password" + password);
        SharedPreferences  sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("username",name);
        editor.putString("password",password);
        editor.commit();
    }


}
