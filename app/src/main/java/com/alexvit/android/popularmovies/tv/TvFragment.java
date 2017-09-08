package com.alexvit.android.popularmovies.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v4.content.ContextCompat;

import com.alexvit.android.popularmovies.R;
import com.alexvit.android.popularmovies.base.BaseBrowseFragment;
import com.alexvit.android.popularmovies.data.MoviesRepository;
import com.alexvit.android.popularmovies.data.models.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alexander.vitjukov on 08.09.2017.
 */

public class TvFragment extends BaseBrowseFragment {

    private static final String TAG = TvFragment.class.getSimpleName();

    @Inject
    MoviesRepository moviesRepository;
    @Inject
    ArrayObjectAdapter mCategoryAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupUi();

        getComponent().inject(this);
        setAdapter(mCategoryAdapter);

        moviesRepository.moviesByPopularity()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> displayCategory(0, "Popular", movies)
                );
        moviesRepository.moviesByRating()
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        movies -> displayCategory(1, "Top rated", movies)
                );
    }

    private void setupUi() {
        setTitle(getString(R.string.app_name));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(ContextCompat.getColor(getActivity(), R.color.primary));
    }

    private void displayCategory(int index, String title, List<Movie> movies) {
//        Log.d(TAG, "Got movies = " + movies.size());
        HeaderItem header = new HeaderItem(title);
        ArrayObjectAdapter moviesAdapter = new ArrayObjectAdapter(new MovieCardPresenter());
        moviesAdapter.addAll(0, movies);
        ListRow listRow = new ListRow(header, moviesAdapter);
        mCategoryAdapter.add(index, listRow);
    }
}
