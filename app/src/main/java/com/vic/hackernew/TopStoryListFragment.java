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
    private List listTopStoriesId;
    private List<TopStory> topStories;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TopStoryAdapter adapter;


//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        if (savedInstanceState != null)
//        {
//            // Populate countries from bundle
//            super.onSaveInstanceState(savedInstanceState);
//        }
//    }

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

        listTopStoriesId = new ArrayList();

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

//        if (topStories == null) // Updated check
//        {
//            getTopStoriesListId(requestQueue, Constant.TAG_BASE_URL + Constant.TAG_TOPSTORIES_URL);
//        }
//        else
//        {
//            // Populate countries by extracting them from saved instance state bundle
//        }

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

        return rootView;
    }

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

                            int prevSize = topStories.size();
                            topStories.add(index, topStory);
//                            adapter.notifyItemInserted(index);
                            adapter.notifyItemRangeInserted(prevSize, topStories.size() - prevSize);
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

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//    }


//        public void onSaveInstanceState(Bundle outState)
//    {
//        outState.putStringArrayList(Constant.TAG_TOP_STORY,topStories);
//    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
//                    Log.e("gif--","fragment back key is clicked");
//                    getActivity().getSupportFragmentManager().popBackStack("topStoryDetail", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    return true;
//                }
//                return false;
//            }
//        });
//    }


    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle("HackerNew");
    }
}
