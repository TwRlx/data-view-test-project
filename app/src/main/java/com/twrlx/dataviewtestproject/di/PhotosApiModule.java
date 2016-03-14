package com.twrlx.dataviewtestproject.di;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.twrlx.dataviewtestproject.requests.PhotosListRequest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by TwRlx on 13.03.2016.
 */
@Module
public class PhotosApiModule {

    @Singleton
    @Provides
    PhotosListRequest providePhotosRequest(Retrofit.Builder builder){
        Retrofit retrofit = builder
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .build();
        return retrofit.create(PhotosListRequest.class);
    }

    @Singleton
    @Provides
    IntentFilter provideConnectivityIntentFilter(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        return filter;
    }


}
