package com.almasapp.hw4.almasapp4;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        final String TAG = "PlaceholderFragment";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            final MovieData movieData = new MovieData();

            RecyclerView moviesRecyclerView = (RecyclerView) rootView.findViewById(R.id.cardList);
            moviesRecyclerView.setHasFixedSize(true);
            moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            //set adapter
            final MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity(), movieData.getMoviesList());
            myRecyclerViewAdapter.SetOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void OnItemLongClick(View view, int position) {
                    movieData.getMoviesList().add(position, (HashMap) ((HashMap) movieData.getItem(position)).clone());
                    myRecyclerViewAdapter.notifyDataSetChanged();
                    Log.d(TAG, "longClick item " + position);
                }

                @Override
                public void OnItemClick(View view, int position) {
                    Toast.makeText(getActivity().getApplication(), movieData.getItem(position).get("name").toString(), Toast.LENGTH_SHORT).show();
                }
            });

            moviesRecyclerView.setAdapter(myRecyclerViewAdapter);

            Button selectAll = (Button) rootView.findViewById(R.id.buttonMoviesSelectAll);
            selectAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < movieData.getSize(); i++)
                        movieData.getItem(i).put("selected", true);
                    myRecyclerViewAdapter.notifyDataSetChanged();
                }
            });

            Button clearAll = (Button) rootView.findViewById(R.id.buttonMoviesClearAll);
            clearAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < movieData.getSize(); i++)
                        movieData.getItem(i).put("selected", false);
                    myRecyclerViewAdapter.notifyDataSetChanged();
                }
            });

            Button delete = (Button) rootView.findViewById(R.id.buttonMoviesDelete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int deleted = 0;

                    for (int i = 0; i < movieData.getSize(); ) {
                        if ( (Boolean) movieData.getItem(i).get("selected")) {
                            movieData.removeItem(i);
                            deleted++;
                        }
                        else
                            i++;
                    }
                    myRecyclerViewAdapter.notifyDataSetChanged();

                    Toast.makeText(getActivity(), deleted + " movies were deleted", Toast.LENGTH_SHORT).show();
                }
            });


            return rootView;
        }
    }
}
