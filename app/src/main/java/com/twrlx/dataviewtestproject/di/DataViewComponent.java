package com.twrlx.dataviewtestproject.di;

import com.twrlx.dataviewtestproject.DataViewApplication;
import com.twrlx.dataviewtestproject.fragments.PhotosListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by TwRlx on 13.03.2016.
 */
@Singleton
@Component(modules = {PhotosApiModule.class, DataViewModule.class})
public interface DataViewComponent {
    void inject(DataViewApplication dataViewApplication);
    void inject(PhotosListFragment photosListFragment);
}
