package com.recoveryrecord.surveyandroid.example.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.recoveryrecord.surveyandroid.example.sqlite.ReadingBehavior;

public class ReadingBehaviorDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reading_behavior.db";
    private static final String TABLE_NAME_READING_BEHAVIOR = "reading_behavior";
    private static final String KEY_ID = "id";
    private static final String KEY_NEWS_ID = "news_id";
    private static final String KEY_DOC_ID = "doc_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_MEDIA = "media";
    private static final String KEY_TRIGGER_BY = "trigger_by";
    private static final String KEY_IN_TIMESTAMP = "in_timestamp";
    private static final String KEY_OUT_TIMESTAMP = "out_timestamp";
    private static final String KEY_CONTENT_LENGTH = "content_length";
    private static final String KEY_DISPLAY_WIDTH = "display_width";
    private static final String KEY_DISPLAY_HEIGHT = "display_height";
    private static final String KEY_TIME_ON_PAGE = "time_on_page";
    private static final String KEY_PAUSE_ON_PAGE = "pause_on_page";
    private static final String KEY_VIEW_PORT_NUM = "view_port_num";
    private static final String KEY_VIEW_PORT_RECORD = "view_port_record";
    private static final String KEY_FLING_NUM = "fling_num";
    private static final String KEY_FLING_RECORD = "fling_record";
    private static final String KEY_DRAG_NUM = "drag_num";
    private static final String KEY_DRAG_RECORD = "drag_counter";
    private static final String KEY_SHARE = "share";
    private static final String KEY_TIME_SERIES = "time_series";

    public ReadingBehaviorDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME_READING_BEHAVIOR + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NEWS_ID + " TEXT,"
                + KEY_DOC_ID + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_MEDIA + " TEXT,"
                + KEY_TRIGGER_BY + " TEXT,"
                + KEY_IN_TIMESTAMP + " INT,"
                + KEY_OUT_TIMESTAMP + " INT,"
                + KEY_CONTENT_LENGTH + " FLOAT,"
                + KEY_DISPLAY_WIDTH + " FLOAT,"
                + KEY_DISPLAY_HEIGHT + " INT,"
                + KEY_TIME_ON_PAGE + " INT,"
                + KEY_PAUSE_ON_PAGE + " INT,"
                + KEY_VIEW_PORT_NUM + " INT,"
                + KEY_VIEW_PORT_RECORD + " TEXT,"
                + KEY_FLING_NUM + " INT,"
                + KEY_FLING_RECORD + " TEXT,"
                + KEY_DRAG_NUM + " INT,"
                + KEY_DRAG_RECORD + " TEXT,"
                + KEY_SHARE + " TEXT,"
                + KEY_TIME_SERIES + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_READING_BEHAVIOR);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    public void insertReadingBehaviorDetailsCreate(ReadingBehavior readingBehavior){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NEWS_ID, readingBehavior.getKEY_NEWS_ID());
        cValues.put(KEY_DOC_ID, readingBehavior.getKEY_DOC_ID());
        cValues.put(KEY_TRIGGER_BY, readingBehavior.getKEY_TRIGGER_BY());
        cValues.put(KEY_TITLE, readingBehavior.getKEY_TITLE());
        cValues.put(KEY_MEDIA, readingBehavior.getKEY_MEDIA());
        cValues.put(KEY_IN_TIMESTAMP, readingBehavior.getKEY_IN_TIMESTAMP());
        cValues.put(KEY_OUT_TIMESTAMP, readingBehavior.getKEY_OUT_TIMESTAMP());
        cValues.put(KEY_CONTENT_LENGTH, readingBehavior.getKEY_CONTENT_LENGTH());
        cValues.put(KEY_DISPLAY_WIDTH, readingBehavior.getKEY_DISPLAY_WIDTH());
        cValues.put(KEY_DISPLAY_HEIGHT, readingBehavior.getKEY_DISPLAY_HEIGHT());
        cValues.put(KEY_TIME_ON_PAGE, readingBehavior.getKEY_TIME_ON_PAGE());
        cValues.put(KEY_PAUSE_ON_PAGE, readingBehavior.getKEY_PAUSE_ON_PAGE());
        cValues.put(KEY_VIEW_PORT_NUM, readingBehavior.getKEY_VIEW_PORT_NUM());
        cValues.put(KEY_VIEW_PORT_RECORD, readingBehavior.getKEY_VIEW_PORT_RECORD());
        cValues.put(KEY_FLING_NUM, readingBehavior.getKEY_FLING_NUM());
        cValues.put(KEY_FLING_RECORD, readingBehavior.getKEY_FLING_RECORD());
        cValues.put(KEY_DRAG_NUM, readingBehavior.getKEY_DRAG_NUM());
        cValues.put(KEY_DRAG_RECORD, readingBehavior.getKEY_DRAG_RECORD());
        cValues.put(KEY_SHARE, readingBehavior.getKEY_SHARE());
        cValues.put(KEY_TIME_SERIES, readingBehavior.getKEY_TIME_SERIES());
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME_READING_BEHAVIOR,null, cValues);
        db.close();
    }

    // Update User Details
    public boolean UpdateReadingBehaviorDetails(ReadingBehavior readingBehavior){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
//        String row_id
        cValues.put(KEY_TITLE, readingBehavior.getKEY_TITLE());
        cValues.put(KEY_MEDIA, readingBehavior.getKEY_MEDIA());
        cValues.put(KEY_CONTENT_LENGTH, readingBehavior.getKEY_CONTENT_LENGTH());
        cValues.put(KEY_OUT_TIMESTAMP, readingBehavior.getKEY_OUT_TIMESTAMP());
        cValues.put(KEY_TIME_ON_PAGE, readingBehavior.getKEY_TIME_ON_PAGE());
        cValues.put(KEY_PAUSE_ON_PAGE, readingBehavior.getKEY_PAUSE_ON_PAGE());
        cValues.put(KEY_VIEW_PORT_NUM, readingBehavior.getKEY_VIEW_PORT_NUM());
        cValues.put(KEY_VIEW_PORT_RECORD, readingBehavior.getKEY_VIEW_PORT_RECORD());
        cValues.put(KEY_FLING_NUM, readingBehavior.getKEY_FLING_NUM());
        cValues.put(KEY_FLING_RECORD, readingBehavior.getKEY_FLING_RECORD());
        cValues.put(KEY_DRAG_NUM, readingBehavior.getKEY_DRAG_NUM());
        cValues.put(KEY_DRAG_RECORD, readingBehavior.getKEY_DRAG_RECORD());
        cValues.put(KEY_SHARE, readingBehavior.getKEY_SHARE());
        cValues.put(KEY_TIME_SERIES, readingBehavior.getKEY_TIME_SERIES());

        return db.update(TABLE_NAME_READING_BEHAVIOR, cValues, KEY_DOC_ID+" = ?",  new String[]{String.valueOf(readingBehavior.getKEY_DOC_ID())}) >0;
//        return true;
    }

    public Cursor getReadingDataForESM(long now_timestamp) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        Cursor res =  db.rawQuery( "SELECT tmp.news_id, tmp.title, tmp.media, tmp.share, tmp.trigger_by, tmp.in_timestamp\n" +
                        "FROM\n" +
                        "(\n" +
                        "\tSELECT DISTINCT rb.news_id, \n" +
                        "\t\t\t\t\trb.title, \n" +
                        "\t\t\t\t\trb.media, \n" +
                        "\t\t\t\t\trb.share,\n" +
                        "\t\t\t\t\trb.trigger_by,\n" +
                        "\t\t\t\t\trb.in_timestamp,\n" +
                        "\t\t\t\t\t(" + now_timestamp + "-rb.in_timestamp) as diff\n" +
                        "\tFROM reading_behavior rb\n" +
                        "\tWHERE rb.time_on_page > 1\n" +
                        ") as tmp\n" +
                        "WHERE tmp.diff <= 3600 AND tmp.diff >=0\n" +
                        "ORDER BY tmp.in_timestamp DESC;"
                , null );
        return res;
    }
    // Get User Details
