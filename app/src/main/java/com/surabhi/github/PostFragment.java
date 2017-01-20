package com.surabhi.github;

import android.app.Fragment;
import android.content.Intent;
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
public class PostFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {
    ListView postList;
    ArrayAdapter<Post> adapter;
    Handler handler;
    List<Post> posts;
    List<Comments> comments;
    PostHolder postHolder;
    String TAG = "PostFragment";
    SwipeRefreshLayout swipeLayout;



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
                Log.d("ITEMPOSITION", position + "");
                Log.d("POST OBJECT", post.commentsUrl + "");
                int count = post.getComments();
                if (count == 0) {
                    Toast.makeText(getActivity(), "No Comments for this Issue", Toast.LENGTH_LONG).show();
                } else {
                    String url = post.commentsUrl;
                    if (url == null) {
                        Log.d("URL_Comments", null);
                    }
                    Log.d("URL", url);
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
        initialize();
    }

    private void initialize(){
        if(posts.size() == 0)
        {
            new Thread(){
                public void run(){
                    try {
                        posts.addAll(postHolder.fetchPosts());
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
            createAdapter();
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
        new Thread(){
            public void run(){
                try {
                    posts.addAll(postHolder.fetchPosts());

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
