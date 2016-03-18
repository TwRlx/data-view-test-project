package com.twrlx.dataviewtestproject.di;

import com.twrlx.dataviewtestproject.annotations.PhotosListFragmentScope;
import com.twrlx.dataviewtestproject.fragments.PhotosListFragment;

import dagger.Component;

/**
 * Created by TwRlx on 18.03.2016.
 */
@PhotosListFragmentScope
@Component(dependencies = DataViewComponent.class, modules = {PhotosApiModule.class, PhotosListModule.class})
public interface PhotosListComponent {
    void inject(PhotosListFragment photosListFragment);
}