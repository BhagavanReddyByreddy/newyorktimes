package com.example.newyorktimes.roomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.newyorktimes.pojos.BookData;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM books")
    List<BookData> getPopularBooks();

    @Insert
    void insertPopularBooks(BookData bookData);
}
