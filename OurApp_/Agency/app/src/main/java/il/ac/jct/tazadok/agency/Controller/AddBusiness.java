package il.ac.jct.tazadok.agency.Controller;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import il.ac.jct.tazadok.agency.Model.backend.Contact;
import il.ac.jct.tazadok.agency.Model.backend.DB_Manager;
import il.ac.jct.tazadok.agency.Model.backend.DB_ManagerFactory;
import il.ac.jct.tazadok.agency.Model.datasource.Lists;
import il.ac.jct.tazadok.agency.Model.datasource.Tools;
import il.ac.jct.tazadok.agency.Model.entities.Business;
import il.ac.jct.tazadok.agency.Model.entities.Code_;
import il.ac.jct.tazadok.agency.Model.entities.User;
import il.ac.jct.tazadok.agency.R;

public class AddBusiness extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private EditText editTextEmail;
    private EditText editTextLink;
    private Button buttonAdd;
    private User currentUser;
    private ProgressBar pbar;
    final Uri uric = Contact.Code.CODE_URI;
    final String TAG = "AddBusiness (activity)";
    boolean flag=true;//check
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);
        findViews();
        pbar = (ProgressBar)findViewById(R.id.progressBar2);
        pbar.setVisibility(View.GONE);
        Log.d(TAG, "on create" );
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("Current user");
        EditText editText = (EditText) findViewById(R.id.editTextName);
        editText.requestFocus();


        final Uri uri = Contact.Business.BUSINESS_URI;


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final String name=editTextName.getText().toString();
                final String email=editTextEmail.getText().toString();
                final String phone=editTextPhone.getText().toString();
                final String web=editTextLink.getText().toString();
                pbar.setVisibility(View.VISIBLE);

                if(Patterns.PHONE.matcher(phone).matches()==false)
                {
                    editTextPhone.setText(null);
                    Toast.makeText(AddBusiness.this, "phone isn't valid ", Toast.LENGTH_LONG).show();
                    return;
                }
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()==false)
                {
                    editTextEmail.setText(null);
                    Toast.makeText(AddBusiness.this, "email isn't valid ", Toast.LENGTH_LONG).show();
                    return;
                }
                if(Patterns.WEB_URL.matcher(web).matches()==false)
                {
                    editTextLink.setText(null);
                    Toast.makeText(AddBusiness.this, "link isn't valid ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (editTextName.getText().toString()==null)
                {
                    Toast.makeText(AddBusiness.this, "name is empty", Toast.LENGTH_LONG).show();
                    buttonAdd.setEnabled(false);
                    flag=false;
                    return;
                }
                if(editTextAddress.getText()==null)
                {
                    editTextAddress.setText(null);
                    Toast.makeText(AddBusiness.this, "address is empty", Toast.LENGTH_LONG).show();
                    buttonAdd.setEnabled(false);
                    flag=false;
                    return;
                }
                if(editTextPhone.getText()==null)
                {
                    editTextPhone.setText(null);
                    Toast.makeText(AddBusiness.this, "phone is empty", Toast.LENGTH_LONG).show();
                    buttonAdd.setEnabled(false);
                    flag=false;
                    return;
                }
                if(editTextEmail.getText()==null)
                {
                    editTextEmail.setText(null);
                    Toast.makeText(AddBusiness.this, "email is empty", Toast.LENGTH_LONG).show();
                    buttonAdd.setEnabled(false);
                    flag=false;
                    return;
                }
                if(editTextLink.getText()==null)
                {
                    editTextLink.setText(null);
                    Toast.makeText(AddBusiness.this, "link is empty", Toast.LENGTH_LONG).show();
                    buttonAdd.setEnabled(false);
                    flag=false;
                    return;
                }
                if (flag==true){buttonAdd.setEnabled(true);}

                empty_fields();//check

                final Business b1 = new Business(
                        editTextName.getText().toString(),
                        editTextAddress.getText().toString(),
                        editTextPhone.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextLink.getText().toString()
                );
                Log.d(TAG, "the new b:\n     id     " +  b1.getId() +"\n      name        " + b1.getName() +"\n      addr        " + b1.getAddress() +"\n      phone        " + b1.getPhone() +"\n      web        " + b1.getWebSite() +" \n     em        " + b1.getEmail());
                final Code_ c = new Code_();

                //check if there is a business with the same name
                AsyncTask<Void, Void, Boolean> async = new AsyncTask<Void, Void, Boolean>() {
                    boolean ok=true;
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        Cursor cursorc = getContentResolver().query(uric, null, null, null, null);
                        cursorc.moveToFirst();
                        Log.d(TAG, "entered to doinbackground: ");
                        b1.setId(Integer.parseInt(cursorc.getString(1)));
                        c.setAcode(Integer.parseInt(cursorc.getString(0)));
                        c.setBcode((int) b1.getId());
                        c.setUcode(Integer.parseInt(cursorc.getString(2)));
                        Cursor cursor= getContentResolver().query(uri, null, null, null, null);
                        Log.d(TAG, "after the getresolver.query");
                        Log.d(TAG, "the cursor is: " + cursor);
                        boolean check =cursor.moveToNext();
                        Log.d(TAG, "move to next is: " + check);
                        while (check)
                        {
                            Log.d(TAG, "in the while loop " );
                            if (cursor.getString(1).equals(name))//this business already exists
                                return false;
                            check =cursor.moveToNext();
                            Log.d(TAG, "move to next is: " + check);
                        }
                        Log.d(TAG, "outside of the while loop " );

                        try {
                            final ContentValues contentValues = new ContentValues(Tools.BusinessToContentValues(b1));
                            Log.d(TAG, "the uri     :        " + uri);
                            getContentResolver().insert(uri, contentValues);
                            getContentResolver().update(uric, Tools.CodeToContentValues(c),null,null);
                        }
                        catch (Exception e)
                        {
                            ok=false;
                        }

                        return true;
                    }
                    @Override
                    protected void onPostExecute(Boolean correct) {
                        try {
                            editTextName.setText("");
                            editTextAddress.setText("");
                            editTextPhone.setText("");
                            editTextEmail.setText("");
                            editTextLink.setText("");
                            pbar.setVisibility(View.GONE);
                            if (!correct)
                            {
                                throw new Exception("this business already exists");
                            }

                            else {
                                if(ok) {
                                    Toast.makeText(AddBusiness.this, " business number" + b1.getId() + "added successfully", Toast.LENGTH_LONG).show();
                                    Intent myIntent = new Intent(AddBusiness.this, ActivityOptions.class);
                                    myIntent.putExtra("Current user", currentUser);
                                    startActivity(myIntent);
                                }
                                else {
                                    Toast.makeText(AddBusiness.this, "something went wrong", Toast.LENGTH_LONG).show();
                                }

                            }
                        } catch (Exception e) {
                            Toast.makeText(AddBusiness.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();


            }

        });

    }

    private void empty_fields() {
        //checks


    }

    private void findViews() {

        editTextName=(EditText) findViewById(R.id.editTextName);
        editTextAddress=(EditText) findViewById(R.id.editTextAddress);
        editTextPhone=(EditText) findViewById(R.id.editTextPhone);
        editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        editTextLink=(EditText) findViewById(R.id.editTextLink);
        buttonAdd=(Button) findViewById(R.id.buttonAddBusiness);
    }
    @Override
    public void onBackPressed()
    {
        pbar.setVisibility(View.VISIBLE);
        Intent myIntent = new Intent(AddBusiness.this, ActivityOptions.class);
        myIntent.putExtra("Current user", currentUser);
        startActivity(myIntent);
    }
}
