// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.wangchang.testbottomnavigationbar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangchang.testbottomnavigationbar.enity.ResultsEntity;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class HomeAdapter extends RecyclerArrayAdapter<ResultsEntity> {
    public HomeItemOnclickListen mOnItemClickListener;
    public HomeAdapter(Context context) {
        super(context);
    }
    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GirlsViewHolder(parent);
    }

    @Override
    public void OnBindViewHolder(final BaseViewHolder holder, final int position) {
        super.OnBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, holder);
                }
            }
        });
    }

    public interface HomeItemOnclickListen{
        void onItemClick(int position,BaseViewHolder viewHold);
    }

    public void setOnItemClickListener(HomeItemOnclickListen mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }



}
