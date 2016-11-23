package com.redbooth.comics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

class ComicViewHolder extends RecyclerView.ViewHolder {
    private ImageView thumbnail;
    private TextView  title;
    private Comic     item;

    ComicViewHolder(View itemView) {
        super(itemView);
        this.thumbnail = (ImageView) itemView.findViewById(com.redbooth.comics.R.id.thumbnail);
        this.title = (TextView) itemView.findViewById(com.redbooth.comics.R.id.title);
    }

    void bind(Comic comic) {
        item = comic;
        title.setText(comic.getTitle());
        Picasso.with(itemView.getContext())
               .load(comic.getThumbnailURL())
               .fit()
               .centerInside()
               .into(thumbnail);
    }
}
