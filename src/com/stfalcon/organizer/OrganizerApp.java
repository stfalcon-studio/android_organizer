package com.stfalcon.organizer;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by AlexZ on 09.07.14.
 */
public class OrganizerApp extends Application{

    private static OrganizerApp self;
    private SharedPreferences sharedPreferences;
    private final String NOTES = "notes";


    public static synchronized OrganizerApp getInstans(){
       return self;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }



    public void saveInPreferences(String note){
        HashSet<String> hashSet = new HashSet<String>();
        Set<String> notes = sharedPreferences.getStringSet(NOTES, new HashSet<String>());
        hashSet.add(note);
        hashSet.addAll(notes);
        sharedPreferences.edit().putStringSet(NOTES, hashSet).commit();
    }


    public Set<String> getSavedNotes(){
        return sharedPreferences.getStringSet(NOTES, new HashSet<String>());
    }
}
