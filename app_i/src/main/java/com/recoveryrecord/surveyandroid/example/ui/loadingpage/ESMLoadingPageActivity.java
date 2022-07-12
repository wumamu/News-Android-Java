package com.recoveryrecord.surveyandroid.example.ui.loadingpage;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.recoveryrecord.surveyandroid.example.DbHelper.ESMDbHelper;
import com.recoveryrecord.surveyandroid.example.DbHelper.PushNewsDbHelper;
import com.recoveryrecord.surveyandroid.example.DbHelper.ReadingBehaviorDbHelper;
import com.recoveryrecord.surveyandroid.example.NewsHybridActivity;
import com.recoveryrecord.surveyandroid.example.R;
import com.recoveryrecord.surveyandroid.example.sqlite.ESM;
import com.recoveryrecord.surveyandroid.example.ui.SurveyActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import static com.recoveryrecord.surveyandroid.example.config.Constants.ESM_SAMPLE_TIME;
import static com.recoveryrecord.surveyandroid.example.config.Constants.ESM_TYPE;
import static com.recoveryrecord.surveyandroid.example.config.Constants.LOADING_PAGE_ID;
import static com.recoveryrecord.surveyandroid.example.config.Constants.LOADING_PAGE_TYPE_KEY;
import static com.recoveryrecord.surveyandroid.example.config.Constants.NOTIFICATION_ESM_TYPE_KEY;
import static com.recoveryrecord.surveyandroid.example.config.Constants.NOTIFICATION_TYPE_KEY;
import static com.recoveryrecord.surveyandroid.example.config.Constants.NOTIFICATION_TYPE_VALUE_ESM;
import static com.recoveryrecord.surveyandroid.example.config.Constants.PUSH_ESM_COLLECTION;
import static com.recoveryrecord.surveyandroid.example.config.Constants.PUSH_ESM_NOTI_ARRAY;
import static com.recoveryrecord.surveyandroid.example.config.Constants.PUSH_ESM_READ_ARRAY;
import static com.recoveryrecord.surveyandroid.example.config.Constants.PUSH_ESM_SAMPLE_TIME;
import static com.recoveryrecord.surveyandroid.example.config.Constants.PUSH_ESM_TYPE;
import static com.recoveryrecord.surveyandroid.example.config.Constants.SAMPLE_ID;
import static com.recoveryrecord.surveyandroid.example.config.Constants.SAMPLE_IN;
import static com.recoveryrecord.surveyandroid.example.config.Constants.SAMPLE_MEDIA;
import static com.recoveryrecord.surveyandroid.example.config.Constants.SAMPLE_RECEIEVE;
import static com.recoveryrecord.surveyandroid.example.config.Constants.SAMPLE_TITLE;
import static com.recoveryrecord.surveyandroid.example.config.Constants.SURVEY_PAGE_ID;

//import static com.recoveryrecord.surveyandroid.example.ConstantsOld.PUSH_NEWS_SAMPLE_CHECK_ID;

public class ESMLoadingPageActivity extends AppCompatActivity {
    private Button button;
//    Task task, task2, task3;
//    Boolean exist_read = false, exist_notification = false;
    String esm_id = "", type ="";
    List<String> rb_query = new ArrayList<>();
    List<String> noti_query = new ArrayList<>();
    int type_esm = 3;
//    List<String> not_sample_short = new ArrayList<>();
//    List<String> not_sample_far = new ArrayList<>();
//    List<String> not_sample_far_noti = new ArrayList<>();

    String device_id = "NA";
    Timestamp my_time;

    private ProgressBar pgsBar;
    private int i = 0;
    private TextView txtView;
    private final Handler hdlr = new Handler();
    @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page_esm);
        device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            esm_id = Objects.requireNonNull(b.getString(LOADING_PAGE_ID));
            type = Objects.requireNonNull(b.getString(LOADING_PAGE_TYPE_KEY));
        }
        if(esm_id.equals("")){
            Toast.makeText(this, "系統出錯，幫您導回主頁面", Toast.LENGTH_SHORT).show();
            Intent back = new Intent();
            back.setClass(getApplicationContext(), NewsHybridActivity.class);
            startActivity(back);
        }
        pgsBar = findViewById(R.id.pBar);
        //initial
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(SAMPLE_ID, "NA");
        editor.putString(SAMPLE_TITLE, "NA");
        editor.putString(SAMPLE_MEDIA, "NA");
        editor.putString(SAMPLE_RECEIEVE, "NA");
        editor.putString(SAMPLE_IN, "NA");
