package com.rajany.rajdy.tabs;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rajany.rajdy.R;
import com.rajany.rajdy.advices.AdvicesMain;
import com.rajany.rajdy.classes.AppConfig;
import com.rajany.rajdy.classes.FeedAppController;
import com.rajany.rajdy.classes.FeedItem;
import com.rajany.rajdy.classes.FeedListAdapter;
import com.rajany.rajdy.ideabook_viewer.ideabook;
import com.rajany.rajdy.stories.StoriesMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = Home.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = AppConfig.HOME_URL_FEED;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_home, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();

        listAdapter = new FeedListAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);
        final LinearLayout Ideabook = (LinearLayout) rootView.findViewById(R.id.Ideabook);
        final LinearLayout ADVICE = (LinearLayout) rootView.findViewById(R.id.ADVICELAYOUT);
        final LinearLayout STORIES = (LinearLayout) rootView.findViewById(R.id.STORIESLAYOUT);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                    if(!isNetworkAvailable()){
                                            Toast.makeText(getActivity(),"Please Connect To The Internet",Toast.LENGTH_LONG).show();
                                        }else{
                                            FeedAppController.getInstance().getRequestQueue().getCache().remove(URL_FEED);
                                            swipeRefreshLayout.setRefreshing(true);
                                            feedItems.clear();
                                            listView.setAdapter(null);
                                            fetchFeed();
                                        }

                                    }
                                }
        );

        Ideabook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ideabook.class);
                getActivity().startActivity(intent);
            }
        });

        ADVICE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AdvicesMain.class);
                getActivity().startActivity(intent);
            }
        });

        STORIES.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StoriesMain.class);
                getActivity().startActivity(intent);
            }
        });

        return rootView;
    }

    private void fetchFeed() {
        swipeRefreshLayout.setRefreshing(true);
        Cache cache = FeedAppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);

        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);

                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                    // stopping swipe refresh
                }
            });

            // Adding request to volley request queue
            FeedAppController.getInstance().addToRequestQueue(jsonReq);
        }
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     * */
    private void parseJsonFeed(JSONObject response) {
        listView.setAdapter(listAdapter);
        try {
            JSONArray feedArray = response.getJSONArray("feed");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("image") ? null : feedObj
                        .getString("image");
                item.setImge(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                feedItems.add(item);


            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRefresh() {
        FeedAppController.getInstance().getRequestQueue().getCache().remove(URL_FEED);
        feedItems.clear();
        listView.invalidateViews();
        fetchFeed();
        listView.invalidateViews();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}