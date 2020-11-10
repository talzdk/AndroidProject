package il.ac.jct.tazadok.agency.Controller;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import il.ac.jct.tazadok.agency.Model.backend.Contact;
import il.ac.jct.tazadok.agency.Model.datasource.Lists;
import il.ac.jct.tazadok.agency.Model.datasource.Tools;
import il.ac.jct.tazadok.agency.Model.entities.Code_;
import il.ac.jct.tazadok.agency.Model.entities.User;
import il.ac.jct.tazadok.agency.R;

public class AddUser extends AppCompatActivity {
    private Button sign_up_button;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private EditText editTextPasswordV;
    private ProgressBar pbar;
    final String TAG = "AddUser (activity)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        findViews();
        pbar = (ProgressBar)findViewById(R.id.progressBar5);
        pbar.setVisibility(View.GONE);
        EditText editText = (EditText) findViewById(R.id.editTextUserName);
        editText.requestFocus();
        final Uri uri = Contact.User.USER_URI;
        final Uri uric = Contact.Code.CODE_URI;
        final int MAX_SIZE=6;
        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pbar.setVisibility(View.VISIBLE);
                final String name=editTextUserName.getText().toString();
                final String p=editTextPassword.getText().toString();
                String pv=editTextPasswordV.getText().toString();
                //check name & passwords

                if(name.length()>MAX_SIZE)
                {
                    editTextUserName.setText(null);
                    Toast.makeText(v.getContext(), "name is too long", Toast.LENGTH_LONG).show();
                    return;
                }

                if(p.length()>MAX_SIZE)
                {
                    editTextPassword.setText(null);
                    Toast.makeText(v.getContext(), "password is too long", Toast.LENGTH_LONG).show();
                    return;
                }

                if(name.length()==0)
                {
                    Toast.makeText(v.getContext(), "you forgot enter your name ", Toast.LENGTH_LONG).show();
                    return;
                }

                if(p.length()==0)
                {
                    Toast.makeText(v.getContext(), "you forgot enter your password", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pv.matches(p)==false)
                {
                    editTextPasswordV.setText(null);
                    Toast.makeText(v.getContext(), "the passwords are not the same", Toast.LENGTH_LONG).show();
                    return;
                }
                //check if there is an user with the same name
                final String s=editTextUserName.getText().toString();
                final User u = new User(name,p);
                final Code_ c = new Code_();
                try {
                    AsyncTask<Void, Void, Boolean> async = new AsyncTask<Void, Void, Boolean>() {
                        boolean ok=true;
                        boolean doesExist=false;
                        @Override
                        protected Boolean doInBackground(Void... params) {

                                Cursor cursorc = getContentResolver().query(uric, null, null, null, null);
                            cursorc.moveToFirst();
                            Log.d(TAG, "the returned cursor: " + cursorc.getString(2));
                                u.setUser_code(Integer.parseInt(cursorc.getString(2)));
                            Log.d(TAG, "the returned cursor: " + cursorc.getString(0));
                                c.setAcode(Integer.parseInt(cursorc.getString(0)));
                            c.setBcode(Integer.parseInt(cursorc.getString(1)));
                            c.setUcode((int)(u.getUser_code()));
                                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                                Log.d(TAG, "the returned cursor: " + cursor);
                                while (cursor.moveToNext()) {
                                    Log.d(TAG, "in the while");
                                    if (cursor.getString(1).equals(s))//the user already exists
                                    {
                                        Log.d(TAG, "in the if that is in the while");
                                        doesExist= true;
                                        return false;
                                    }
                                }
                            try {
                                if(!doesExist) {
                                getContentResolver().insert(uri, Tools.UserToContentValues(u));
                                    getContentResolver().update(uric, Tools.CodeToContentValues(c),null,null);
                                return true;
                                }

                            }
                            catch (Exception e)
                            {
                                Log.d(TAG, "in exception      " );
                                ok=false;
                                return false;
                            }
                            return false;
                        }
                        @Override
                        protected void onPostExecute(Boolean correct) {
                            try {
                                if (!correct) {
                                    pbar.setVisibility(View.GONE);
                                    throw new Exception("This UserName is Taken");
                                }
                                else {
                                    pbar.setVisibility(View.GONE);
                                    Toast.makeText(AddUser.this, "User added successfully ", Toast.LENGTH_LONG).show();
                                    Intent myIntent = new Intent(AddUser.this, ActivityOptions.class);
                                    myIntent.putExtra("Current user", u);
                                    startActivity(myIntent);
                                }
                            } catch (Exception e) {
                                Toast.makeText(AddUser.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }.execute();
                }
                catch (Exception e){Toast.makeText(AddUser.this, "Error!", Toast.LENGTH_LONG).show();}

            }

        });

    }
    private void findViews() {
        sign_up_button = (Button) findViewById(R.id.signUpButton);
        editTextUserName= (EditText) findViewById(R.id.editTextUserName);
        editTextPassword= (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordV= (EditText) findViewById(R.id.editTextPasswordV);


    }
    @Override
    public void onBackPressed()
    {
        pbar.setVisibility(View.VISIBLE);
        Intent myIntent = new Intent(AddUser.this, MainActivity.class);
        startActivity(myIntent);
    }
    }

