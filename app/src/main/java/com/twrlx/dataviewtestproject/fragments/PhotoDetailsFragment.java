package com.twrlx.dataviewtestproject.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twrlx.dataviewtestproject.R;
import com.twrlx.dataviewtestproject.units.AlbumPhoto;

/**
 * Created by TwRlx on 12.03.2016.
 */
public class PhotoDetailsFragment extends Fragment {

    private static final String KEY_PHOTO = "photo";
    private AlbumPhoto photo;

    public PhotoDetailsFragment() {
    }


    public static PhotoDetailsFragment newInstance(AlbumPhoto photo){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_PHOTO, photo);

        PhotoDetailsFragment photoDetailsFragment = new PhotoDetailsFragment();
        photoDetailsFragment.setArguments(bundle);

        return photoDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo_details, container, false);
        Bundle arguments = getArguments();

        if (arguments != null){
            photo = (AlbumPhoto) arguments.getSerializable(KEY_PHOTO);
        }

        TextView albumId = (TextView) rootView.findViewById(R.id.photo_album_id);
        albumId.setText(Long.toString(photo.getAlbumId()));

        TextView photoId = (TextView) rootView.findViewById(R.id.photo_id);
        photoId.setText(Long.toString(photo.getId()));

        TextView title = (TextView) rootView.findViewById(R.id.photo_title);
        title.setText(photo.getTitle());

        ImageView photoImageView = (ImageView) rootView.findViewById(R.id.photo);

        Glide.with(getActivity())
                .load(photo.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder_image)
                .into(photoImageView);

        return rootView;
    }
}
