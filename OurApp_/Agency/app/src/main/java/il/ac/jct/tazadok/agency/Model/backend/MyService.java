package il.ac.jct.tazadok.agency.Model.backend;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Talush122 on 04/03/2017.
 */

public class MyService extends Service{

        final  String TAG = "myservice";
        boolean isThread=false;
        public MyService() {
        }

        @Override
        public IBinder onBind(Intent intent)
        {
            Log.d(TAG,"onBind");
            return  null;
        }
        @Override
        public void onCreate() {
            super.onCreate();

            Log.d(TAG,"onCreate");
        }
        @Override
        public void onDestroy() {
            Log.d(TAG,"onDestroy");
            isThread=false;
            Toast.makeText(this, "The Service was destroyed", Toast.LENGTH_SHORT).show();
            super.onDestroy();
        }

        @Override
        public int onStartCommand(final Intent intent, int flags, int startId)
        {
            Log.d(TAG,"onStartCommand");
            if (!isThread)
            {
                Toast.makeText(this, "Service Has Started", Toast.LENGTH_LONG).show();
                isThread = true;
                Thread t = new Thread()
                {
                    @Override
                    public void run() {
                        while (true)
                        {
                            try
                            {
                                Thread.sleep(10000);
                                Log.d(TAG, "thread running ..");
                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            if (DB_ManagerFactory.getManager().is_added_activity_()) {
                                Log.d(TAG, "new activity running ..");
                                Intent intent1 = new Intent("il.ac.jct.tazadok.agency.AddActivity_");
                                MyService.this.sendBroadcast(intent1);
                            }
                            if (DB_ManagerFactory.getManager().is_added_business()) {
                                Log.d(TAG, "new business running ..");
                                Intent intent1 = new Intent("il.ac.jct.tazadok.agency.AddBuisness");
                                MyService.this.sendBroadcast(intent1);
                            }
                        }
                    }
                };
                t.start();
            }
            else
                Toast.makeText(this, "service-onStart: thread is already running", Toast.LENGTH_LONG).show();

            return START_STICKY;
        }

}
