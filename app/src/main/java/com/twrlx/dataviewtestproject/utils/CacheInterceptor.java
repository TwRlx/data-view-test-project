package com.twrlx.dataviewtestproject.utils;

import android.app.Application;
import android.content.Context;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor {

    private Context context;

    @Inject
    public CacheInterceptor(Application context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();

        if (!NetworkUtils.isNetworkAvailable(context)) {
            request.cacheControl(CacheControl.FORCE_CACHE);
        }

        Response response = chain.proceed(request.build());

        if (NetworkUtils.isNetworkAvailable(context)) {
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-stale=2419200")
                    .build();
        } else {
            return response;
        }
    }

}