package com.twrlx.dataviewtestproject;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.twrlx.dataviewtestproject.fragments.PhotoDetailsFragment;
import com.twrlx.dataviewtestproject.fragments.PhotosListFragment;
import com.twrlx.dataviewtestproject.units.AlbumPhoto;

public class MainActivity extends AppCompatActivity implements PhotosListFragment.PhotosListFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_placeholder, PhotosListFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void navigateToPhotoDetailsFragment(AlbumPhoto photo) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, PhotoDetailsFragment.newInstance(photo))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }else{
            super.onBackPressed();
        }
    }

}
