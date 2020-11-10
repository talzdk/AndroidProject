package il.ac.jct.tazadok.agency.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import il.ac.jct.tazadok.agency.Model.backend.DB_ManagerFactory;
import il.ac.jct.tazadok.agency.Model.datasource.Lists;
import il.ac.jct.tazadok.agency.Model.entities.User;
import il.ac.jct.tazadok.agency.R;

public class ActivityOptions extends AppCompatActivity {

    private Button active_button;
    private Button business_button;
    private User currentUser;
    private ProgressBar pbar;
    final String TAG = "ActivityOp(activity)";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Intent intent=getIntent();
        currentUser = (User) intent.getSerializableExtra("Current user");
        Log.d(TAG, "current user " + currentUser);
        final TextView currentUserTextBox = (TextView) findViewById(R.id.currentUserLabel);
        currentUserTextBox.setText("Hello " + currentUser.getUser_name() +"!");
        findViews();
        pbar = (ProgressBar)findViewById(R.id.progressBar4);
        pbar.setVisibility(View.GONE);
        active_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    pbar.setVisibility(View.VISIBLE);
                    Intent intent1 = new Intent(ActivityOptions.this,AddActivity_.class);
                    intent1.putExtra("Current user", currentUser);
                    startActivity(intent1);
                }
                catch (Exception e)
                {
                    Toast.makeText(ActivityOptions.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        business_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    pbar.setVisibility(View.VISIBLE);
                    Intent intent1 = new Intent(ActivityOptions.this,AddBusiness.class);
                    intent1.putExtra("Current user", currentUser);
                    startActivity(intent1);
                }
                catch (Exception e)
                {
                    Toast.makeText(ActivityOptions.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void findViews() {
        active_button = (Button) findViewById(R.id.newActiveButton);
        business_button = (Button) findViewById(R.id.newBuisnessButton);

    }
    @Override
    public void onBackPressed()
    {
        pbar.setVisibility(View.VISIBLE);
        Intent myIntent = new Intent(ActivityOptions.this, MainActivity.class);
        startActivity(myIntent);
    }
}
