package com.example.wanqian.presenter;

import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseObserver;
import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.utitls.SPUtils;
import java.util.HashMap;
public class CarMessagePresenter extends BasePresenter<BaseBckDataView> {
    public CarMessagePresenter(BaseBckDataView baseView) {
        super(baseView);
    }
    /**
     * 车辆信息接口
     */
    public void getCarMessage(String userName,String pageNo,String pageSize) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", SPUtils.getValue("yhm"));
        map.put("pageNo","1");
        map.put("pageSize","20");
        addDisposable(apiServer.getHistoryWork(map), new BaseObserver(baseView) {

            @Override
            public void onSuccess(BaseModel o) {
                baseView.getBaseData(o);
            }
            @Override
            public void onError(String msg) {
                baseView.getErrorMsg(msg);
            }

            @Override
            public void onCode(String code) {

            }
        });
    }
}
