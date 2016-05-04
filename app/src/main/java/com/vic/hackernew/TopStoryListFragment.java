package com.vic.hackernew;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vic.hackernew.Adapter.TopStoryAdapter;
import com.vic.hackernew.Decoration.DividerItemDecoration;
import com.vic.hackernew.Model.TopStory;
import com.vic.hackernew.NetWork.CustomJsonArrayRequest;
import com.vic.hackernew.NetWork.CustomJsonObjectRequest;
import com.vic.hackernew.NetWork.CustomVolleyRequest;
import com.vic.hackernew.Utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopStoryListFragment extends Fragment {


    ProgressBar progressBar;
    RequestQueue requestQueue;
    SwipeRefreshLayout swipeContainer;
    Toolbar toolbar;
    View rootView;

    private List<TopStory> topStories;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TopStoryAdapter adapter;


    public TopStoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            if (rootView.getParent() != null)
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            return rootView;
        }

        rootView = inflater.inflate(R.layout.topstory_list, container, false);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);


        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);

        topStories = new ArrayList<>();

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.topStoryRecycleView);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TopStoryAdapter(getContext(), topStories);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), null));


        requestQueue = CustomVolleyRequest.getInstance(getActivity().getApplicationContext()).getRequestQueue();

        getTopStoriesListId(requestQueue, Constant.TAG_BASE_URL + Constant.TAG_TOPSTORIES_URL);

//        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
//            @Override
//            public void onRequestFinished(Request<Object> request) {
//                adapter.notifyDataSetChanged();
//            }
//        });

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                getTopStoriesListId(requestQueue, Constant.TAG_BASE_URL + Constant.TAG_TOPSTORIES_URL);
                requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                    @Override
                    public void onRequestFinished(Request<Object> request) {
//                        adapter.notifyDataSetChanged();
                        if (swipeContainer.isRefreshing()) swipeContainer.setRefreshing(false);
                    }
                });
            }
        });


//        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                // Triggered only when new data needs to be appended to the list
//                // Add whatever code is needed to append new items to the bottom of the list
//                customLoadMoreDataFromApi(page);
//            }
//        });



        return rootView;
    }

//    // Append more data into the adapter
//    // This method probably sends out a network request and appends new data items to your adapter.
//    public void customLoadMoreDataFromApi(int offset) {
//        // Send an API request to retrieve appropriate data using the offset value as a parameter.
//        // Deserialize API response and then construct new objects to append to the adapter
//        // Add the new objects to the data source for the adapter
////        topStories.addAll(moreItems);
//        getTopStoriesListId(requestQueue, Constant.TAG_BASE_URL + Constant.TAG_TOPSTORIES_URL);
//
//        // For efficiency purposes, notify the adapter of only the elements that got changed
//        // curSize will equal to the index of the first element inserted because the list is 0-indexed
////        int curSize = adapter.getItemCount();
////        adapter.notifyItemRangeInserted(curSize, topStories.size() - 1);
//    }



    private void getTopStoriesListId(final RequestQueue requestQueue, String url) {
        progressBar.setVisibility(View.VISIBLE);
        CustomJsonArrayRequest jsonArrayRequest = new CustomJsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray respond) {
                        progressBar.setVisibility(View.GONE);
                        topStories.clear();
                        for (int i = 0; i < respond.length(); i++) {
                            try {
                                getTopStoryDetail(requestQueue, (Constant.TAG_BASE_URL + "item/" + respond.getInt(i) + ".json?print=pretty"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    private void getTopStoryDetail(RequestQueue requestQueue, String topStoryUrl) {

        progressBar.setVisibility(View.VISIBLE);
        CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.GET, topStoryUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject respond) {
                        progressBar.setVisibility(View.GONE);
                        Gson gson = new GsonBuilder().create();
                        TopStory topStory = gson.fromJson(respond.toString(), TopStory.class);

                        int index = Collections.binarySearch(topStories, topStory);
                        if (index < 0) {
                            index = -index - 1;
                            topStories.add(index, topStory);
                            int curSize = adapter.getItemCount();
                            adapter.notifyItemRangeInserted(curSize, topStories.size() - 1);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle("HackerNew");
    }
}
