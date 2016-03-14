package com.twrlx.dataviewtestproject;

import android.app.Application;

import com.twrlx.dataviewtestproject.di.DaggerDataViewComponent;
import com.twrlx.dataviewtestproject.di.DataViewComponent;
import com.twrlx.dataviewtestproject.di.DataViewModule;
import com.twrlx.dataviewtestproject.di.PhotosApiModule;

/**
 * Created by TwRlx on 13.03.2016.
 */


public class DataViewApplication extends Application {
    private DataViewComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerDataViewComponent.builder()
                .dataViewModule(new DataViewModule(this))
                .photosApiModule(new PhotosApiModule())
                .build();
        component.inject(this);

    }

    public DataViewComponent getApplicationComponent() {
        return component;
    }
}
