package com.example.wangchang.testbottomnavigationbar.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.example.wangchang.testbottomnavigationbar.R;
import com.example.wangchang.testbottomnavigationbar.activity.MainActivity;
import com.example.wangchang.testbottomnavigationbar.activity.girl.GirlActivity;
import com.example.wangchang.testbottomnavigationbar.adapter.HomeAdapter;
import com.example.wangchang.testbottomnavigationbar.base.ActivityLifeCycleEvent;
import com.example.wangchang.testbottomnavigationbar.base.CacheKey;
import com.example.wangchang.testbottomnavigationbar.base.DataKey;
import com.example.wangchang.testbottomnavigationbar.enity.ResultsEntity;
import com.example.wangchang.testbottomnavigationbar.http.Api;
import com.example.wangchang.testbottomnavigationbar.http.HttpUtilGank;
import com.example.wangchang.testbottomnavigationbar.http.ProgressSubscriber;
import com.example.wangchang.testbottomnavigationbar.util.LogUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;

import static android.content.ContentValues.TAG;
import static com.example.wangchang.testbottomnavigationbar.R.id.gridRv;

/**
 * Created by WangChang on 2016/5/15.
 */
public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerArrayAdapter.OnLoadMoreListener {

    @BindView(gridRv)
    EasyRecyclerView mEasyRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.home_network_error_layout)
    ViewStub homeNetworkErrorLayout;


    private Context mContext;
    private View rootView;
    private Unbinder mUnbinder;
    private HomeAdapter mHomeAdapter;

    private int page = 1;
    private int size = 10;
    private ArrayList<ResultsEntity> data;
    private View networkErrorView;
//    TextView tryagain_a_tv;

    public static HomeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView != null) {

            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            mUnbinder = ButterKnife.bind(this, rootView);
            initView(rootView);
            getGirls(size, page, true, CacheKey.FIRSTGETGIRLS, true, true, true);

        }
        return rootView;
    }

    private void initView(View view) {
        data = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(mContext);
        mEasyRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
//        mEasyRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mEasyRecyclerView.setAdapterWithProgress(mHomeAdapter);
        mHomeAdapter.setMore(R.layout.load_more_layout, this);
        mHomeAdapter.setNoMore(R.layout.no_more_layout);
        mHomeAdapter.setError(R.layout.error_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setProgressViewOffset(false,DataKey.swipeStart, DataKey.swipeEnd);
//        swipeRefreshLayout.setProgressViewEndTarget (true,200);
        swipeRefreshLayout.setOnRefreshListener(this);
        mHomeAdapter.setOnItemClickListener(new HomeAdapter.HomeItemOnclickListen() {
            @Override
            public void onItemClick(int position, BaseViewHolder viewHold) {
                Intent intent = new Intent(mContext, GirlActivity.class);
                intent.putParcelableArrayListExtra("girls", data);
                intent.putExtra("current", position);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(viewHold.itemView, viewHold.itemView.getWidth() / 2, viewHold.itemView.getHeight() / 2, 0, 0);
                startActivity(intent, options.toBundle());
            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void getGirls(int count, int page,
                          final boolean isfresh,
                          final String cacheKey,
                          final boolean issave,
                          final boolean forceRefresh,
                          final boolean isShowDialog) {
        Observable ob = Api.getGankService().getBeauties(count, page);
        HttpUtilGank.getInstance().toSubscribe(ob, new ProgressSubscriber<List<ResultsEntity>>(mContext) {
                    @Override
                    protected void _onError(String message) {
                        if (isfresh) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        showError();
                    }

                    @Override
                    protected void _onNext(List<ResultsEntity> list) {

                        if (isfresh) {
                            refresh(list);
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            load(list);
                        }
                        showNormal();

                    }

                },
                cacheKey,
                ActivityLifeCycleEvent.CREATE,
                MainActivity.getInstance().getLifeSubject(),
                issave,
                forceRefresh,
                isShowDialog);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        getGirls(size, 1, true, CacheKey.FIRSTGETGIRLS, true, true, false);
        page = 1;

    }

    @Override
    public void onLoadMore() {
        if (data.size() % 10 == 0) {
            LogUtil.d(TAG, "onloadmore");
            page++;
            getGirls(size, page, false, CacheKey.LOADMOREGIRLS + String.valueOf(page), true, false, false);
        }
    }

    private void refresh(List<ResultsEntity> datas) {
        data.clear();
        data.addAll(datas);
        mHomeAdapter.clear();
        mHomeAdapter.addAll(datas);
        if ((datas != null && datas.size() <= 5) || datas == null) {
            mHomeAdapter.stopMore();
        }

    }

    public void load(List<ResultsEntity> datas) {
        data.addAll(datas);
        mHomeAdapter.addAll(datas);
        if ((datas != null && datas.size() == 0) || datas == null) {
            mHomeAdapter.stopMore();
        }
    }

    public void showError() {
        mEasyRecyclerView.showError();

        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.VISIBLE);
            return;
        }
        networkErrorView = homeNetworkErrorLayout.inflate();
        TextView textView = (TextView) networkErrorView.findViewById(R.id.tryagain_tv);
        if(textView != null)
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    swipeRefreshLayout.setRefreshing(true);
                    getGirls(size, page, true, CacheKey.FIRSTGETGIRLS, true, false, false);
                }
            });


    }

    public void showNormal() {

        if (networkErrorView != null) {
            networkErrorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       /* if (mUnbinder != null) {
            mUnbinder.unbind();
        }*/
    }


}
