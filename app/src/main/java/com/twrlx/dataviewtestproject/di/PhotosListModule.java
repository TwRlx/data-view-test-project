package com.twrlx.dataviewtestproject.di;

import android.content.Context;

import com.twrlx.dataviewtestproject.adapters.PhotosAdapter;
import com.twrlx.dataviewtestproject.annotations.PhotosListFragmentScope;
import com.twrlx.dataviewtestproject.units.Album;
import com.twrlx.dataviewtestproject.units.AlbumPhoto;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TwRlx on 18.03.2016.
 */
@Module
public class PhotosListModule {
    private PhotosAdapter.PhotoViewClicksListener listener;

    public PhotosListModule(PhotosAdapter.PhotoViewClicksListener listener) {
        this.listener = listener;
    }

    @PhotosListFragmentScope
    @Provides PhotosAdapter.PhotoViewClicksListener providePhotoViewClicksListener(){
        return listener;
    }

    @PhotosListFragmentScope
    @Provides ArrayList<Album> provideAlbums(){
        return new ArrayList<Album>();
    }

    @PhotosListFragmentScope
    @Provides ArrayList<AlbumPhoto> provideAlbumPhotos(){
        return new ArrayList<AlbumPhoto>();
    }

    @PhotosListFragmentScope
    @Provides PhotosAdapter providePhotosAdapter(Context context, PhotosAdapter.PhotoViewClicksListener listener, ArrayList<AlbumPhoto> photos){
        return new PhotosAdapter(context, listener, photos);
    }

}
