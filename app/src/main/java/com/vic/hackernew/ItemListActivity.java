package com.vic.hackernew;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.vic.hackernew.Adapter.TopStoryAdapter;
import com.vic.hackernew.Model.TopStory;
import com.vic.hackernew.Utils.Constant;
import com.vic.hackernew.Utils.CustomJsonArrayRequest;
import com.vic.hackernew.Utils.CustomJsonObjectRequest;
import com.vic.hackernew.Utils.CustomVolleyRequest;
import com.vic.hackernew.Utils.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    public static boolean mTwoPane;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    private List listTopStoriesId;
    private List<TopStory> topStories;


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TopStoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        progressBar = (ProgressBar)findViewById(R.id.progressBar);


        topStories = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        assert recyclerView != null;

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TopStoryAdapter(this, topStories);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, null));


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        requestQueue = CustomVolleyRequest.getInstance(getApplicationContext()).getRequestQueue();
        listTopStoriesId = new ArrayList();

        getTopStoriesListId(requestQueue, Constant.TAG_BASE_URL + Constant.TAG_TOPSTORIES_URL);

//        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
//            @Override
//            public void onRequestFinished(Request<Object> request) {
//                requestQueue.getCache().clear();
//                adapter.notifyDataSetChanged();
//                recyclerView.setHasFixedSize(true);
//            }
//        });

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

    private void getTopStoryDetail(final RequestQueue requestQueue, final String topStoryUrl) {

        progressBar.setVisibility(View.VISIBLE);
        final CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.GET,topStoryUrl,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject respond) {
                        progressBar.setVisibility(View.GONE);
                        TopStory topStory = TopStory.fromJson(respond);

                        int index = Collections.binarySearch(topStories, topStory);
                        if (index < 0) {
                            index = -index - 1;
                            topStories.add(index, topStory);
                            adapter.notifyItemInserted(index);
                            recyclerView.setHasFixedSize(true);
                        }

//                        requestQueue.getCache().invalidate(topStoryUrl,true);
//                        adapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}
