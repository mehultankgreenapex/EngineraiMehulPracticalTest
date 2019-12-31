package com.example.myapplication.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.RowPostListBinding;
import com.example.myapplication.model.HitsItem;

import java.util.List;

public class PostListAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HitsItem> postListItem;


    private RowPostListBinding rowPostListBinding;


    private boolean isLoading;

    public  PostListAdapater(Context context, List<HitsItem> postListItem) {
        this.postListItem = postListItem;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rowPostListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_post_list, parent, false);
        return new ViewHolderData(rowPostListBinding);

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);



    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ViewHolderData) holder).bindData(postListItem.get(position), position);
    }


    /**
     *
     * is viewholder class
     *
     */

    protected class ViewHolderData extends RecyclerView.ViewHolder {

        private RowPostListBinding binding;

        public ViewHolderData(RowPostListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(HitsItem item, int position) {
            binding.setPostModel(item);
            binding.executePendingBindings();
        }
    }

    /**
     *
     *
     * numer of list item return
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return postListItem.size();
    }



    public void setLoaded() {
        isLoading = false;
    }

    public void setLoading() {
        isLoading = true;
    }

    public boolean isLoading() {
        return isLoading;
    }


    /**
     * add list on apply more data
     * @param hitsItemList
     */
    public void addRecord(List<HitsItem> hitsItemList) {
        postListItem = hitsItemList;
    }
}