//    public ArrayList<HashMap<String, String>> GetNotifications(){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ArrayList<HashMap<String, String>> notificationList = new ArrayList<>();
////        String query = "SELECT packagename, tickertext, time, notititle, notitext FROM "+ TABLE_NAME_NOTIFICATION;
//        String query = "SELECT * FROM "+ TABLE_NAME_READING_BEHAVIOR;
//        Cursor cursor = db.rawQuery(query,null);
//        while (cursor.moveToNext()){
//            HashMap<String, String> notification = new HashMap<>();
//            notification.put("packagename",cursor.getString(cursor.getColumnIndex(KEY_NEWS_ID)));
//            notification.put("time",cursor.getString(cursor.getColumnIndex(KEY_TIME_IN)));
//            notification.put("tickertext",cursor.getString(cursor.getColumnIndex(KEY_TRIGGER_BY)));
//            notification.put("notititle",cursor.getString(cursor.getColumnIndex(KEY_DISPLAY_WIDTH)));
//            notification.put("notitext",cursor.getString(cursor.getColumnIndex(KEY_TIME_OUT)));
//            notificationList.add(notification);
//        }
//        return  notificationList;
//    }


//    // Get User Details based on userid
//    public ArrayList<HashMap<String, String>> GetUserByUserId(int notificationid){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ArrayList<HashMap<String, String>> notificationList = new ArrayList<>();
//        String query = "SELECT packagename, tickertext, time, title, text FROM "+ TABLE_Notifications;
//        Cursor cursor = db.query(TABLE_Notifications, new String[]{KEY_PACKAGE_NAME, KEY_TICKER_TEXT, KEY_TIME, KEY_TITLE, KEY_TEXT}, KEY_ID+ "=?",new String[]{String.valueOf(notificationid)},null, null, null, null);
//        if (cursor.moveToNext()){
//            HashMap<String, String> notification = new HashMap<>();
//            notification.put("packagename",cursor.getString(cursor.getColumnIndex(KEY_PACKAGE_NAME)));
//            notification.put("time",cursor.getString(cursor.getColumnIndex(KEY_TIME)));
//            notification.put("tickertext",cursor.getString(cursor.getColumnIndex(KEY_TICKER_TEXT)));
//            notification.put("title",cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
//            notification.put("text",cursor.getString(cursor.getColumnIndex(KEY_TEXT)));
//            notificationList.add(notification);
//        }
//        return  notificationList;
//    }
//    // Delete User Details
//    public void DeleteUser(int userid){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_Notifications, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
//        db.close();
//    }


}