//        editor.putInt(ESM_TYPE, 3);
        editor.apply();
        my_time = Timestamp.now();
        rb_query.clear();
//        firestore_query(esm_id);
        sql_query_noti();
//        sql_query_rb();
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> openESM());

        button.setEnabled(false);
        txtView = findViewById(R.id.textView);
        i = pgsBar.getProgress();
        new Thread(() -> {
            while (i < 100) {
                i += 1;
                // Update the progress bar and display the current value in text view
                hdlr.post(() -> {
                    pgsBar.setProgress(i);
//                            txtView.setText(i+"/"+pgsBar.getMax());
                    if(i==100){
                        txtView.setText("資料匯入完成");
                        button.setEnabled(true);
                    }

                });
                try {
                    // Sleep for 100 milliseconds to show the progress slowly.
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void sql_query_noti() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        long mysampletime = sharedPrefs.getLong(ESM_SAMPLE_TIME, 0);
        //noti
        //read
        //noti
        PushNewsDbHelper dbHandler = new PushNewsDbHelper(getApplicationContext());
        boolean noti_read = false, self_read = false, noti_not_read = false;
        int click = 1;//not click
        Cursor cursor_click = dbHandler.getNotiClickDataForESM(mysampletime);
        if (cursor_click.moveToFirst()) {
            while(!cursor_click.isAfterLast()) {
                String news_id = cursor_click.getString(cursor_click.getColumnIndex("news_id"));
                String title = cursor_click.getString(cursor_click.getColumnIndex("title"));
                title = title.replace("\n", "");
                String media = cursor_click.getString(cursor_click.getColumnIndex("media"));
                click = cursor_click.getInt(cursor_click.getColumnIndex("click"));
                switch (media) {
                    case "cna":
                        media = "中央社";
                        break;
                    case "chinatimes":
                        media = "中時";
                        break;
                    case "cts":
                        media = "華視";
                        break;
                    case "ebc":
                        media = "東森";
                        break;
                    case "ltn":
                        media = "自由時報";
                        break;
                    case "storm":
                        media = "風傳媒";
                        break;
                    case "udn":
                        media = "聯合";
                        break;
                    case "ettoday":
                        media = "ettoday";
                        break;
                    case "setn":
                        media = "三立";
                        break;
                }
                long re_time = cursor_click.getLong(cursor_click.getColumnIndex("receieve_timestamp"));
                Date date = new Date();
                date.setTime(re_time*1000);
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                String[] mysplit = formatter.format(date).split(" ");
                noti_query.clear();
                noti_query.add(news_id);
                noti_query.add(title);
                noti_query.add(media);
                noti_query.add(mysplit[2]);
                break;
//                    exist_notification = true;
//                    if(click==0){
//                        //only noti
//                        break;
//                    } else {
//                        Random rand = new Random();
//                        int n = rand.nextInt(3);//0 1 2
//                        if(n==0){
//                            //1/3
//                            break;
//                        }
//                    }

            }
        }
        if (!cursor_click.isClosed())  {
            cursor_click.close();
        }
        if(click==0){
            //exist read find reading behavior
            ReadingBehaviorDbHelper dbHandler_rb = new ReadingBehaviorDbHelper(getApplicationContext());
            Cursor cursor_rb = dbHandler_rb.getReadingDataFromNoti(mysampletime, "\"" + noti_query.get(0) + "\"");
            if (cursor_rb.moveToFirst()) {
                while(!cursor_rb.isAfterLast()) {
                    long in_time = cursor_rb.getLong(cursor_rb.getColumnIndex("in_timestamp"));
                    Date date = new Date();
                    date.setTime(in_time*1000);
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                    String[] myssplit = formatter.format(date).split(" ");
                    Log.d("lognewsselect", formatter.format(date));
                    if(in_time!=0){
                        noti_query.add(myssplit[2]);
                        noti_read = true;
                        break;
                    }
                    cursor_rb.moveToNext();
                }
            }
            if (!cursor_rb.isClosed())  {
                cursor_rb.close();
            }
        }
        //query reading
        //not exist read query self read
        ReadingBehaviorDbHelper dbHandler_rb_self = new ReadingBehaviorDbHelper(getApplicationContext());
        Cursor cursor_rb_self = dbHandler_rb_self.getReadingDataSelf(mysampletime);
        if (cursor_rb_self.moveToFirst()) {
            while(!cursor_rb_self.isAfterLast()) {
                String news_id = cursor_rb_self.getString(cursor_rb_self.getColumnIndex("news_id"));
                String title = cursor_rb_self.getString(cursor_rb_self.getColumnIndex("title"));
                title = title.replace("\n", "");
                String media = cursor_rb_self.getString(cursor_rb_self.getColumnIndex("media"));
                long in_time = cursor_rb_self.getLong(cursor_rb_self.getColumnIndex("in_timestamp"));
                Date date = new Date();
                date.setTime(in_time*1000);
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                String[] myssplit = formatter.format(date).split(" ");
                Log.d("lognewsselect", formatter.format(date));
                if(in_time!=0){
                    rb_query.clear();
                    rb_query.add(news_id);
                    rb_query.add(title);
                    rb_query.add(media);
                    rb_query.add(myssplit[2]);
                    self_read = true;
                    break;
                }
                cursor_rb_self.moveToNext();
            }
        }
        if (!cursor_rb_self.isClosed())  {
            cursor_rb_self.close();
        }
        //alternate two esm
        int last_type = sharedPrefs.getInt(ESM_TYPE, 3);
        Log.d("lognewsselect", "last esm type" + last_type);
        if(noti_read && self_read){
            //同時成立
            if (last_type==0){
                noti_read = false;
                self_read = true;
            } else {
                self_read = false;
            }
        } else if(!noti_read && !self_read){
            noti_not_read = true;
            Cursor cursor_unclick = dbHandler.getNotiUnclickDataForESM(mysampletime);
            if (cursor_unclick.moveToFirst()) {
                while(!cursor_unclick.isAfterLast()) {
                    String news_id = cursor_unclick.getString(cursor_unclick.getColumnIndex("news_id"));
                    String title = cursor_unclick.getString(cursor_unclick.getColumnIndex("title"));
                    title = title.replace("\n", "");
                    String media = cursor_unclick.getString(cursor_unclick.getColumnIndex("media"));
                    switch (media) {
                        case "cna":
                            media = "中央社";
                            break;
                        case "chinatimes":
                            media = "中時";
                            break;
                        case "cts":
                            media = "華視";
                            break;
                        case "ebc":
                            media = "東森";
                            break;
                        case "ltn":
                            media = "自由時報";
                            break;
                        case "storm":
                            media = "風傳媒";
                            break;
                        case "udn":
                            media = "聯合";
                            break;
                        case "ettoday":
                            media = "ettoday";
                            break;
                        case "setn":
                            media = "三立";
                            break;
                    }
                    long re_time = cursor_unclick.getLong(cursor_unclick.getColumnIndex("receieve_timestamp"));
                    Date date = new Date();
                    date.setTime(re_time*1000);
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                    String[] mysplit = formatter.format(date).split(" ");
                    noti_query.clear();
                    noti_query.add(news_id);
                    noti_query.add(title);
                    noti_query.add(media);
                    noti_query.add(mysplit[2]);
                    break;
                }
            }
            if (!cursor_unclick.isClosed())  {
                cursor_unclick.close();
            }

        }
        if(noti_read){
            type_esm = 0;
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(SAMPLE_ID, noti_query.get(0));
            editor.putString(SAMPLE_TITLE, noti_query.get(1));
            editor.putString(SAMPLE_MEDIA, noti_query.get(2));
            editor.putString(SAMPLE_RECEIEVE, noti_query.get(3));
            editor.putString(SAMPLE_IN, noti_query.get(4));
            editor.putInt(ESM_TYPE, type_esm);
            editor.apply();
        } else if (self_read){
            type_esm = 1;
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(SAMPLE_ID, rb_query.get(0));
            editor.putString(SAMPLE_TITLE, rb_query.get(1));
            editor.putString(SAMPLE_MEDIA, rb_query.get(2));
            editor.putString(SAMPLE_IN, rb_query.get(3));
            editor.putInt(ESM_TYPE, type_esm);
            editor.apply();
        } else if (noti_not_read){
            type_esm = 2;
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(SAMPLE_ID, noti_query.get(0));
            editor.putString(SAMPLE_TITLE, noti_query.get(1));
            editor.putString(SAMPLE_MEDIA, noti_query.get(2));
            editor.putString(SAMPLE_RECEIEVE, noti_query.get(3));
            editor.putInt(ESM_TYPE, type_esm);
            editor.apply();
        }
//        Log.d("lognewsselect", String.valueOf(noti_query));
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openESM() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent_esm = new Intent();
        intent_esm.setClass(this, SurveyActivity.class);
        intent_esm.putExtra(SURVEY_PAGE_ID, esm_id);//******************
        intent_esm.putExtra(NOTIFICATION_TYPE_KEY, NOTIFICATION_TYPE_VALUE_ESM);
        intent_esm.putExtra(NOTIFICATION_ESM_TYPE_KEY, type_esm);


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        ESM myesm = new ESM();
        myesm.setKEY_DOC_ID(device_id + " " + esm_id);
        myesm.setKEY_ESM_TYPE(type_esm);
        myesm.setKEY_ESM_SAMPLE_TIME(sharedPrefs.getLong(ESM_SAMPLE_TIME, 0));
        myesm.setKEY_NOTI_SAMPLE(String.join("#", noti_query));
        myesm.setKEY_SELF_READ_SAMPLE(String.join("#", rb_query));
        ESMDbHelper dbHandler_esm = new ESMDbHelper(getApplicationContext());
        dbHandler_esm.UpdatePushESMDetailsClick(myesm);

        if (!esm_id.equals("")){
            final DocumentReference rbRef = db.collection(PUSH_ESM_COLLECTION).document(device_id + " " + esm_id);
            rbRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    assert document != null;
                    if (document.exists()) {
                        rbRef.update(PUSH_ESM_TYPE, type_esm,
                                PUSH_ESM_NOTI_ARRAY, noti_query,
                                PUSH_ESM_READ_ARRAY, rb_query,
                                PUSH_ESM_SAMPLE_TIME, new Timestamp(sharedPrefs.getLong(ESM_SAMPLE_TIME, 0), 0)
                        )//another field
                                .addOnSuccessListener(aVoid -> Log.d("lognewsselect", "DocumentSnapshot successfully updated!"))
                                .addOnFailureListener(e -> Log.w("lognewsselect", "Error updating document", e));
                    } else {
                        Log.d("lognewsselect", "ESMLoadingPageActivity No such document");
                        Toast.makeText(getApplicationContext(), "系統出錯，幫您導回主頁面", Toast.LENGTH_SHORT).show();
                        Intent back = new Intent();
                        back.setClass(getApplicationContext(), NewsHybridActivity.class);
                        startActivity(back);
                    }
                } else {
                    Log.d("lognewsselect", "get failed with ", task.getException());
                    Toast.makeText(getApplicationContext(), "系統出錯，幫您導回主頁面", Toast.LENGTH_SHORT).show();
                    Intent back = new Intent();
                    back.setClass(getApplicationContext(), NewsHybridActivity.class);
                    startActivity(back);
                }
            });
        }
        esm_id = "";
        startActivity(intent_esm);
    }

