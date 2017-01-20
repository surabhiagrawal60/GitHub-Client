package com.surabhi.github;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Surabhi Agrawal on 1/19/2017.
 */
public class FetchComments extends Fragment {

    Handler handler;
    List<Comments> comments;
    ListView commList;
    ArrayAdapter<Comments> adapter;
    String url;
    static String TAG = "FetchComments";

    public FetchComments()
    {
        handler = new Handler();
        comments = new ArrayList<Comments>();
    }

    public static Fragment newInstanceComments(String url){
        FetchComments pf= new FetchComments();
        pf.url = url;
        return  pf;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstance){

        // getting attached intent data
        View v = inflater.inflate(R.layout.comments,container,false);
        commList = (ListView)v.findViewById(R.id.comments_list);
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
        if(comments.size() == 0)
        {
            new Thread(){
                public void run(){
                    try {
                        comments.addAll(fetchComments(url));

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

        adapter = new ArrayAdapter<Comments>(getActivity(),R.layout.comments_item,comments){
            @Override
            public View getView(int position,View convertView, ViewGroup parent)
            {
                if(convertView == null) {
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.comments_item, null);
                }

                TextView postTitle;
                postTitle= (TextView) convertView.findViewById(R.id.comment_text);

                postTitle.setText(comments.get(position).getBody());


                TextView postCommentBy;
                postCommentBy= (TextView) convertView.findViewById(R.id.comment_by);

                postCommentBy.setText("Commented By " + comments.get(position).getComment_by());

                return convertView;
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
            }

        };

        commList.setAdapter(adapter);
    }

    static List<Comments> fetchComments(String commentsUrl) throws MalformedURLException {
        Log.d(TAG, "fetchComments()");
        Log.d(TAG, "url:" + commentsUrl);
        String raw = RemoteData.readContents(commentsUrl);

        if (raw == null)
        {
            Log.d(TAG, "response = null");
            return null;
        }

        Log.d(TAG, "Response: "+raw);
        List<Comments> list=new ArrayList<Comments>();
        try{
            JSONArray data=new JSONArray(raw);

            Log.d(TAG, "data.Json[]"+ data.toString());

            for(int i=0;i<data.length();i++){
                JSONObject cur=data.getJSONObject(i);
                Comments c=new Comments();
                c.body=cur.optString("body");
                JSONObject jsonObject = cur.getJSONObject("user");
                c.comment_by = jsonObject.getString("login");
                list.add(c);
                Log.d(TAG,"children.json[i]:"+ c);
            }
        }catch(Exception e){
            Log.e(TAG, e.toString());
        }
        return list;
    }



}
