package com.alexvit.android.popularmovies.moviedetails;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alexvit.android.popularmovies.PopularMoviesApplication;
import com.alexvit.android.popularmovies.R;
import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.data.models.Movie;
import com.alexvit.android.popularmovies.utils.Movies;
import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.alexvit.android.popularmovies.utils.Toast.toast;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getSimpleName();

    @BindView(R.id.app_bar)
    AppBarLayout incAppBar;
    @BindView(R.id.body)
    View incBody;

    private AppBar mAppBar;
    private Body mBody;

    private MovieExtrasAdapter mExtrasAdapter;
    private Movie mMovie;
    private MoviesRepository repository;
    private CompositeDisposable compositeSub = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mMovie = getMovie();
        if (mMovie == null) {
            Log.e(TAG, "No movie was passed.");
            return;
        }

        ButterKnife.bind(this);
        
        mAppBar = new AppBar();
        ButterKnife.bind(mAppBar, incAppBar);
        mBody = new Body();
        ButterKnife.bind(mBody, incBody);

        initToolbar(mMovie.title);
        loadImages(mMovie);
        loadText(mMovie);

        mBody.toggleFavorite.setOnCheckedChangeListener(new FavoriteToggleListener(mMovie));

        mExtrasAdapter = new MovieExtrasAdapter(mBody.reviewList, mBody.videoList);
        repository = PopularMoviesApplication.get(this).getMoviesRepository();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSub.clear();
        compositeSub.dispose();
    }

    private Movie getMovie() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return null;
        } else {
            return getIntent().getParcelableExtra("movie");
        }
    }

    private void initToolbar(String title) {
        setSupportActionBar(mAppBar.toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAppBar.collapsingToolbar.setContentScrimColor(ContextCompat.getColor(this, R.color.primary_dark));
        mAppBar.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white_text));
        mAppBar.collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white_text));
        mAppBar.collapsingToolbar.setTitle(title);
    }

    private void loadImages(Movie movie) {
        String posterSize = Movies.PosterSize.w185;
        String backdropSize;
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE) {
            backdropSize = Movies.BackdropSize.w780;
        } else {
            backdropSize = Movies.BackdropSize.w300;
        }

        String posterUrl = Movies.fullImageUrl(movie.posterPath, posterSize);
        Glide.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .into(mBody.ivPoster);

        String backdropUrl = Movies.fullImageUrl(movie.backdropPath, backdropSize);
        Glide.with(this)
                .load(backdropUrl)
                .placeholder(R.drawable.placeholder_backdrop)
                .into(mAppBar.ivBackdrop);
    }

    private void loadText(Movie movie) {
        mBody.tvYear.setText(movie.year());
        mBody.tvVotes.setText(
                "Rating: "
                        + new DecimalFormat("#0.0").format(movie.voteAverage)
                        + " (" + movie.voteCount.toString() + " votes)"
        );
        mBody.tvOverview.setText(movie.overview);
    }

    private void loadData() {
        Disposable reviewsSub = repository.reviewsByMovieId(String.valueOf(mMovie.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mExtrasAdapter::setReviews,
                        err -> toast(this, err.getMessage())
                );
        compositeSub.add(reviewsSub);

        Disposable videosSub = repository.videosByMovieId(String.valueOf(mMovie.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mExtrasAdapter::setVideos,
                        err -> toast(this, err.getMessage())
                );
        compositeSub.add(videosSub);
    }

    private class FavoriteToggleListener implements CompoundButton.OnCheckedChangeListener {

        private Movie movie;

        FavoriteToggleListener(Movie movie) {
            this.movie = movie;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                // TODO: Favorite movie
                toast(DetailsActivity.this, "Favorite " + movie.title);
            } else {
                // TODO: Un-favorite movie
                toast(DetailsActivity.this, "Un-favorite " + movie.title);
            }
            // TODO: Notify change, anyone?
        }
    }

    static class AppBar {
        @BindView(R.id.collapsing_toolbar)
        CollapsingToolbarLayout collapsingToolbar;
        @BindView(R.id.iv_backdrop)
        ImageView ivBackdrop;
        @BindView(R.id.toolbar)
        Toolbar toolbar;
    }

    static class Body {
        @BindView(R.id.iv_poster)
        ImageView ivPoster;
        @BindView(R.id.tv_year)
        TextView tvYear;
        @BindView(R.id.tv_votes)
        TextView tvVotes;
        @BindView(R.id.tv_overview)
        TextView tvOverview;
        @BindView(R.id.video_list)
        FlexboxLayout videoList;
        @BindView(R.id.toggle_favortie)
        ToggleButton toggleFavorite;
        @BindView(R.id.review_list)
        LinearLayout reviewList;
    }
}