//    private void firestore_query(final String esm_id_in) {
//        final FirebaseFirestore db = FirebaseFirestore.getInstance();
//        @SuppressLint("HardwareIds")
//        final String device_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
//        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
////        final Long now = System.currentTimeMillis();
//        final Timestamp now_time_stamp = Timestamp.now();
//
//        task =   db.collection(READING_BEHAVIOR_COLLECTION)
//                .whereEqualTo(READING_BEHAVIOR_DEVICE_ID, device_id)
//                .whereEqualTo(READING_BEHAVIOR_SAMPLE_CHECK, false)
//                .orderBy(READING_BEHAVIOR_OUT_TIME, Query.Direction.DESCENDING)
//                .get();
//        task2 =  db.collection(PUSH_NEWS_COLLECTION)
//                .whereEqualTo(PUSH_NEWS_DEVICE_ID, device_id)
//                .whereEqualTo(PUSH_NEWS_CLICK, 1)
//                .orderBy(PUSH_NEWS_NOTI_TIME, Query.Direction.DESCENDING)
//                .get();
////
////        task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////            @Override
////            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//////                        List<String> news_title_array_add = new ArrayList<>();
////                if (!queryDocumentSnapshots.isEmpty()) {
//////                    news_title_target_array.clear();
////                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
////                    for (DocumentSnapshot d : list) {
////                        if(!(d.getString(READING_BEHAVIOR_TITLE) ==null) && !d.getString(READING_BEHAVIOR_TITLE).equals("NA")){
////                            if (d.getDouble(READING_BEHAVIOR_TIME_ON_PAGE)>= ESM_TIME_ON_PAGE_THRESHOLD) {
////                                if (Math.abs(now_time_stamp.getSeconds() - d.getTimestamp(READING_BEHAVIOR_IN_TIME).getSeconds()) <= ESM_TARGET_RANGE) {
//////                                    news_title_target_array.add(d.getString(READING_BEHAVIOR_TITLE) + "¢" + share_var + "¢" + trigger_var + "¢" + cat_var + "¢" + formatter.format(date) + "¢" + d.getString(READING_BEHAVIOR_NEWS_ID));
////                                } else {
////                                    not_sample_far.add(d.getString(READING_BEHAVIOR_TITLE));
////                                }
////                            } else {
////                                not_sample_short.add(d.getString(READING_BEHAVIOR_TITLE));
////                            }
////                        }
////                        //mark as check
////                        db.collection(READING_BEHAVIOR_COLLECTION)
////                                .document(d.getId())
////                                .update(READING_BEHAVIOR_SAMPLE_CHECK, true,
////                                        READING_BEHAVIOR_SAMPLE_CHECK_ID, tmp_esm_id)
////                                .addOnSuccessListener(new OnSuccessListener<Void>() {
////                                    @Override
////                                    public void onSuccess(Void aVoid) {
////                                        Log.d("lognewsselect", "DocumentSnapshot successfully updated!");
////                                    }
////                                })
////                                .addOnFailureListener(new OnFailureListener() {
////                                    @Override
////                                    public void onFailure(@NonNull Exception e) {
////                                        Log.w("lognewsselect", "Error updating document", e);
////                                    }
////                                });
////                    }
////                }
////                Log.d("lognewsselect", "**********COMPLETE TASK1");
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                Log.d("lognewsselect", String.valueOf(e));
////            }
////        });
////
////        task2.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////            @Override
////            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//////                notification_unclick_array.clear();
////                if (!queryDocumentSnapshots.isEmpty()) {
////                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
////                    for (DocumentSnapshot d : list) {
////                        if (Math.abs(now_time_stamp.getSeconds() - d.getTimestamp(PUSH_NEWS_NOTI_TIME).getSeconds()) <= NOTIFICATION_TARGET_RANGE) {
////                        } else {
////                            not_sample_far_noti.add(d.getString(PUSH_NEWS_TITLE));
////                        }
////                        //mark as check
//////                        db.collection(TEST_USER_COLLECTION)
////                        db.collection(PUSH_NEWS_COLLECTION)
////                                .document(d.getId())
////                                .update(PUSH_NEWS_CLICK, 4,
////                                        PUSH_NEWS_SAMPLE_CHECK_ID, tmp_esm_id)
////                                .addOnSuccessListener(new OnSuccessListener<Void>() {
////                                    @Override
////                                    public void onSuccess(Void aVoid) {
////                                        Log.d("lognewsselect", "DocumentSnapshot successfully updated!");
////                                    }
////                                })
////                                .addOnFailureListener(new OnFailureListener() {
////                                    @Override
////                                    public void onFailure(@NonNull Exception e) {
////                                        Log.w("lognewsselect", "Error updating document", e);
////                                    }
////                                });
////                    }
////                }
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                Log.d("lognewsselect", String.valueOf(e));
////            }
////        });
////        Log.d("lognewsselect", "select news finish################################# ");
//    }

    @Override
    protected void onResume() {
        if(esm_id.equals("")){
            Intent back = new Intent();
            back.setClass(this, NewsHybridActivity.class);
            startActivity(back);
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(com.recoveryrecord.surveyandroid.R.string.close_survey)
                .setMessage(com.recoveryrecord.surveyandroid.R.string.are_you_sure_you_want_to_close)
                .setNeutralButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss()).setPositiveButton(android.R.string.ok, (dialog, which) -> ESMLoadingPageActivity.super.onBackPressed()).show();
    }
}