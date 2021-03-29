package com.example.wanqian.bean;

import com.example.wanqian.main.terminal.bean.TerminalBean;

import java.io.Serializable;
import java.util.List;

public class TerminalNewBean implements Serializable {
    /**
     * pageNo : 2
     * pages : 0
     * total : 104
     * pageSize : 1
     * list : [{"id":null,"devId":"WQDSN0AA000A0043","rubbishL":"380","rubbishR":"0","tmp":"1712","batV":"3651","angle":"3","signalQ":"20","reportType":"64","devStatus":"0","connectType":"电信NB","devSimNum":"","latitude":"","longitude":"","devName":"奥园NB测试43","combination":"奥体公园","rubbishHeight":"520","onlineStatus":"1","eventTime":"2019-11-07 11:26:53","remark":"","maintenanceMan":"","productModel":"WQIOT-SD100N-G0","number":null,"deviceId":"19ca3179-2600-4791-b239-3fd64bef371f","requestId":null,"msgId":"2","currentZTime":null,"garbageId":0,"groupId":null,"groupType":null,"pageNo":null,"pageSize":null,"combinationId":1,"serviceType":null,"cidList":null}]
     */

    private int pageNo;
    private int pages;
    private int total;
    private int pageSize;
    private List<ListBean> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : null
         * devId : WQDSN0AA000A0043
         * rubbishL : 380
         * rubbishR : 0
         * tmp : 1712
         * batV : 3651
         * angle : 3
         * signalQ : 20
         * reportType : 64
         * devStatus : 0
         * connectType : 电信NB
         * devSimNum :
         * latitude :
         * longitude :
         * devName : 奥园NB测试43
         * combination : 奥体公园
         * rubbishHeight : 520
         * onlineStatus : 1
         * eventTime : 2019-11-07 11:26:53
         * remark :
         * maintenanceMan :
         * productModel : WQIOT-SD100N-G0
         * number : null
         * deviceId : 19ca3179-2600-4791-b239-3fd64bef371f
         * requestId : null
         * msgId : 2
         * currentZTime : null
         * garbageId : 0
         * groupId : null
         * groupType : null
         * pageNo : null
         * pageSize : null
         * combinationId : 1
         * serviceType : null
         * cidList : null
         */

        private Object id;
        private String devId;
        private String rubbishL;
        private String rubbishR;
        private String tmp;
        private String batV;
        private String angle;
        private String signalQ;
        private String reportType;
        private String devStatus;
        private String connectType;
        private String devSimNum;
        private String latitude;
        private String longitude;
        private String devName;
        private String combination;
        private String rubbishHeight;
        private String onlineStatus;
        private String eventTime;
        private String remark;
        private String maintenanceMan;
        private String productModel;
        private Object number;
        private String deviceId;
        private Object requestId;
        private String msgId;
        private Object currentZTime;
        private int garbageId;
        private Object groupId;
        private Object groupType;
        private Object pageNo;
        private Object pageSize;
        private int combinationId;
        private Object serviceType;
        private Object cidList;
        private int position;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getDevId() {
            return devId;
        }

        public void setDevId(String devId) {
            this.devId = devId;
        }

        public String getRubbishL() {
            return rubbishL;
        }

        public void setRubbishL(String rubbishL) {
            this.rubbishL = rubbishL;
        }

        public String getRubbishR() {
            return rubbishR;
        }

        public void setRubbishR(String rubbishR) {
            this.rubbishR = rubbishR;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getBatV() {
            return batV;
        }

        public void setBatV(String batV) {
            this.batV = batV;
        }

        public String getAngle() {
            return angle;
        }

        public void setAngle(String angle) {
            this.angle = angle;
        }

        public String getSignalQ() {
            return signalQ;
        }

        public void setSignalQ(String signalQ) {
            this.signalQ = signalQ;
        }

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
        }

        public String getDevStatus() {
            return devStatus;
        }

        public void setDevStatus(String devStatus) {
            this.devStatus = devStatus;
        }

        public String getConnectType() {
            return connectType;
        }

        public void setConnectType(String connectType) {
            this.connectType = connectType;
        }

        public String getDevSimNum() {
            return devSimNum;
        }

        public void setDevSimNum(String devSimNum) {
            this.devSimNum = devSimNum;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getDevName() {
            return devName;
        }

        public void setDevName(String devName) {
            this.devName = devName;
        }

        public String getCombination() {
            return combination;
        }

        public void setCombination(String combination) {
            this.combination = combination;
        }

        public String getRubbishHeight() {
            return rubbishHeight;
        }

        public void setRubbishHeight(String rubbishHeight) {
            this.rubbishHeight = rubbishHeight;
        }

        public String getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(String onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public String getEventTime() {
            return eventTime;
        }

        public void setEventTime(String eventTime) {
            this.eventTime = eventTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getMaintenanceMan() {
            return maintenanceMan;
        }

        public void setMaintenanceMan(String maintenanceMan) {
            this.maintenanceMan = maintenanceMan;
        }

        public String getProductModel() {
            return productModel;
        }

        public void setProductModel(String productModel) {
            this.productModel = productModel;
        }

        public Object getNumber() {
            return number;
        }

        public void setNumber(Object number) {
            this.number = number;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public Object getRequestId() {
            return requestId;
        }

        public void setRequestId(Object requestId) {
            this.requestId = requestId;
        }

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public Object getCurrentZTime() {
            return currentZTime;
        }

        public void setCurrentZTime(Object currentZTime) {
            this.currentZTime = currentZTime;
        }

        public int getGarbageId() {
            return garbageId;
        }

        public void setGarbageId(int garbageId) {
            this.garbageId = garbageId;
        }

        public Object getGroupId() {
            return groupId;
        }

        public void setGroupId(Object groupId) {
            this.groupId = groupId;
        }

        public Object getGroupType() {
            return groupType;
        }

        public void setGroupType(Object groupType) {
            this.groupType = groupType;
        }

        public Object getPageNo() {
            return pageNo;
        }

        public void setPageNo(Object pageNo) {
            this.pageNo = pageNo;
        }

        public Object getPageSize() {
            return pageSize;
        }

        public void setPageSize(Object pageSize) {
            this.pageSize = pageSize;
        }

        public int getCombinationId() {
            return combinationId;
        }

        public void setCombinationId(int combinationId) {
            this.combinationId = combinationId;
        }

        public Object getServiceType() {
            return serviceType;
        }

        public void setServiceType(Object serviceType) {
            this.serviceType = serviceType;
        }

        public Object getCidList() {
            return cidList;
        }

        public void setCidList(Object cidList) {
            this.cidList = cidList;
        }
    }

}
