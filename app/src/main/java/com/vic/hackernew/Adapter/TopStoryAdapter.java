package com.vic.hackernew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vic.hackernew.ItemDetailActivity;
import com.vic.hackernew.ItemDetailFragment;
import com.vic.hackernew.Model.TopStory;
import com.vic.hackernew.R;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by vic on 22-Apr-16.
 */
public class TopStoryAdapter extends RecyclerView.Adapter<TopStoryAdapter.ViewHolder> {

    private  Context context;
    private  List<TopStory> topStories;

    private boolean mTwoPane;

    public TopStoryAdapter(Context context,List<TopStory> topStories) {
        this.context = context;
        this.topStories = topStories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TopStory topStory = topStories.get(position);
        holder.author.setText(topStory.getAuthor());
        holder.title.setText(topStory.getTitle());
        holder.score.setText(String.valueOf(topStory.getScore()));

//        holder.time.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
//                format(new Date(topStory.getTime() * 1000)));
//        holder.time.setText(new DateFormat.getDateTimeInstance(new Date(topStory.getTime() * 1000)));

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putInt(ItemDetailFragment.ARG_ITEM_ID, holder.topStory.getId());
//                    ItemDetailFragment fragment = new ItemDetailFragment();
//                    fragment.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.item_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, ItemDetailActivity.class);
//                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.topStory.getId());
//
//                    context.startActivity(intent);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return topStories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public final TextView author;
        public final TextView score;
        public final TextView time;
        public TopStory topStory;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            score = (TextView) view.findViewById(R.id.score);
            time = (TextView) view.findViewById(R.id.time);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + author.getText() + "'";
        }
    }
}
