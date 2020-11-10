package il.ac.jct.tazadok.agency.Model.backend;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Talush122 on 04/03/2017.
 */

public class MyReceiver  extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.d("my service" , "onReceive");

        Toast.makeText(context,intent.getAction(),Toast.LENGTH_LONG).show();
    }
}
