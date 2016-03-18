package com.twrlx.dataviewtestproject.network;

import com.twrlx.dataviewtestproject.adapters.PhotosAdapter;
import com.twrlx.dataviewtestproject.units.Album;
import com.twrlx.dataviewtestproject.units.AlbumPhoto;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotosApi {

    private PhotosApiInterface mApiInterface;

    @Inject
    public PhotosApi(PhotosApiInterface mApiInterface) {
        this.mApiInterface = mApiInterface;
    }

    public void sendGetPhotosRequest(final ResponseHandler<ArrayList<AlbumPhoto>> responseHandler) {
        Call<ArrayList<AlbumPhoto>> call = mApiInterface.listPhotos();
        sendRequest(call, responseHandler);
    }

    public void sendGetAlbumsRequest(final ResponseHandler<ArrayList<Album>> responseHandler) {
        Call<ArrayList<Album>> call = mApiInterface.listAlbums();
        sendRequest(call, responseHandler);
    }

    public void sendGetAlbumPhotosRequest(long albumId, final ResponseHandler<ArrayList<AlbumPhoto>> responseHandler) {
        Call<ArrayList<AlbumPhoto>> call = mApiInterface.listAlbumPhotos(albumId);
        sendRequest(call, responseHandler);
    }

    public <T> void sendRequest(Call<T> call, final ResponseHandler<T> responseHandler){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    responseHandler.onResponse(response.body());
                } else {
                    responseHandler.onFailure();
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                responseHandler.onFailure();
            }

        });
    }

    public interface ResponseHandler<T>{
        void onResponse(T data);
        void onFailure();
    }
}