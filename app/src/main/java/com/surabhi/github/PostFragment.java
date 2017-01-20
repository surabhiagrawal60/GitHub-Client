package com.surabhi.github;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Surabhi Agrawal on 1/19/2017.
 */
// ListView with all issues in recently updated order
public class PostFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {
    ListView postList;
    ArrayAdapter<Post> adapter;
    Handler handler;
    List<Post> posts;
    List<Comments> comments;
    PostHolder postHolder;
    String TAG = "PostFragment";
    SwipeRefreshLayout swipeLayout;
    int page;


    public PostFragment()
    {
        handler = new Handler();
        posts = new ArrayList<Post>();

    }

    public static Fragment newInstance(){
        PostFragment pf= new PostFragment();
        pf.postHolder= new PostHolder();
        return  pf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstance){
        View v = inflater.inflate(R.layout.posts,container,false);

        postList = (ListView)v.findViewById(R.id.posts_list);
        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_red_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);

        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = adapter.getItem(position);
                Log.d(TAG,"ITEMPOSITION:" + position + "");
                Log.d(TAG, "POST OBJECT" + post.commentsUrl + "");
                int count = post.getComments();

                ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();

          if (count == 0 || !isConnected) {
              if(count == 0 ) {
                  Toast.makeText(getActivity(), "No Comments for this Issue", Toast.LENGTH_LONG).show();
              }
              else if(!isConnected){
                  Toast.makeText(getActivity(), "Please check the internet connection and try again!", Toast.LENGTH_LONG).show();
              }
          } else {
                    String url = post.commentsUrl;
                    if (url == null) {
                        Log.d(TAG , "URL_Comments"+ null);
                    }
                    Log.d(TAG,"URL"+ url);
                    Intent i;
                    i = new Intent(getActivity(), MyComments.class);
                    // sending data to new activity
                    i.putExtra("url", url);
                    startActivity(i);
                }
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);

        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected)
            initialize();
        else
            Toast.makeText(getActivity(), "Please check the internet connection and try again!", Toast.LENGTH_LONG).show();

    }

    private void initialize(){
        if(posts.size() == 0)
        {
            new Thread(){
                public void run(){
                    try {
                        page =1;
                        posts.addAll(postHolder.fetchPosts(page));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            createAdapter();
                        }
                    });
                }
            }.start();
        }else{
            new Thread(){
                public void run(){
                    try {
                        int size = posts.size();
                        page = (size/30) + 1;
                        posts.addAll(postHolder.fetchPosts(page));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            createAdapter();
                        }
                    });
                }
            }.start();
        }
    }

    private void createAdapter(){
        if(getActivity() == null) return;

        adapter = new ArrayAdapter<Post>(getActivity(),R.layout.post_item,posts){
            @Override
            public View getView(int position,View convertView, ViewGroup parent)
            {
                if(convertView == null) {
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.post_item, null);
                }

                TextView postTitle;
                postTitle= (TextView) convertView.findViewById(R.id.post_title);


                TextView postBody;
                postBody = (TextView) convertView.findViewById(R.id.post_body);

                TextView postUser;
                postUser = (TextView) convertView.findViewById(R.id.user);

                TextView postComments;
                postComments = (TextView) convertView.findViewById(R.id.num_comments);


                postTitle.setText(posts.get(position).getTitle());

                postUser.setText("Posted By " + posts.get(position).getUser());

                postComments.setText("" + posts.get(position).getComments());

                int count= posts.get(position).getTitleBody().length();
                if(count >140)
                    postBody.setText(posts.get(position).getTitleBody().substring(0,140));
                else
                    postBody.setText(posts.get(position).getTitleBody());

                return convertView;
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
            }

            @Override
            public Post getItem(int position) {
                return super.getItem(position);
            }

        };

        postList.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(false);
        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            new Thread() {
                public void run() {


                    try {
                        int size = posts.size();
                        page = (size / 30) + 1;
                        posts.addAll(postHolder.fetchPosts(page));


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                createAdapter();

                                Toast.makeText(getActivity(), "Please go to the bottom", Toast.LENGTH_SHORT).show();
                            }
                        });

                }
            }.start();
        }
        else
        {
            Toast.makeText(getActivity(), "Please check the internet connection and try again!", Toast.LENGTH_LONG).show();
        }
    }

 }
