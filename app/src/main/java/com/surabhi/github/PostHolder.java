package com.surabhi.github;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Surabhi Agrawal on 1/19/2017.
 */
// For PostFrgment to get the data from
public class PostHolder {
    String TAG = "PostHolder";
    /**
     * We will be fetching JSON data from the API.
     */
    private final String URL_TEMPLATE=
            "https://api.github.com/repos/rails/rails/issues?sort=updated&page=";

    String url;

    PostHolder(){
        generateURL();
    }

    /**
     * Generates the actual URL from the template based on the
     * subreddit name and the 'after' property.
     */
    private void generateURL(){
        url=URL_TEMPLATE;
    }

    /**
     * Returns a list of Post objects after fetching data from
     * Reddit using the JSON API.
     *
     * @return
     */
    List<Post> fetchPosts(int page) throws MalformedURLException {
        Log.d(TAG, "fetchPosts()");
        Log.d(TAG, "url:" + url);
        url = url + page;
        Log.d(TAG,url);
        String raw = RemoteData.readContents(url);

        if (raw == null)
        {
            Log.d(TAG, "response = null");
            return null;
        }

        Log.d(TAG, "Response: "+raw);
        List<Post> list=new ArrayList<Post>();
        try{
            JSONArray data=new JSONArray(raw);
//

            Log.d(TAG, "data.Json[]"+ data.toString());
//            Log.d(TAG, "children.Json[]"+ children.toString());
            //Using this property we can fetch the next set of
            //posts from the same subreddit
//            after=data.getString("after");

            for(int i=0;i<data.length();i++){
                JSONObject cur=data.getJSONObject(i);
                Post p=new Post();
                p.title=cur.optString("title");
                p.titleBody=cur.optString("body");
                p.updated_at= new Date(cur.optLong("updated_at")*1000);
                p.commentsUrl= cur.getString("comments_url");
                p.comments = cur.getInt("comments");
                JSONObject jsonObject= cur.getJSONObject("user");
                p.user = jsonObject.getString("login");
                if(p.title!=null)
                    list.add(p);
                Log.d(TAG,"children.json[i]:"+ p);
            }
        }catch(Exception e){
            Log.e(TAG, e.toString());
        }
        return list;
    }

    /**
     * This is to fetch the next set of posts
     * using the 'after' property
     * @return
     */
//    List<Post> fetchMorePosts() throws MalformedURLException {
//        generateURL();
//        return fetchPosts();
//    }
}
