package com.example.wanqian.ui.locationMessage;

import android.view.View;
import android.widget.TextView;
import com.example.wanqian.R;
import com.example.wanqian.bean.AllMessageDeviceBean;
import com.example.wanqian.newbase.NewBaseFragment;
import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.utitls.Utils;
import butterknife.BindView;


public class LocationPopFragment extends NewBaseFragment {
    @BindView(R.id.rb_sn)
    TextView rb_sn;
    @BindView(R.id.rb_combination)
    TextView rb_combination;
    @BindView(R.id.rb_r)
    TextView rb_r;
    @BindView(R.id.rb_percentage)
    TextView rb_percentage;
    @BindView(R.id.rb_csq)
    TextView rb_csq;
    @BindView(R.id.rb_batV)
    TextView rb_batV;
    @BindView(R.id.rb_angle)
    TextView rb_angle;
    @BindView(R.id.rb_temp)
    TextView rb_temp;
    @BindView(R.id.rb_ProductModel)
    TextView rb_ProductModel;
    @BindView(R.id.rb_connect_type)
    TextView rb_connect_type;
    @BindView(R.id.rb_lng)
    TextView rb_lng;
    @BindView(R.id.rb_lat)
    TextView rb_lat;
    @BindView(R.id.rb_online)
    TextView rb_online;
    @BindView(R.id.rb_report)
    TextView rb_report;
    @BindView(R.id.rb_man)
    TextView rb_man;
    @BindView(R.id.rb_tel)
    TextView rb_tel;

