package com.example.newyorktimes.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newyorktimes.MainActivity;
import com.example.newyorktimes.R;
import com.example.newyorktimes.pojos.BookData;

import java.util.List;

public class PopularBooksAdapter extends RecyclerView.Adapter<PopularBooksAdapter.BooksHolder> {

    private Context context;
    private List<BookData> bookDataList;

    public PopularBooksAdapter(Context context,List<BookData> bookDataList) {
        this.context = context;
        this.bookDataList = bookDataList;
    }

    @NonNull
    @Override
    public PopularBooksAdapter.BooksHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View bookItem= layoutInflater.inflate(R.layout.book_item_layout, viewGroup, false);
        BooksHolder booksHolder = new BooksHolder(bookItem);
        return booksHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularBooksAdapter.BooksHolder booksHolder, final int position) {

        final BookData bookData = bookDataList.get(position);

        booksHolder.bookNameTv.setText(bookData.getListName());
        booksHolder.bookOldPublishDateTv.setText(bookData.getOldestPublishedDate());
        booksHolder.bookNewPublishDateTv.setText(bookData.getNewestPublishedDate());
        booksHolder.updateDurationTv.setText(bookData.getUpdated());

        booksHolder.bookItemCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof MainActivity){
                    ((MainActivity) context).showBookNameInToast(bookData.getListName());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookDataList.size();
    }

    public class BooksHolder extends RecyclerView.ViewHolder {

        private TextView bookNameTv,bookOldPublishDateTv,bookNewPublishDateTv,updateDurationTv;
        private ConstraintLayout bookItemCl;

        public BooksHolder(@NonNull View itemView) {
            super(itemView);

            bookNameTv = (TextView) itemView.findViewById(R.id.bookNameTv);
            bookOldPublishDateTv = (TextView) itemView.findViewById(R.id.bookOldPublishDateTv);
            bookNewPublishDateTv = (TextView) itemView.findViewById(R.id.bookNewPublishDateTv);
            updateDurationTv = (TextView) itemView.findViewById(R.id.updateDurationTv);
            bookItemCl = (ConstraintLayout) itemView.findViewById(R.id.bookItemCl);
        }
    }
}
