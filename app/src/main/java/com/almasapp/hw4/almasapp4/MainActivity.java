package com.almasapp.hw4.almasapp4;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
                    myRecyclerViewAdapter.notifyItemChanged(position);
                }

                @Override
                public void OnItemClick(View view, int position) {
                    Toast.makeText(getActivity().getApplication(), "asd", Toast.LENGTH_SHORT).show();
                }
            });

            moviesRecyclerView.setAdapter(myRecyclerViewAdapter);

            return rootView;
        }
    }
}
