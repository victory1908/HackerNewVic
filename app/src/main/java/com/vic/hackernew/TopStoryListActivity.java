package com.vic.hackernew;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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


public class TopStoryListActivity extends AppCompatActivity {

    public static boolean mTwoPane;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    SwipeRefreshLayout swipeContainer;
    private List listTopStoriesId;
    private List<TopStory> topStories;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TopStoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topstory_list);
        if (findViewById(R.id.topStory_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        listTopStoriesId = new ArrayList();

        topStories = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) findViewById(R.id.topStory_list);
        assert recyclerView != null;
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TopStoryAdapter(this, topStories);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, null));


        requestQueue = CustomVolleyRequest.getInstance(getApplicationContext()).getRequestQueue();

        getTopStoriesListId(requestQueue, Constant.TAG_BASE_URL + Constant.TAG_TOPSTORIES_URL);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                getTopStoriesListId(requestQueue, Constant.TAG_BASE_URL + Constant.TAG_TOPSTORIES_URL);
                requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                    @Override
                    public void onRequestFinished(Request<Object> request) {
                        if (swipeContainer.isRefreshing()) swipeContainer.setRefreshing(false);
                    }
                });
            }
        });

    }

    private void getTopStoriesListId(final RequestQueue requestQueue, String url) {
        progressBar.setVisibility(View.VISIBLE);
        CustomJsonArrayRequest jsonArrayRequest = new CustomJsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray respond) {
                        progressBar.setVisibility(View.GONE);
                            topStories.clear();
                        for (int i = 0; i <respond.length() ; i++) {
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    private void getTopStoryDetail(RequestQueue requestQueue, String topStoryUrl) {

        progressBar.setVisibility(View.VISIBLE);
        final CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.GET,topStoryUrl,null,
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
                            adapter.notifyItemInserted(index);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}
