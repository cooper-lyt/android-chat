/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfire.chat.kit.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.wildfire.chat.kit.R;
import cn.wildfire.chat.kit.favorite.viewholder.FavContentViewHolder;
import cn.wildfire.chat.kit.favorite.viewholder.FavImageContentViewHolder;
import cn.wildfire.chat.kit.favorite.viewholder.FavTextContentViewHolder;
import cn.wildfire.chat.kit.favorite.viewholder.FavUnknownContentViewHolder;
import cn.wildfirechat.message.core.MessageContentType;

public class FavoriteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Fragment favListFragment;
    private List<FavoriteItem> favoriteItems = new ArrayList<>();

    public FavoriteListAdapter(Fragment favListFragment) {
        this.favListFragment = favListFragment;
    }

    public void addFavoriteItems(List<FavoriteItem> newItems) {
        this.favoriteItems.addAll(newItems);
    }

    public List<FavoriteItem> getFavoriteItems() {
        return favoriteItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        ViewStub viewStub;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_content_container, parent, false);
        viewStub = itemView.findViewById(R.id.favContentViewStub);
        viewStub.setLayoutResource(getFavContentLayoutResource(viewType));
        viewStub.inflate();
        return getFavContentViewHolder(itemView, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        FavoriteItem item = favoriteItems.get(position);
        return item.getFavType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((FavContentViewHolder) holder).bind(favListFragment, this.favoriteItems.get(position));
    }

    @Override
    public int getItemCount() {
        return this.favoriteItems == null ? 0 : this.favoriteItems.size();
    }

    private int getFavContentLayoutResource(int favType) {
        int layoutResourceId;
        switch (favType) {
            case MessageContentType.ContentType_Text:
                layoutResourceId = R.layout.fav_content_text;
                break;
            case MessageContentType.ContentType_Image:
                layoutResourceId = R.layout.fav_content_image;
                break;
            default:
                layoutResourceId = R.layout.fav_content_unkown;
                break;
        }
        return layoutResourceId;
    }

    private RecyclerView.ViewHolder getFavContentViewHolder(View itemView, int favType) {
        RecyclerView.ViewHolder viewHolder;
        switch (favType) {
            case MessageContentType.ContentType_Text:
                viewHolder = new FavTextContentViewHolder(itemView);
                break;
            case MessageContentType.ContentType_Image:
                viewHolder = new FavImageContentViewHolder(itemView);
                break;
            default:
                viewHolder = new FavUnknownContentViewHolder(itemView);
                break;
        }
        return viewHolder;
    }
}
