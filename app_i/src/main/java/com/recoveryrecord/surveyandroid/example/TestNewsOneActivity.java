package com.recoveryrecord.surveyandroid.example;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.recoveryrecord.surveyandroid.example.model.News;

import java.util.ArrayList;
import java.util.List;

//import static com.recoveryrecord.surveyandroid.example.Constants.DEFAULT_ESM_PARCELABLE;
//import static com.recoveryrecord.surveyandroid.example.Constants.DEFAULT_NEWS_PARCELABLE;


public class TestNewsOneActivity extends AppCompatActivity {
    private RecyclerView courseRV;
    private ArrayList<News> dataModalArrayList;
    private NewsRecycleViewAdapter dataRVAdapter;
    private FirebaseFirestore db;

    //temp for notification
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("即時新聞");
        setContentView(R.layout.activity_one_news);

        // initializing our variables.
        courseRV = findViewById(R.id.idRVItems);

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // creating our new array list
        dataModalArrayList = new ArrayList<>();
        courseRV.setHasFixedSize(true);

        // adding horizontal layout manager for our recycler view.
//        courseRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        courseRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // adding our array list to our recycler view adapter class.
        dataRVAdapter = new NewsRecycleViewAdapter(dataModalArrayList, this);

        // setting adapter to our recycler view.
        courseRV.setAdapter(dataRVAdapter);

        loadrecyclerViewData();
    }

    private void loadrecyclerViewData() {
//.orderBy("name").limit(3)//                db.collectionGroup("news") //
        db.collection("medias")
                .document("cts")
                .collection("news")
                .orderBy("pubdate", Query.Direction.DESCENDING)
                .limit(50)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding our
                            // progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                // after getting this list we are passing that
                                // list to our object class.
                                News dataModal = d.toObject(News.class);

                                // and we will pass this object class
                                // inside our arraylist which we have
                                // created for recycler view.
                                dataModalArrayList.add(dataModal);
                            }
                            // after adding the data to recycler view.
                            // we are calling recycler view notifyDataSetChanged
                            // method to notify that data has been changed in recycler view.
                            dataRVAdapter.notifyDataSetChanged();
                        } else {
                            // if the snapshot is empty we are
                            // displaying a toast message.
                            Toast.makeText(TestNewsOneActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // if we do not get any data or any error we are displaying
                // a toast message that we do not get any data
                Toast.makeText(TestNewsOneActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_news, menu);
        //getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_5 :
//                scheduleNotification(getNotification("news (5 second delay)" ), 5000 );
//                scheduleNotification_esm(getNotification_esm("Please fill out the questionnaire" ), 30000 );
                return true;
            case R.id.action_10 :
//                scheduleNotification(getNotification("news (10 second delay)" ), 10000 );
//                scheduleNotification_esm(getNotification_esm("Please fill out the questionnaire" ), 30000 );
                return true;
            case R.id.action_30 :
//                scheduleNotification(getNotification("news (30 second delay)" ), 30000 );
//                scheduleNotification_esm(getNotification_esm("Please fill out the questionnaire" ), 30000 );
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void scheduleNotification (Notification notification, int delay) {
////        int nid = (int) System.currentTimeMillis();
////        Log.d("log: notification", "news id" + nid);
//        Intent notificationIntent = new Intent(this, NotificationListenerNews.class);
//        notificationIntent.putExtra(NEWS_CHANNEL_ID, 1 ) ;
//        notificationIntent.putExtra(DEFAULT_NEWS_PARCELABLE, notification) ;
//        int randomNum = ThreadLocalRandom.current().nextInt(0, 1000000 + 1);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast( this, randomNum, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        long futureInMillis = SystemClock.elapsedRealtime() + delay;
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        assert alarmManager != null;
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
//    }
    private Notification getNotification (String content) {
        int nid = (int) System.currentTimeMillis();
        Log.d("log: notification", "news id" + nid);
        Intent intent_news = new Intent();
        intent_news.setClass(TestNewsOneActivity.this, NewsModuleActivity.class);
        intent_news.putExtra("trigger_from", "Notification");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, nid, intent_news, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id);
        builder.setContentTitle("Scheduled NEWS");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build() ;
    }
    private Notification getNotification_esm (String content) {
        int nid = (int) System.currentTimeMillis();
        Log.d("log: notification", "esm id" + nid);
        Intent intent_esm = new Intent();
        intent_esm.setClass(TestNewsOneActivity.this, SurveyActivity.class);
        intent_esm.putExtra("trigger_from", "Notification");
        intent_esm.putExtra("esm_id", System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, nid, intent_esm, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id);
        builder.setContentTitle("Scheduled ESM");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build() ;
    }
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void scheduleNotification_esm (Notification notification, int delay) {
////        int nid = (int) System.currentTimeMillis();
////        Log.d("log: notification", "news id" + nid);
//        Intent notificationIntent = new Intent(this, NotificationListenerNews.class);
//        notificationIntent.putExtra(ESM_CHANNEL_ID, 1 ) ;
//        notificationIntent.putExtra(DEFAULT_ESM_PARCELABLE, notification) ;
//        int randomNum = ThreadLocalRandom.current().nextInt(0, 1000000 + 1);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast( this, randomNum, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        long futureInMillis = SystemClock.elapsedRealtime() + delay;
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        assert alarmManager != null;
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
//    }
}
