package com.twrlx.dataviewtestproject.network;

import com.twrlx.dataviewtestproject.units.Album;
import com.twrlx.dataviewtestproject.units.AlbumPhoto;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by TwRlx on 12.03.2016.
 */
public interface PhotosApiInterface {
    @GET("photos")
    Call<ArrayList<AlbumPhoto>> listPhotos();

    @GET("albums")
    Call<ArrayList<Album>> listAlbums();

    @GET("albums/{id}/photos")
    Call<ArrayList<AlbumPhoto>> listAlbumPhotos(@Path("id") long albumId);

}
