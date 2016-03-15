package com.twrlx.dataviewtestproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twrlx.dataviewtestproject.R;
import com.twrlx.dataviewtestproject.fragments.PhotosListFragment;
import com.twrlx.dataviewtestproject.units.AlbumPhoto;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by TwRlx on 12.03.2016.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> implements View.OnClickListener{

    private Context context;
    private ArrayList<AlbumPhoto> photos;
    private PhotoViewClicksListener mListener;

    public PhotosAdapter(Context context, PhotoViewClicksListener viewClicksListener, ArrayList<AlbumPhoto> photos) {
        this.context = context;
        this.mListener = viewClicksListener;
        this.photos = photos;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_photo, parent, false);

        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        final AlbumPhoto albumPhoto = photos.get(position);

        holder.vTitleTextView.setText(albumPhoto.getTitle());
        holder.vItemLayout.setTag(position);
        holder.vItemLayout.setOnClickListener(this);
        String thumbnailUrl = albumPhoto.getThumbnailUrl();
        if (thumbnailUrl != null){
            Glide.with(context)
                    .load(thumbnailUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.vPhotoImageView);
        }else{
            Glide.clear(holder.vPhotoImageView);
            holder.vPhotoImageView.setImageDrawable(null);
        }
    }

    @Override
    public void onClick(View v) {
        mListener.onPhotoItemClick(photos.get((Integer) v.getTag()));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public PhotoViewClicksListener getListener() {
        return mListener;
    }

    public void setListener(PhotoViewClicksListener mListener) {
        this.mListener = mListener;
    }

    public void setPhotos(ArrayList<AlbumPhoto> photos) {
        this.photos = photos;
    }

    public ArrayList<AlbumPhoto> getPhotos() {
        return photos;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        protected TextView vTitleTextView;
        protected ImageView vPhotoImageView;
        protected RelativeLayout vItemLayout;

        public PhotoViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            vTitleTextView = (TextView) itemLayoutView.findViewById(R.id.photo_title);
            vPhotoImageView = (ImageView) itemLayoutView.findViewById(R.id.photo_thumbnail);
            vItemLayout = (RelativeLayout) itemLayoutView.findViewById(R.id.photo_item_layout);
        }
    }

    public interface PhotoViewClicksListener {
        void onPhotoItemClick(AlbumPhoto photo);
    }

}
