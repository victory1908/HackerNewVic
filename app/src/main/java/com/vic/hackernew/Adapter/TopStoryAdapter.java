package com.vic.hackernew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vic.hackernew.ItemDetailActivity;
import com.vic.hackernew.ItemDetailFragment;
import com.vic.hackernew.ItemListActivity;
import com.vic.hackernew.Model.TopStory;
import com.vic.hackernew.R;
import com.vic.hackernew.TopStoryWebView;
import com.vic.hackernew.Utils.DateTimeFunction;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by vic on 22-Apr-16.
 */
public class TopStoryAdapter extends RecyclerView.Adapter<TopStoryAdapter.ViewHolder> {

    Context context;
    List<TopStory> topStories;


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
        holder.url.setText(topStory.getUrl());

//        holder.time.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
//                format(new Date((long) (topStory.getTime() * 1000)))+"test");

        holder.time.setText(DateTimeFunction.formatDateTime(topStory.getTime()));

//        holder.time.setText(formatDate(Long(1308114404722)*1000L));

        Calendar.getInstance();


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
        public final TextView title;
        public final TextView author;
        public final TextView score;
        public final TextView time;
        public final Button url;
        public final View topStory_View;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            score = (TextView) view.findViewById(R.id.score);
            time = (TextView) view.findViewById(R.id.time);
            url = (Button) view.findViewById(R.id.url);

            topStory_View = view.findViewById(R.id.Topstory_View);


            url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("topStoryUrl", topStories.get(getLayoutPosition()).getUrl());

                    TopStoryWebView topStoryWebViewFragment = new TopStoryWebView();
                    topStoryWebViewFragment.setArguments(bundle);
                    if (ItemListActivity.mTwoPane) {

                        ((FragmentActivity) context).getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.item_detail_container, topStoryWebViewFragment)
                                .commit();
                    } else {
                        topStoryWebViewFragment.setArguments(bundle);
                        ((FragmentActivity) context).getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.frameLayout, topStoryWebViewFragment).addToBackStack(null)
                                .commit();
                    }
                }
            });

            topStory_View.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context,topStories.get(getLayoutPosition()).getAuthor(),Toast.LENGTH_SHORT).show();
                    TopStory topStory = topStories.get(getLayoutPosition());

                    if (topStory.getKids() != null) {

                        if (ItemListActivity.mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, topStory.getKids().toString());

                            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, topStory.getKids().toString());

                            ItemDetailFragment fragment = new ItemDetailFragment();
                            fragment.setArguments(arguments);

                            ((FragmentActivity) context).getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.item_detail_container, fragment)
                                    .commit();
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.topstory_detail_container, fragment)
//                                .commit();
                        } else {
                            Intent intent = new Intent(context, ItemDetailActivity.class);
                            intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, topStory.getKids().toString());
                            context.startActivity(intent);
                        }
                    } else {
                        Toast.makeText(context, "No comment for this article", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + author.getText() + "'";
        }
    }
}