    private AllMessageDeviceBean deviceAllMessageBean;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initListener() {
        deviceAllMessageBean= (AllMessageDeviceBean) getArguments().get("data");
        if (deviceAllMessageBean!=null){
            rb_sn.setText(deviceAllMessageBean.getDevId()==null?"- -":deviceAllMessageBean.getDevId());
            rb_combination.setText(deviceAllMessageBean.getCombination()==null?"- -":deviceAllMessageBean.getCombination());
            rb_r.setText(deviceAllMessageBean.getRubbishL()+"mm");
            if (deviceAllMessageBean.getRubbishL()!=null&&deviceAllMessageBean.getRubbishHeight()!=null){
                String dem= Utils.division(Double.valueOf(deviceAllMessageBean.getRubbishL())*100,Double.valueOf(deviceAllMessageBean.getRubbishHeight()));
                rb_percentage.setText(dem+"%");
            }else{
                rb_percentage.setText("- -");
            }
            rb_batV.setText(deviceAllMessageBean.getBatV()==null?"- -":deviceAllMessageBean.getBatV()+"mV");

            java.text.DecimalFormat myformat=new java.text.DecimalFormat("##0.00");
            if (deviceAllMessageBean.getTmp()!=null){
                String str = myformat.format(Double.valueOf(deviceAllMessageBean.getTmp())/100);
                rb_temp.setText(str+"???");
            }else{
                rb_temp.setText("- -");
            }
            rb_angle.setText(deviceAllMessageBean.getAngle()==null?"- -":deviceAllMessageBean.getAngle()+"??");
            rb_csq.setText(deviceAllMessageBean.getSignalQ()==null?"- -dBm":deviceAllMessageBean.getSignalQ()+"dBm");
            rb_ProductModel.setText(deviceAllMessageBean.getProductModel()==null?"- -":deviceAllMessageBean.getProductModel());
            if (deviceAllMessageBean.getConnectType()==null){
                rb_connect_type.setText("- -");
            }else{
                if (deviceAllMessageBean.getConnectType().equals("1")){
                    rb_connect_type.setText("TCP????????????");
                }else if (deviceAllMessageBean.getConnectType().equals("2")){
                    rb_connect_type.setText("UDP????????????");
                }else if (deviceAllMessageBean.getConnectType().equals("3")){
                    rb_connect_type.setText("Ocean IOT??????");
                }else if (deviceAllMessageBean.getConnectType().equals("4")){
                    rb_connect_type.setText("OneNet ??????");
                }else if (deviceAllMessageBean.getConnectType().equals("5")){
                    rb_connect_type.setText("UNICOM IOT??????");
                }else if (deviceAllMessageBean.getConnectType().equals("6")){
                    rb_connect_type.setText("??????LORAWAN");
                }else{
                    rb_connect_type.setText("AliIOT");
                }
            }
            rb_lat.setText(deviceAllMessageBean.getLatitude()==null?"- -":deviceAllMessageBean.getLatitude());
            rb_lng.setText(deviceAllMessageBean.getLongitude()==null?"- -":deviceAllMessageBean.getLongitude());
            if (deviceAllMessageBean.getOnlineStatus()==null){
                rb_online.setText("- -");
            }else{
                if (deviceAllMessageBean.getOnlineStatus().equals("1")){
                    rb_online.setText("??????");
                }else if (deviceAllMessageBean.getOnlineStatus().equals("2")){
                    rb_online.setText("??????");
                }else {
                    rb_online.setText("??????");
                }
            }

            if (deviceAllMessageBean.getReportType()==null){
                rb_report.setText("- -");
            }else{
                String[] listData=deviceAllMessageBean.getReportType().split(",");
//                    '0': '????????????',
//                            '1': '????????????',
//                            '2': '????????????',
//                            '4': '????????????',
//                            '8': '????????????',
//                            '16': '????????????',
//                            '32': '????????????',
//                            '64': '????????????',
//                            '128': '????????????'
                String content="";
                for (int i=0;i<listData.length;i++){
                    if (listData[i].equals("0")){
                        content=content+"????????????,";
                    }else if (listData[i].equals("1")){
                        content=content+"????????????,";
                    }else if (listData[i].equals("2")){
                        content=content+"????????????,";
                    }else if (listData[i].equals("4")){
                        content=content+"????????????,";
                    }else if (listData[i].equals("8")){
                        content=content+"????????????,";
                    }else if (listData[i].equals("16")){
                        content=content+"????????????,";
                    }else if (listData[i].equals("32")){
                        content=content+"????????????,";
                    }else if (listData[i].equals("64")){
                        content=content+"????????????,";
                    }else{
                        content=content+"????????????,";
                    }
                }
                if (content.endsWith(",")){
                    String real=content.substring(0,content.length()-1);
                    rb_report.setText(real);
                }else{
                    rb_report.setText(content);
                }
            }
            if (deviceAllMessageBean.getDevStatus()!=null){
//                '0': '??????',         // ??????????????????
//                        '1': '??????/??????',  // ??????????????????
//                        '2': '?????????????????????',// ???????????????????????????
//                        '4': '?????????????????????',
//                        '8': '?????????????????????',
//                        '16': '??????????????????',
//                        '32': 'RTC ????????????',
//                        '64': '????????????',
//                        '128': 'GPS ??????
                if (deviceAllMessageBean.getDevStatus().equals("0")){
                    rb_man.setText("??????");
                }else if (deviceAllMessageBean.getDevStatus().equals("1")){
                    rb_man.setText("??????/??????");
                }else if (deviceAllMessageBean.getDevStatus().equals("2")){
                    rb_man.setText("?????????????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("4")){
                    rb_man.setText("?????????????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("8")){
                    rb_man.setText("?????????????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("16")){
                    rb_man.setText("??????????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("32")){
                    rb_man.setText("RTC ????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("64")){
                    rb_man.setText("????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("128")){
                    rb_man.setText("GPS ??????");
                }
            }else{

            }
            if (deviceAllMessageBean.getDevStatus()==null){
                rb_man.setText("- -");
            }else{
//                '0': '??????',         // ??????????????????
//                        '1': '??????/??????',  // ??????????????????
//                        '2': '?????????????????????',// ???????????????????????????
//                        '4': '?????????????????????',
//                        '8': '?????????????????????',
//                        '16': '??????????????????',
//                        '32': 'RTC ????????????',
//                        '64': '????????????',
//                        '128': 'GPS ??????'
                if (deviceAllMessageBean.getDevStatus().equals("0")){
                    rb_man.setText("??????");
                }else if (deviceAllMessageBean.getDevStatus().equals("1")){
                    rb_man.setText("??????/??????");
                }else if (deviceAllMessageBean.getDevStatus().equals("2")){
                    rb_man.setText("?????????????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("4")){
                    rb_man.setText("?????????????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("8")){
                    rb_man.setText("?????????????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("16")){
                    rb_man.setText("??????????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("32")){
                    rb_man.setText("RTC ????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("64")){
                    rb_man.setText("????????????");
                }else if (deviceAllMessageBean.getDevStatus().equals("128")){
                    rb_man.setText("GPS ??????");
                }

            }

            rb_tel.setText(deviceAllMessageBean.getEventTime()==null?"":deviceAllMessageBean.getEventTime());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_location;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }
}
