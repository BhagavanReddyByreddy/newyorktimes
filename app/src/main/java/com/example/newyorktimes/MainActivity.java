package com.example.newyorktimes;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.newyorktimes.adapters.PopularBooksAdapter;
import com.example.newyorktimes.constants.Globals;
import com.example.newyorktimes.dagger2.MyComponent;
import com.example.newyorktimes.dagger2.SharedPrefModule;
import com.example.newyorktimes.databinding.ActivityMainBinding;
import com.example.newyorktimes.interfaces.ServiceReponse;
import com.example.newyorktimes.pojos.BookData;
import com.example.newyorktimes.roomDB.AppDataBase;
import com.example.newyorktimes.roomDB.BookRepository;
import com.example.newyorktimes.utilities.NetworkUtil;
import com.example.newyorktimes.volley.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements ServiceReponse {

    private ActivityMainBinding bind;
    private Volley volley;
    private MyComponent myComponent;
    //@Inject
    //SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main);

        volley = new Volley(MainActivity.this,this);

        /*myComponent = DaggerMyComponent.builder().sharedPrefModule(new SharedPrefModule(this)).build();
        myComponent.inject(this);*/

        sharedPreferences = getSharedPreferences("booksPref", Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean("isItFirstLaunch",true)){
            requestToGetPopularBooks();
        }else{
           getBooksDataFromRoomDB();
        }


    }

    private void getBooksDataFromRoomDB() {

            class GetBooksAsync extends AsyncTask<Void, Void, List<BookData>> {

                @Override
                protected List<BookData> doInBackground(final Void... params) {


                    List<BookData> booksList = AppDataBase.getAppDatabase(MainActivity.this).bookDao().getPopularBooks();
                    return booksList;
                }

                @Override
                protected void onPostExecute(List<BookData> booksList) {
                    super.onPostExecute(booksList);

                    populateList(booksList);

                }
            }

        GetBooksAsync task = new GetBooksAsync();
            task.execute();


    }

    private void requestToGetPopularBooks() {

        volley.serviceRequest(Globals.GET_POPULAR_BOOKS,null, Request.Method.GET);

    }

    public void showBookNameInToast(String listName) {
        Toast.makeText(MainActivity.this, listName, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject responseObj, String serviceUrl) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isItFirstLaunch",false);
        editor.apply();

        List<BookData> bookDataList = new ArrayList<>();

        try {
            JSONArray booksArrayElements = responseObj.getJSONArray("results");

            if (booksArrayElements.length() > 0) {

                for (int i = 0; i < booksArrayElements.length(); i++) {
                    JSONObject bookObj = booksArrayElements.getJSONObject(i);

                    BookData bookData = new BookData();
                    bookData.setListName(bookObj.getString("display_name"));
                    bookData.setNewestPublishedDate(bookObj.getString("newest_published_date"));
                    bookData.setOldestPublishedDate(bookObj.getString("oldest_published_date"));
                    bookData.setUpdated(bookObj.getString("updated"));

                    bookDataList.add(bookData);
                }

                populateList(bookDataList);

                BookRepository.addBooks(AppDataBase.getAppDatabase(this),bookDataList);


            } else {
                Toast.makeText(this, "No Books are available at this moment", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateList(List<BookData> bookDataList) {

        PopularBooksAdapter adapter = new PopularBooksAdapter(this, bookDataList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        bind.booksRv.setLayoutManager(manager);
        bind.booksRv.setItemAnimator(new DefaultItemAnimator());
        bind.booksRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        bind.booksRv.setAdapter(adapter);

    }
}
