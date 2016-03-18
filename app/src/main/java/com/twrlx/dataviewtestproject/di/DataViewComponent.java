package com.twrlx.dataviewtestproject.di;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;

import com.twrlx.dataviewtestproject.DataViewApplication;
import com.twrlx.dataviewtestproject.fragments.PhotosListFragment;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by TwRlx on 13.03.2016.
 */
@Singleton
@Component(modules = DataViewModule.class)
public interface DataViewComponent {
    void inject(DataViewApplication dataViewApplication);

    Context context();
    IntentFilter intentFilter();
    Retrofit.Builder retrofitBuilder();
}
