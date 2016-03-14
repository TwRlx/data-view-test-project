package com.twrlx.dataviewtestproject.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twrlx.dataviewtestproject.DataViewApplication;
import com.twrlx.dataviewtestproject.R;
import com.twrlx.dataviewtestproject.adapters.PhotosAdapter;
import com.twrlx.dataviewtestproject.requests.PhotosListRequest;
import com.twrlx.dataviewtestproject.units.AlbumPhoto;
import com.twrlx.dataviewtestproject.utils.NetworkUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TwRlx on 12.03.2016.
 */
public class PhotosListFragment extends Fragment implements PhotosAdapter.PhotoViewClicksListener, Callback<ArrayList<AlbumPhoto>>{

    private PhotosListFragmentListener photosListFragmentListener;
    private BroadcastReceiver receiver;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PhotosAdapter photosAdapter;
    private ArrayList<AlbumPhoto> photos = new ArrayList<>();

    @Inject PhotosListRequest photosListRequest;
    @Inject IntentFilter connectionFilter;

    public PhotosListFragment() {
    }

    public static PhotosListFragment newInstance(){
        return new PhotosListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photos_list, container, false);

        ((DataViewApplication) getActivity().getApplicationContext())
                .getApplicationComponent().inject(this);

        final Call<ArrayList<AlbumPhoto>> photosCalls = photosListRequest.listPhotos();
        swipeRefreshLayout= (SwipeRefreshLayout) rootView.findViewById(R.id.photos_swipe_refresh);
        photosCalls.enqueue(this);

        photosListFragmentListener = (PhotosListFragmentListener) getActivity();
        photosAdapter = new PhotosAdapter(getActivity(), this, photos);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photos.clear();
                photosAdapter.notifyDataSetChanged();

                Call<ArrayList<AlbumPhoto>> photosCalls = photosListRequest.listPhotos();
                photosCalls.enqueue(PhotosListFragment.this);
            }
        });

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.photos_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(photosAdapter);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                swipeRefreshLayout.setEnabled(NetworkUtils.isNetworkAvailable(context));
            }
        };

        return rootView;
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

    @Override
    public void onResponse(Call<ArrayList<AlbumPhoto>> call, Response<ArrayList<AlbumPhoto>> response) {

        ArrayList<AlbumPhoto> responsePhotos = response.body();
        if (responsePhotos != null) {
            photos = responsePhotos;
            photosAdapter.setPhotos(photos);
            photosAdapter.notifyDataSetChanged();
        }

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<ArrayList<AlbumPhoto>> call, Throwable t) {
        swipeRefreshLayout.setRefreshing(false);
    }

    public interface PhotosListFragmentListener {
        void navigateToPhotoDetailsFragment(AlbumPhoto photo);
    }
}
