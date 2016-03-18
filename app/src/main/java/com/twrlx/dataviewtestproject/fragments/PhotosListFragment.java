package com.twrlx.dataviewtestproject.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twrlx.dataviewtestproject.DataViewApplication;
import com.twrlx.dataviewtestproject.R;
import com.twrlx.dataviewtestproject.adapters.PhotosAdapter;
import com.twrlx.dataviewtestproject.di.DaggerPhotosListComponent;
import com.twrlx.dataviewtestproject.di.DataViewComponent;
import com.twrlx.dataviewtestproject.di.PhotosListModule;
import com.twrlx.dataviewtestproject.network.PhotosApi;
import com.twrlx.dataviewtestproject.units.Album;
import com.twrlx.dataviewtestproject.units.AlbumPhoto;
import com.twrlx.dataviewtestproject.utils.NetworkUtils;
import com.twrlx.dataviewtestproject.views.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by TwRlx on 12.03.2016.
 */
public class PhotosListFragment extends Fragment implements PhotosAdapter.PhotoViewClicksListener{

    private static final String KEY_PHOTOS = "photos";
    private static final String KEY_ALBUMS = "albums";
    private static final String KEY_ALBUMS_LOADED_COUNT = "count";

    private BroadcastReceiver receiver;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PhotosListFragmentListener photosListFragmentListener;
    private int albumsLoadedCount = 0;

    @Inject PhotosAdapter photosAdapter;
    @Inject ArrayList<AlbumPhoto> photos;
    @Inject ArrayList<Album> albums;
    @Inject PhotosApi photosApi;
    @Inject IntentFilter connectionFilter;

    public PhotosListFragment() {
    }

    public static PhotosListFragment newInstance(){
        return new PhotosListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataViewComponent applicationComponent = ((DataViewApplication) getActivity().getApplicationContext())
                .getApplicationComponent();
        DaggerPhotosListComponent.builder()
                .dataViewComponent(applicationComponent)
                .photosListModule(new PhotosListModule(this))
                .build().inject(this);

        if (savedInstanceState != null){
            photos = (ArrayList<AlbumPhoto>) savedInstanceState.getSerializable(KEY_PHOTOS);
            albums = (ArrayList<Album>) savedInstanceState.getSerializable(KEY_ALBUMS);
            albumsLoadedCount = savedInstanceState.getInt(KEY_ALBUMS_LOADED_COUNT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_PHOTOS, photos);
        outState.putSerializable(KEY_ALBUMS, albums);
        outState.putInt(KEY_ALBUMS_LOADED_COUNT, albumsLoadedCount);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photos_list, container, false);

        swipeRefreshLayout= (SwipeRefreshLayout) rootView.findViewById(R.id.photos_swipe_refresh);

        if (albums == null || albums.size() == 0) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    loadAlbums();
                }
            });
        }

        photosListFragmentListener = (PhotosListFragmentListener) getActivity();
        photosAdapter.setPhotos(photos);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photos.clear();
                albumsLoadedCount = 0;
                photosAdapter.notifyDataSetChanged();
                if (albums == null || albums.size() == 0){
                    loadAlbums();
                }else{
                    loadMoreAlbumPhotos();
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.photos_recycler_view);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public boolean onLoadMore(int currentPage) {
                return loadMoreAlbumPhotos();
            }
        });
        mRecyclerView.setAdapter(photosAdapter);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                swipeRefreshLayout.setEnabled(NetworkUtils.isNetworkAvailable(context));
            }
        };

        return rootView;
    }

    private void loadAlbums() {
        photosApi.sendGetAlbumsRequest(new PhotosApi.ResponseHandler<ArrayList<Album>>() {
            @Override
            public void onResponse(ArrayList<Album> responseAlbums) {
                if (responseAlbums != null && responseAlbums.size() > 0) {
                    albums = responseAlbums;
                    loadMoreAlbumPhotos();
                }else{
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private boolean loadMoreAlbumPhotos() {
        if (albums == null || albums.size() == 0){
            return false;
        }

        if (albumsLoadedCount < albums.size()){
            Album album = albums.get(albumsLoadedCount);
            loadAlbumPhotos(album.getId());
            return true;
        }

        return false;
    }

    private void loadAlbumPhotos(long albumId) {
        photosApi.sendGetAlbumPhotosRequest(albumId, new PhotosApi.ResponseHandler<ArrayList<AlbumPhoto>>() {
            @Override
            public void onResponse(ArrayList<AlbumPhoto> responsePhotos) {
                if (responsePhotos != null && albums.size() > 0) {
                    photos.addAll(responsePhotos);
                    photosAdapter.notifyDataSetChanged();
                    albumsLoadedCount++;
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
            }
        } );
    }

    @Override
    public void onResume() {
        getActivity().registerReceiver(receiver, connectionFilter);
        super.onResume();
    }

    @Override
    public void onPause() {
        getActivity().unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    public void onPhotoItemClick(AlbumPhoto photo) {
        photosListFragmentListener.navigateToPhotoDetailsFragment(photo);
    }

    public interface PhotosListFragmentListener {
        void navigateToPhotoDetailsFragment(AlbumPhoto photo);
    }

}
