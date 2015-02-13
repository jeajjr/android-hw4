package com.almasapp.hw4.almasapp4;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by José Ernesto on 12/02/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Map<String, ?>> mDataSet;
    private Context context;
    OnItemClickListener mItemClickListener;

    public MyRecyclerViewAdapter(Context context, List<Map<String, ?>> mDataSet) {
        this.mDataSet = mDataSet;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView vIcon;
        public TextView vTitle;
        public TextView vDescription;
        public CheckBox vCheckBox;
        public RatingBar ratingBar;

        public ViewHolder(View v) {
            super(v);
            vIcon = (ImageView) v.findViewById(R.id.imageViewMoviesImage);
            vTitle = (TextView) v.findViewById(R.id.textViewMoviesTitle);
            vDescription = (TextView) v.findViewById(R.id.textViewMoviesDescription);
            vCheckBox = (CheckBox) v.findViewById(R.id.checkBoxMovies);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBarMovie);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemClick(v, getPosition());
                    }
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.OnItemLongClick(v, getPosition());
                    }
                    return true;
                }
            });
        }

        public void bindMovieData (Map<String, ?> movie) {
            vTitle.setText((String) movie.get("name"));
            vDescription.setText((String) movie.get("description"));
            vIcon.setImageResource((Integer) movie.get("image"));
            vCheckBox.setChecked((Boolean) movie.get("selection"));
            ratingBar.setRating((int) Double.parseDouble(movie.get("rating").toString()) + 1);
        }
    }

    public interface OnItemClickListener {
        public void OnItemClick(View view, int position);
        public void OnItemLongClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v;

        if (viewType == 1)
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_good_movie, parent, false);
        else
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_normal, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        Map<String, ?> movie = mDataSet.get(position);
        double rating = Double.parseDouble(movie.get("rating").toString());
        return (rating >= 8.0) ? 1 : 0;
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        Map<String, ?> movie = mDataSet.get(position);
        holder.bindMovieData(movie);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


}
