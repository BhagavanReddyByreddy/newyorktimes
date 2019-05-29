package com.example.newyorktimes.roomDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.newyorktimes.pojos.BookData;

/**
 * Created by Bhagavan Byreddy on 3/12/2019.
 */


@Database(entities = {BookData.class}, version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase INSTANCE;

    public abstract BookDao bookDao();

    public static AppDataBase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "book-database")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
