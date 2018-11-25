package com.arpan.itinterview;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ITInterviewApplication extends Application {


    private static DatabaseReference database;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getFireBaseDatabase(){
        return database;
    }

}
