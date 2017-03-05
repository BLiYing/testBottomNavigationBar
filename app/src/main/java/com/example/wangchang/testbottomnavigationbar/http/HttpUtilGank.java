package com.example.wangchang.testbottomnavigationbar.http;

import com.example.wangchang.testbottomnavigationbar.base.ActivityLifeCycleEvent;
import com.example.wangchang.testbottomnavigationbar.enity.HttpResultGank;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

/**
 *
 *
 //  ┏┓　　　┏┓
 //┏┛┻━━━┛┻┓
 //┃　　　　　　　┃
 //┃　　　━　　　┃
 //┃　┳┛　┗┳　┃
 //┃　　　　　　　┃
 //┃　　　┻　　　┃
 //┃　　　　　　　┃
 //┗━┓　　　┏━┛
  //   ┃　　　┃   神兽保佑
  //   ┃　　　┃   代码无BUG！
  //   ┃　　　┗━━━┓
  //   ┃　　　　　　　┣┓
  //   ┃　　　　　　　┏┛
  //   ┗┓┓┏━┳┓┏┛
  //     ┃┫┫　┃┫┫
  //     ┗┻┛　┗┻┛
 *
 * Created by helin on 2016/10/10 11:32.
 */

public class HttpUtilGank {

    /**
     * 构造方法私有
     */
    private HttpUtilGank() {
    }

    /**
     * 在访问HttpMethods时创建单例
     */
    private static class SingletonHolder {
        private static final HttpUtilGank INSTANCE = new HttpUtilGank();
    }

    /**
     * 获取单例
     */
    public static HttpUtilGank getInstance() {
        return SingletonHolder.INSTANCE;
    }
    /**
     * 添加线程管理并订阅
     * @param ob
     * @param subscriber
     * @param cacheKey 缓存kay
     * @param event Activity 生命周期
     * @param lifecycleSubject
     * @param isSave 是否缓存
     * @param forceRefresh 是否强制刷新
     */
    @SuppressWarnings("unchecked")
    public void toSubscribe(Observable ob,
                            final ProgressSubscriber subscriber,
                            String cacheKey,
                            final ActivityLifeCycleEvent event,
                            final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject,
                            boolean isSave,
                            boolean forceRefresh,
                            final boolean isShowDialog) {
        //数据预处理
        Observable.Transformer<HttpResultGank<Object>, Object> result = RxHelperGank.handleResult(event,lifecycleSubject);
        Observable observable = ob.compose(result)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //显示Dialog和一些其他操作
                        if(isShowDialog)
                            subscriber.showProgressDialog();
                    }
                });
        RetrofitCache.load(cacheKey,observable,isSave,forceRefresh).subscribe(subscriber);
    }
}
