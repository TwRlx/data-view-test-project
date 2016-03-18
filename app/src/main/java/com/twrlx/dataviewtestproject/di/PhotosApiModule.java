package com.twrlx.dataviewtestproject.di;

import com.twrlx.dataviewtestproject.annotations.PhotosListFragmentScope;
import com.twrlx.dataviewtestproject.network.PhotosApi;
import com.twrlx.dataviewtestproject.network.PhotosApiInterface;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by TwRlx on 13.03.2016.
 */
@Module
public class PhotosApiModule {

    @PhotosListFragmentScope
    @Provides
    PhotosApiInterface providePhotosRequest(Retrofit.Builder builder){
        Retrofit retrofit = builder
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .build();
        return retrofit.create(PhotosApiInterface.class);
    }

    @PhotosListFragmentScope
    @Provides
    PhotosApi providePhotosApi(PhotosApiInterface mApiInterface){
        return new PhotosApi(mApiInterface);
    }

}
