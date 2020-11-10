package il.ac.jct.tazadok.agency.Controller;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import il.ac.jct.tazadok.agency.Model.backend.Contact;
import il.ac.jct.tazadok.agency.Model.backend.DB_Manager;
import il.ac.jct.tazadok.agency.Model.backend.DB_ManagerFactory;
import il.ac.jct.tazadok.agency.Model.datasource.Tools;
import il.ac.jct.tazadok.agency.Model.entities.Activity_;
import il.ac.jct.tazadok.agency.Model.entities.Business;
import il.ac.jct.tazadok.agency.Model.entities.Code_;
import il.ac.jct.tazadok.agency.Model.entities.Description;
import il.ac.jct.tazadok.agency.Model.entities.User;
import il.ac.jct.tazadok.agency.R;

public class AddActivity_ extends AppCompatActivity {


    private EditText editTextBuisnessId;
    private EditText editTextCost;
    private EditText editTextDescription;
    private EditText editTextCountryName;
    private Spinner spinnerType;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat dateFormatter2;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialog2;
    private TextView textViewStartDate;
    private TextView textViewFinishDate;
    private Spinner spinnerDes;
    private Button Addbutton;
    private User currentUser;
    private ProgressBar pbar;
    final Uri uric = Contact.Code.CODE_URI;
    final String TAG = "AddActivity (activity)";

    @TargetApi(Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_);
        findViews();
        pbar = (ProgressBar)findViewById(R.id.progressBar3);
        pbar.setVisibility(View.GONE);
        final EditText editText = (EditText) findViewById(R.id.editTextBusinessId);
        editText.requestFocus();
        Intent intent=getIntent();
        currentUser = (User) intent.getSerializableExtra("Current user");
        init();

        final Uri uri = Contact.Activity_.ACTIVITY_URI;
        final Uri uriB = Contact.Business.BUSINESS_URI;

