package com.example.youtubelearningbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.youtubelearningbuddy.Model.Topic;
import com.example.youtubelearningbuddy.Model.Video;

import java.util.ArrayList;

/*This class represents the data base to contain the topics and videos within the topics.
* It is created so that the user data is not lost when the app is closed.*/

public class DataBase extends SQLiteOpenHelper{

    private static final String TAG = DataBase.class.getSimpleName();
    private static final String DB_NAME="PlayListDB";
    private static final int DB_VER = 1;
    public static final String TABLE_TOPIC="Topics";
    public static final String COL_TOPIC_NAME="TopicName";
    public static final String TABLE_VIDEO="Videos";
    public static final String COL_VIDEO_ID="VideoId";
    public static final String COL_VIDEO_TITLE="VideoTitle";
    public static final String COL_VIDEO_DESCRIPTION="VideoDescription";
    public static final String COL_VIDEO_PUBLISHEDAT="VideoPublishedAt";
    public static final String COL_VIDEO_THUMBNAIL="VideoThumbnail";
    public static final String COL_VIDEO_FOREIGN_KEY="VideoForeignKey";


    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        /*Create the SQL commands to create topic table and video table in the database.*/
        String createTopicTable = "CREATE TABLE " + TABLE_TOPIC + "( " + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TOPIC_NAME + " TEXT )";
        String createVideoTable = "CREATE TABLE " + TABLE_VIDEO + "( " + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_VIDEO_ID + " TEXT, " + COL_VIDEO_TITLE + " TEXT, " + COL_VIDEO_DESCRIPTION + " TEXT, " + COL_VIDEO_PUBLISHEDAT + " TEXT, " + COL_VIDEO_THUMBNAIL + " TEXT, " + COL_VIDEO_FOREIGN_KEY + " INTEGER )";
        /*Commit the SQL commands to the database.*/
        db.execSQL(createTopicTable);
        db.execSQL(createVideoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
        /*Deleted the existing tables.*/
        String deleteTopicTable = "DELETE TABLE IF EXISTS " + TABLE_TOPIC;
        String deleteVideoTable = "DELETE TABLE IF EXISTS " + TABLE_VIDEO;
        db.execSQL(deleteTopicTable);
        db.execSQL(deleteVideoTable);
        /*Create new tables.*/
        onCreate(db);
    }

    /*Helper methods.*/
    public void insertTopic (Topic topic){
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put(COL_TOPIC_NAME,topic.getTopic());
        /*Insert the topic to the data base and get the auto incremented the row id.*/
        db.insert(TABLE_TOPIC,null,values);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    public void insertVideo (Video video, Long topicId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_VIDEO_ID,video.getId());
        values.put(COL_VIDEO_TITLE,video.getTitle());
        values.put(COL_VIDEO_DESCRIPTION,video.getDescription());
        values.put(COL_VIDEO_PUBLISHEDAT,video.getPublishedAt());
        values.put(COL_VIDEO_THUMBNAIL,video.getThumbnailUrl());
        values.put(COL_VIDEO_FOREIGN_KEY,topicId);
        db.insert(TABLE_VIDEO,null,values);
        db.close();
    }

    public ArrayList<Topic> getTopicList (){
        ArrayList<Topic> topics = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        /*Create a cursor that looks at the topic table's row which contains auto increment id and topic name and order in ascending order.*/
        Cursor cursor = db.query(TABLE_TOPIC,new String[]{BaseColumns._ID,COL_TOPIC_NAME},null,null,null,null,COL_TOPIC_NAME + " ASC");
        /*First place the cursor at the first row.
        * Then while the cursor has a row to move to create a topic and add it to the topic list.*/
        if(cursor.moveToFirst()){
            do{
                /*Create a topic.
                * Use the cursor in Topic table to get the field value of COL_TOPIC_NAME column and convert it to string to set the topic name.
                * https://stackoverflow.com/questions/903343/get-the-field-value-with-a-cursor*/
                String topicName = cursor.getString(cursor.getColumnIndex(COL_TOPIC_NAME));
                Topic topic = new Topic(topicName);

                /*Set the topic id (used as the foreign key)*/
                /*Use the cursor in Topic table to get the field value of _ID column and convert it to long to set the topic id.
                * https://stackoverflow.com/questions/903343/get-the-field-value-with-a-cursor*/
                long topicId =cursor.getLong(cursor.getColumnIndex(BaseColumns._ID));
                topic.setId(topicId);

                /*Set the video list inside the topic.*/
                /*Use the cursor in Topic table to get the field value of _ID column and convert it to string to pass to video as the foreign key.
                * https://stackoverflow.com/questions/903343/get-the-field-value-with-a-cursor*/
                String foreignKey =cursor.getString(cursor.getColumnIndex(BaseColumns._ID));
                topic.setVideoList(getVideoList(db, foreignKey));

                /*Add the topic to the topic list.*/
                topics.add(topic);
            }while(cursor.moveToNext());
        }
        return topics;
    }

    private ArrayList<Video> getVideoList(SQLiteDatabase db, String foreignKey) {
        ArrayList<Video> videos = new ArrayList<>();
        /*Create a cursor (use rawQuery to apply filter to the query) that looks at the video table's columns and filters by foreign key and orders it in ascending order.*/
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_VIDEO + " WHERE " + COL_VIDEO_FOREIGN_KEY + " = " + foreignKey + " ORDER BY " + BaseColumns._ID + " ASC", null);
        if(cursor.moveToFirst()){
            do{
                /*Use the cursor in Video table to get the value of each column and create a video.*/
                String videoId = cursor.getString(cursor.getColumnIndex(COL_VIDEO_ID));
                String videoTitle = cursor.getString(cursor.getColumnIndex(COL_VIDEO_TITLE));
                String videoDescription = cursor.getString(cursor.getColumnIndex(COL_VIDEO_DESCRIPTION));
                String videoPublishedAt = cursor.getString(cursor.getColumnIndex(COL_VIDEO_PUBLISHEDAT));
                String videoThumbnail = cursor.getString(cursor.getColumnIndex(COL_VIDEO_THUMBNAIL));
                String videoForeignKey = cursor.getString(cursor.getColumnIndex(COL_VIDEO_FOREIGN_KEY));
                Video video = new Video(videoId,videoTitle,videoDescription,videoPublishedAt,videoThumbnail,videoForeignKey);
                videos.add(video);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return videos;
    }

}
