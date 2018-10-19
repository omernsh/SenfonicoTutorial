package com.omernsh.android.senfonicotutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.omernsh.android.senfonicotutorial.R;

import java.util.ArrayList;



public class MainActivity extends Noyau {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private FloatingActionButton fab;
    private Button search_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        activateToolbar();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,
                new ArrayList<Photo>());
        mRecyclerView.setAdapter(recyclerViewAdapter);
        search_button=(Button) findViewById((R.id.search_button));






        mRecyclerView.addOnItemTouchListener(new RecyclerItemClick(this,
                mRecyclerView, new RecyclerItemClick.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                Intent intent = new Intent(MainActivity.this, PhotoDetailsActivity.class);
                intent.putExtra(PHOTO_TRANSFER, recyclerViewAdapter.getPhoto(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){


        switch (item.getItemId()) {

            case R.id.mShare:

                Intent i = new Intent(

                        android.content.Intent.ACTION_SEND);

                i.setType("text/plain");

                i.putExtra(
                        android.content.Intent.EXTRA_TEXT, "Harika bir uygulama, sen de indir");

                startActivity(Intent.createChooser(

                        i,

                        "Paylaş"));

                break;


            case R.id.mLike:

                like();

                break;

            case R.id.mSearch:

                search();

                break;



        }




        return super.onOptionsItemSelected(item);

    }

    private void search() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }


    public void like(){
        Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
        openURL.setData(Uri.parse("https://play.google.com/store/apps?hl=en"));
        startActivity(openURL);

    }





    @Override
    protected void onResume() {
        super.onResume();
        String query = getSavedPreferenceData(FLICKR_QUERY);
        if (query.length() > 0) {
            ProcessPhotos processPhotos = new ProcessPhotos(query, true);
            processPhotos.execute();
        }
    }

    private String getSavedPreferenceData(String key) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPref.getString(key, "");
    }

    public class ProcessPhotos extends JsonData {

        public ProcessPhotos(String searchCriteria, boolean matchAll) {
            super(searchCriteria, matchAll);
        }

        public void execute() {

            ProcessData processData = new ProcessData();
            processData.execute();
        }

        public class ProcessData extends DownloadJsonData {

            protected void onPostExecute(String webData) {
                super.onPostExecute(webData);
                recyclerViewAdapter.loadNewData(getPhotos());


            }
        }

    }

}
