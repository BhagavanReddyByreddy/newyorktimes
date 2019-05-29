package com.example.newyorktimes.roomDB;

import com.example.newyorktimes.pojos.BookData;

import java.util.List;

public class BookRepository {


    public static void addBooks(final AppDataBase db, final List<BookData> bookDataList) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < bookDataList.size(); i++) {
                    BookData bookData = bookDataList.get(i);
                    db.bookDao().insertPopularBooks(bookData);
                }
            }
        }).start();

    }


}
