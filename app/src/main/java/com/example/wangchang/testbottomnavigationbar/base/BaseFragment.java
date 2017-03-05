package com.example.wangchang.testbottomnavigationbar.base;import android.app.Fragment;import android.os.Bundle;import rx.subjects.PublishSubject;/** * Created by liying on 2017-2-24. */public class BaseFragment extends Fragment{    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubjectFragment = PublishSubject.create();   /* @Nullable    @Override    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        lifecycleSubjectFragment.onNext(ActivityLifeCycleEvent.CREATE);        return super.onCreateView(inflater, container, savedInstanceState);    }    @Override    public void onDestroyView() {        lifecycleSubjectFragment.onNext(ActivityLifeCycleEvent.PAUSE);        super.onDestroyView();    }*/    @Override    public void onCreate(Bundle savedInstanceState) {        lifecycleSubjectFragment.onNext(ActivityLifeCycleEvent.CREATE);        super.onCreate(savedInstanceState);    }    @Override    public void onPause() {        lifecycleSubjectFragment.onNext(ActivityLifeCycleEvent.PAUSE);        super.onPause();    }    @Override    public void onStop() {        lifecycleSubjectFragment.onNext(ActivityLifeCycleEvent.STOP);        super.onStop();    }    @Override    public void onDestroy() {        super.onDestroy();        lifecycleSubjectFragment.onNext(ActivityLifeCycleEvent.DESTROY);    }}