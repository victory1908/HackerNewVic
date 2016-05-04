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
import com.vic.hackernew.Adapter.CommentAdapter;
import com.vic.hackernew.Decoration.DividerItemDecoration;
import com.vic.hackernew.Model.Comment;
import com.vic.hackernew.NetWork.CustomJsonObjectRequest;
import com.vic.hackernew.NetWork.CustomVolleyRequest;
import com.vic.hackernew.Utils.Constant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommentDetailFragment extends Fragment {

    List<Comment> replies;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    Comment comment;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CommentAdapter adapter;
    SwipeRefreshLayout swipeContainer;
    View rootView;
    private Toolbar toolbar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CommentDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) {
            if (rootView.getParent() != null)
                ((ViewGroup) rootView.getParent()).removeView(rootView);
            return rootView;
        }

        rootView = inflater.inflate(R.layout.comment_list, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);

        Bundle bundle = this.getArguments();
        comment = bundle.getParcelable(Constant.TAG_COMMENT);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbar);
        toolbar.setTitle(comment.getBy());

        replies = new ArrayList<>();

//        Activity activity = this.getActivity();
//        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
//        if (appBarLayout != null) {
//            appBarLayout.setTitle(comment.getBy());
//            appBarLayout.setTitleEnabled(true);
//        }


        recyclerView = (RecyclerView) rootView.findViewById(R.id.comment_list_recycleView);
        assert recyclerView != null;

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CommentAdapter(getContext(), replies);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), null));


        requestQueue = CustomVolleyRequest.getInstance(this.getContext().getApplicationContext()).getRequestQueue();

        for (int i = 0; i < comment.getKids().length; i++) {
            getCommentsDetail(requestQueue, Constant.TAG_BASE_URL + "item/" + comment.getKids()[i] + ".json?print=pretty");
        }

//         Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                for (int i = 0; i < comment.getKids().length; i++) {
                    getCommentsDetail(requestQueue, Constant.TAG_BASE_URL + "item/" + comment.getKids()[i] + ".json?print=pretty");
                }
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

    private void getCommentsDetail(final RequestQueue requestQueue, String commentUrl) {

        progressBar.setVisibility(View.VISIBLE);
        CustomJsonObjectRequest jsonObjectRequest = new CustomJsonObjectRequest(Request.Method.GET, commentUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject respond) {
                        progressBar.setVisibility(View.GONE);

                        Gson gson = new GsonBuilder().create();
                        Comment comment = gson.fromJson(respond.toString(), Comment.class);

                        int index = Collections.binarySearch(replies, comment);
                        if (index < 0) {
                            index = -index - 1;
                            int prevSize = replies.size();
                            replies.add(index, comment);
                            adapter.notifyItemRangeInserted(prevSize, replies.size() - prevSize);
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
        jsonObjectRequest.setPriority(Request.Priority.HIGH);
        requestQueue.add(jsonObjectRequest);
    }



}


