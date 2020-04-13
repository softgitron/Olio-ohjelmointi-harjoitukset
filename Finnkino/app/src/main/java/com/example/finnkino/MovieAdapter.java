package com.example.finnkino;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> movies;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final View view;
        private final TextView movieTitle;
        private final TextView movieStart;
        private final TextView movieEnd;
        private final TextView theatreName;
        private final TextView auditoriumName;
        private final ImageView movieThumbnail;

        public ViewHolder(View v) {
            super(v);
            view = v;
            movieTitle = v.findViewById(R.id.movieTitle);
            movieStart = v.findViewById(R.id.movieStart);
            movieEnd = v.findViewById(R.id.movieEnd);
            theatreName = v.findViewById(R.id.theatreName);
            auditoriumName = v.findViewById(R.id.auditoriumName);
            movieThumbnail = v.findViewById(R.id.movieThumbnail);
        }

        public TextView getMovieTitleView() { return movieTitle; }
        public TextView getMovieStartView() { return movieStart; }
        public TextView getMovieEndView() { return movieEnd; }
        public TextView getTheatreNameView() { return theatreName; }
        public TextView getAuditoriumNameView() { return auditoriumName; }
        public ImageView getMovieThumbnail() { return movieThumbnail; }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Movie movie = movies.get(position);
        holder.getMovieTitleView().setText(movie.getTitle());
        holder.getMovieStartView().setText(movie.getStartTimeString());
        holder.getMovieEndView().setText(movie.getEndTimeString());
        holder.getTheatreNameView().setText(movie.getTheatre());
        holder.getAuditoriumNameView().setText(movie.getAuditorium());
        setImage(holder, movie);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // https://medium.com/@crossphd/android-image-loading-from-a-string-url-6c8290b82c5e
    private void setImage(ViewHolder holder, Movie movie) {
        class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView imageView;
            public DownloadImageTask(ImageView bmImage) {
                this.imageView = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String url = urls[0];
                Bitmap bitmap = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    System.out.println(e);
                }
                return bitmap;
            }

            protected void onPostExecute(Bitmap result) {
                imageView.setImageBitmap(result);
            }
        }
        new DownloadImageTask(holder.getMovieThumbnail()).execute(movie.getPicture());
    }
}
