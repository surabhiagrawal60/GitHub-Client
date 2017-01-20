package com.surabhi.github;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

/**
 * Created by Surabhi Agrawal on 1/19/2017.
 */
public class MyComments extends Activity {
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

        setContentView(R.layout.activity_cm);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.comment_title);


        Intent i = getIntent();
        String url = i.getStringExtra("url");
        Log.d("URL IN ACTIVITY", url);


        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            addFragment(url);
        }
        else
        {
            Toast.makeText(this,"Please check the internet connection and try again!",Toast.LENGTH_LONG).show();
        }
    }

    public void addFragment(String url)
    {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragments_comments,
                        FetchComments.newInstanceComments(url))
                .commit();
    }

}