        spinnerType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Description.values()));
        Addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pbar.setVisibility(View.VISIBLE);
                if (editTextCountryName.getText().toString().equals(""))
                {
                    Toast.makeText(AddActivity_.this, "country name is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(editTextCost.getText().equals(""))
                {
                    Toast.makeText(AddActivity_.this, "price is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(editTextDescription.getText().equals(""))
                {
                    Toast.makeText(AddActivity_.this, "description is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(editTextBuisnessId.getText().equals(""))
                {
                    Toast.makeText(AddActivity_.this, "business id is empty", Toast.LENGTH_LONG).show();
                    return;
                }


                final String businessId= editTextBuisnessId.getText().toString();

                final Activity_ a1 = new Activity_( spinnerType.getSelectedItem().toString(),
                        editTextCountryName.getText().toString(),
                        textViewStartDate.getText().toString(),
                        textViewFinishDate.getText().toString(),
                        Double.parseDouble(editTextCost.getText().toString()),
                        editTextDescription.getText().toString(),
                        Long.parseLong(editTextBuisnessId.getText().toString())

                );
                final Code_ c = new Code_();
                //check if there is a business with the same name
                AsyncTask<Void, Void, Boolean> async = new AsyncTask<Void, Void, Boolean>() {
                    boolean ok=true;
                    boolean doesExist=false;
                    @Override
                    protected Boolean doInBackground(Void... params) {

                        Log.d(TAG, "in doinback before query" );
                        Cursor cursorc = getContentResolver().query(uric, null, null, null, null);
                        cursorc.moveToFirst();
                        a1.setCode(Integer.parseInt(cursorc.getString(0)));
                        c.setAcode((int) a1.getCode());
                        c.setBcode(Integer.parseInt(cursorc.getString(1)));
                        c.setUcode(Integer.parseInt(cursorc.getString(2)));
                        Cursor cursor=  getContentResolver().query(uriB, null, null, null, null);
                        Log.d(TAG, "cursor after the query       "+ cursor );

                        boolean check =cursor.moveToNext();
                        Log.d(TAG, "check       "+ check );

                        while (check)
                        {
                            Log.d(TAG, "in while       " );
                            if (cursor.getString(0).equals(businessId))//this business really exists
                            {
                                Log.d(TAG, "in if       " );
                                doesExist= true;
                            }
                            Log.d(TAG, "after if       " );
                            check =cursor.moveToNext();
                            Log.d(TAG, "check       "+ check );
                        }
                        Log.d(TAG, "after while       " );
                        try {
                            if(doesExist) {
                                Log.d(TAG, "in condition in try       ");
                                final ContentValues contentValues = new ContentValues(Tools.ActiveToContentValues(a1));
                                Log.d(TAG, "contentValues       " + contentValues);
                                Log.d(TAG, "the uri     :        " + uri);
                                Log.d(TAG, "b4 inset       ");
                                getContentResolver().insert(uri, contentValues);

                                Log.d(TAG, "after insert       ");
                                getContentResolver().update(uric, Tools.CodeToContentValues(c),null,null);
                                return true;
                                }
                            else return false;

                        }
                        catch (Exception e)
                        {
                            Log.d(TAG, "in exception      " );
                            ok=false;
                            return false;
                        }
                    }
                    @Override
                    protected void onPostExecute(Boolean correct) {
                        try {
                            if(doesExist==false ) {
                                pbar.setVisibility(View.GONE);
                                editTextCountryName.setText("");
                                textViewStartDate.setText("Choose Date Here");
                                textViewFinishDate.setText("Choose Date Here");
                                editTextCost.setText("");
                                editTextDescription.setText("");
                                editTextBuisnessId.setText("");

                                Toast.makeText(AddActivity_.this, "Business doesn't Exist", Toast.LENGTH_LONG).show();
                            }
                             else if (ok==false)
                            {
                                pbar.setVisibility(View.GONE);
                                Toast.makeText(AddActivity_.this, "ERROR", Toast.LENGTH_LONG).show();
                            }

                            else {
                                pbar.setVisibility(View.GONE);
                                Log.d(TAG, "in else      " );
                                Toast.makeText(AddActivity_.this, " activity number"+a1.getCode()+"added successfully", Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(AddActivity_.this, ActivityOptions.class);
                                myIntent.putExtra("Current user", currentUser);
                                startActivity(myIntent);
                            }
                        } catch (Exception e) {
                            Log.d(TAG, "in exce      " );
                            pbar.setVisibility(View.GONE);
                            Toast.makeText(AddActivity_.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();

            }

        });
    }
    private void findViews() {

        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        editTextBuisnessId = (EditText) findViewById(R.id.editTextBusinessId);
        editTextCost = (EditText) findViewById(R.id.editTextCost);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextCountryName = (EditText) findViewById(R.id.editTextCountryName);
        textViewStartDate = (TextView) findViewById(R.id.textViewStartDate);
        textViewFinishDate = (TextView) findViewById(R.id.textViewFinishDate);
        Addbutton = (Button) findViewById(R.id.Addbutton);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {

        dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();

                newDate.set(year, monthOfYear, dayOfMonth);

                textViewStartDate.setText(dateFormatter.format(newDate.getTime()));
            }

        }, 2017, 1, 1);

        textViewStartDate.setFocusable(false);
        textViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }

        });



        dateFormatter2 = new SimpleDateFormat("yyyy/MM/dd");

        datePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                textViewFinishDate.setText(dateFormatter2.format(newDate.getTime()));
            }

        }, 2017, 1, 1);

        textViewFinishDate.setFocusable(false);
        textViewFinishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog2.show();
            }

        });


    }
    @Override
    public void onBackPressed()
    {
        pbar.setVisibility(View.VISIBLE);
        Intent myIntent = new Intent(AddActivity_.this, ActivityOptions.class);
        myIntent.putExtra("Current user", currentUser);
        startActivity(myIntent);
    }
}
