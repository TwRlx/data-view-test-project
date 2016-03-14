package com.twrlx.dataviewtestproject.di;

import android.app.Application;

import com.twrlx.dataviewtestproject.annotations.ClientCache;
import com.twrlx.dataviewtestproject.utils.CacheInterceptor;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TwRlx on 13.03.2016.
 */
@Module
public class DataViewModule {

    private Application context;

    public DataViewModule(Application context) {
        this.context = context;
    }


    @Singleton
    @Provides
    Application provideApplication() {
        return context;
    }

    @Singleton
    @Provides
    OkHttpClient provideCachedClient(@ClientCache File cacheDir, CacheInterceptor interceptor) {
        Cache cache = new Cache(cacheDir, 20 * 1024 * 1024);
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .cache(cache)
                .addNetworkInterceptor(interceptor)
                .addInterceptor(interceptor)
                .build();

        return okHttpClient;
    }


    @Singleton
    @Provides
    @ClientCache
    File provideCacheFile(Application context) {
        return new File(context.getCacheDir(), "cache_file");
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRestAdapterBuilder(OkHttpClient cachedClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(cachedClient);
    }

}
