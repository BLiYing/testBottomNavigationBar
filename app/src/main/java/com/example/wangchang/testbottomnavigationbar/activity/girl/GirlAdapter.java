package com.example.wangchang.testbottomnavigationbar.activity.girl;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.wangchang.testbottomnavigationbar.R;
import com.example.wangchang.testbottomnavigationbar.widget.PinchImageView;
import com.example.wangchang.testbottomnavigationbar.enity.ResultsEntity;

import java.util.ArrayList;


/**
 * Created by oracleen on 2016/7/4.
 */
public class GirlAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<ResultsEntity> mDatas;
    private LayoutInflater layoutInflater;
    private View mCurrentView;

    public GirlAdapter(Context context, ArrayList<ResultsEntity> datas) {
        mContext = context;
        mDatas = datas;
        layoutInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
    }

    public View getPrimaryItem() {
        return mCurrentView;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final String imageUrl = mDatas.get(position).getUrl();
        View view = layoutInflater.inflate(R.layout.item_girl_detail, container, false);
        PinchImageView imageView = (PinchImageView) view.findViewById(R.id.img);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.app_icon)
                .error(R.drawable.icon_error)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);


        Glide.with(mContext)
                .load(imageUrl)
                .apply(options)
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
