package com.example.wanqian.presenter;

import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.example.wanqian.bean.TaskListBean;
import com.example.wanqian.main.zhu.utils.MapBdUtils;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseObserver;
import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.pView.MapFunctionFrView;
import com.example.wanqian.utitls.ToastUtils;

import java.text.DecimalFormat;
import java.util.List;

import okhttp3.RequestBody;

/**
 * MVP的使用，使Activity中的网络请求剥离出来 成为model、presenter，model只负责网络的请求
 * pesenter负责处理请求网络后的数据处理：加载中 成功 or 失败 取消加载；最后View进行界面的展示
 */
public class MapFunctionFrPresenter extends BasePresenter<MapFunctionFrView> {

    private static final String TAG = "MapFunctionFrPresenter";
    final DecimalFormat decimalFormat = new DecimalFormat("0.0");
    private String task_uuid;

    public MapFunctionFrPresenter(MapFunctionFrView baseView) {
        super(baseView);
    }

    /**
     * 任务列表接口接口
     *
     * @param iMei
     */
    public void getJobList(String iMei) {
        addDisposable(apiServer.current_postFiled(iMei), new BaseObserver(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.getTaskListSuccess((List<TaskListBean>) o.getData());
            }

            @Override
            public void onError(String msg) {
                baseView.getTaskListFail(msg);
            }

            @Override
            public void onCode(String code) {
            }
        });
    }

    /**
     * 任务开始进行中
     *
     * @param iMei
     */
    public void StartClearTask(String iMei) {
        // startClear
        addDisposable(apiServer.current_StartClear(iMei, "apptest"), new BaseObserver(baseView) {

            @Override
            public void onSuccess(BaseModel o) {
                baseView.startTaskSuccess(o);
            }

            @Override
            public void onError(String msg) {
                baseView.startTaskFail(msg);
            }

            @Override
            public void onCode(String code) {

            }
        });
    }

    /**
     * 点击结束当前的任务
     */
    public void finishTask(RequestBody requestBody) {
        addDisposable(apiServer.current_EndClear(requestBody), new BaseObserver(baseView) {

            @Override
            public void onSuccess(BaseModel o) {
                baseView.endTaskSuccess(o);
            }

            @Override
            public void onError(String msg) {
                baseView.endTaskFail(null);
                ToastUtils.showSingleToast(msg);
            }

            @Override
            public void onCode(String code) {

            }
        });
    }

    /**
     * 清理任务完成
     */
    public void endClearTask(String imei, String grouup, int type, int cid, double lat, double lon, int index) {
        addDisposable(apiServer.current_ClearUp(imei, grouup, type, cid, lat, lon), new BaseObserver(baseView) {

            @Override
            public void onSuccess(BaseModel o) {
                baseView.clearComplete(index);
            }

            @Override
            public void onError(String msg) {
                baseView.clearTaskFail(msg);
            }

            @Override
            public void onCode(String code) {

            }
        });
    }

    /**
     * 上报当前的位置
     *
     * @param imei
     * @param lat
     * @param lon
     */
    public void UpPositionMessage(String imei, double lat, double lon) {
        Log.i(TAG, "UpPositionMessage: " + lat + "  " + lon);
        addDisposable(apiServer.current_UpdateCurrLocation(imei, lat, lon), new BaseObserver(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                if (task_uuid == null) {
                    task_uuid = o.getData().toString();
                    getJobList(baseView.getImei());
                }
                // 重新获取清收线路
                if (!task_uuid.equals(o.getData().toString())) {
                    task_uuid = o.getData().toString();
                    getJobList(baseView.getImei());
                }
            }

            @Override
            public void onError(String msg) {
                ToastUtils.showSingleToast(msg);
            }

            @Override
            public void onCode(String code) {
                baseView.processServiceEndOrCarNotTurnOn(code);
            }
        });
    }

    /**
     * 获取经纬度代表的位置信息
     */
    public void speakLocation(List<LatLng> roads) {
        double distance = 0;
        for (int i = 0; i < roads.size() - 1; i++) {
            distance += MapBdUtils.calculateLineDistance(roads.get(i), roads.get(i + 1));
        }
        baseView.speak("下次清收点距您" + decimalFormat.format(distance) + "米，请按指定线路行驶");
    }

    /**
     * Model接口 创建对应的联网请求的方法，将Presenter提交的字段放到联网请求中，发送给服务器
     * View 接口 创建在界面上显示加载中、取消加载以及登陆成功、失败的方法
     * Presenter 接口 创建 登陆的方法，以及需要提交的字段 (username、password)
     */

}
