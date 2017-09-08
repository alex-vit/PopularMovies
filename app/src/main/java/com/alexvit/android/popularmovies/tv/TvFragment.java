package com.alexvit.android.popularmovies.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v4.content.ContextCompat;

import com.alexvit.android.popularmovies.R;
import com.alexvit.android.popularmovies.base.BaseBrowseFragment;

import javax.inject.Inject;

/**
 * Created by alexander.vitjukov on 08.09.2017.
 */

public class TvFragment extends BaseBrowseFragment {

    @Inject
    ArrayObjectAdapter mCategoryAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupUi();

        getComponent().inject(this);
        setAdapter(mCategoryAdapter);
    }

    private void setupUi() {
        setTitle(getString(R.string.app_name));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(ContextCompat.getColor(getActivity(), R.color.primary));
    }
}
