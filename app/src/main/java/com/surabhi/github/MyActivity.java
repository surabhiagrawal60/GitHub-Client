package com.surabhi.github;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected)
        {
            addFragment();
        }
        else
        {
            TextView textView =(TextView) findViewById(R.id.textView);
            textView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Please check the internet connection and try again!", Toast.LENGTH_LONG).show();
        }
    }

    public void addFragment()
    {
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragments_holder,
                        PostFragment.newInstance())
        .commit();
    }

}
