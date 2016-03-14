package com.twrlx.dataviewtestproject.requests;

import com.twrlx.dataviewtestproject.units.AlbumPhoto;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by TwRlx on 12.03.2016.
 */
public interface PhotosListRequest {
    @GET("photos")
    Call<ArrayList<AlbumPhoto>> listPhotos();
}
