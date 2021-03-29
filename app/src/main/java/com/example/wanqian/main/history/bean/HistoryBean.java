package com.example.wanqian.main.history.bean;

import java.util.List;

public class HistoryBean {


    /**
     * code : 200
     * msg : success
     * data : {"pageNo":1,"pages":0,"total":215,"pageSize":20,"list":[{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/10 14:33:07","endTime":"2019/12/10 14:57:37","cri":249,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/10 16:27:01","endTime":"2019/12/10 16:36:36","cri":261,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/10 19:39:43","endTime":"2019/12/11 10:45:23","cri":258,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/11 10:26:21","endTime":"2019/12/11 10:45:28","cri":255,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/11 10:45:25","endTime":"2019/12/11 10:45:45","cri":252,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/10 14:58:11","endTime":"2019/12/11 11:12:24","cri":246,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/11 10:45:35","endTime":"2019/12/12 09:50:20","cri":243,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/11 10:45:50","endTime":"2019/12/12 10:54:56","cri":240,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/11 11:12:26","endTime":"2019/12/12 11:58:02","cri":237,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/12 09:49:03","endTime":"2019/12/12 12:37:35","cri":234,"status":2},{"carName":"fan测试车辆","clearCount":3,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:03:36","endTime":"2019/12/17 10:03:52","cri":334,"status":2},{"carName":"fan测试车辆","clearCount":4,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:04:05","endTime":"2019/12/17 10:09:19","cri":330,"status":2},{"carName":"fan测试车辆","clearCount":15,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:16:48","endTime":"2019/12/17 10:18:12","cri":327,"status":2},{"carName":"fan测试车辆","clearCount":2,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:18:23","endTime":"2019/12/17 10:19:22","cri":324,"status":2},{"carName":"fan测试车辆","clearCount":1,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:19:25","endTime":"2019/12/17 10:39:41","cri":321,"status":2},{"carName":"fan测试车辆","clearCount":5,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:39:42","endTime":"2019/12/17 10:39:47","cri":318,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:39:48","endTime":"2019/12/17 10:39:49","cri":315,"status":2},{"carName":"fan测试车辆","clearCount":6,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:39:59","endTime":"2019/12/17 10:40:00","cri":312,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:40:02","endTime":"2019/12/17 10:40:04","cri":309,"status":2},{"carName":"fan测试车辆","clearCount":6,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:40:15","endTime":"2019/12/17 10:40:16","cri":306,"status":2}]}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * pageNo : 1
         * pages : 0
         * total : 215
         * pageSize : 20
         * list : [{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/10 14:33:07","endTime":"2019/12/10 14:57:37","cri":249,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/10 16:27:01","endTime":"2019/12/10 16:36:36","cri":261,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/10 19:39:43","endTime":"2019/12/11 10:45:23","cri":258,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/11 10:26:21","endTime":"2019/12/11 10:45:28","cri":255,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/11 10:45:25","endTime":"2019/12/11 10:45:45","cri":252,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/10 14:58:11","endTime":"2019/12/11 11:12:24","cri":246,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/11 10:45:35","endTime":"2019/12/12 09:50:20","cri":243,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/11 10:45:50","endTime":"2019/12/12 10:54:56","cri":240,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/11 11:12:26","endTime":"2019/12/12 11:58:02","cri":237,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/12 09:49:03","endTime":"2019/12/12 12:37:35","cri":234,"status":2},{"carName":"fan测试车辆","clearCount":3,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:03:36","endTime":"2019/12/17 10:03:52","cri":334,"status":2},{"carName":"fan测试车辆","clearCount":4,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:04:05","endTime":"2019/12/17 10:09:19","cri":330,"status":2},{"carName":"fan测试车辆","clearCount":15,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:16:48","endTime":"2019/12/17 10:18:12","cri":327,"status":2},{"carName":"fan测试车辆","clearCount":2,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:18:23","endTime":"2019/12/17 10:19:22","cri":324,"status":2},{"carName":"fan测试车辆","clearCount":1,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:19:25","endTime":"2019/12/17 10:39:41","cri":321,"status":2},{"carName":"fan测试车辆","clearCount":5,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:39:42","endTime":"2019/12/17 10:39:47","cri":318,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:39:48","endTime":"2019/12/17 10:39:49","cri":315,"status":2},{"carName":"fan测试车辆","clearCount":6,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:39:59","endTime":"2019/12/17 10:40:00","cri":312,"status":2},{"carName":"fan测试车辆","clearCount":0,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:40:02","endTime":"2019/12/17 10:40:04","cri":309,"status":2},{"carName":"fan测试车辆","clearCount":6,"name":"平板3732","carNum":"京CAA123","startTime":"2019/12/17 10:40:15","endTime":"2019/12/17 10:40:16","cri":306,"status":2}]
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
             * carName : fan测试车辆
             * clearCount : 0
             * name : 平板3732
             * carNum : 京CAA123
             * startTime : 2019/12/10 14:33:07
             * endTime : 2019/12/10 14:57:37
             * cri : 249
             * status : 2
             */

            private String carName;
            private int clearCount;
            private String name;
            private String carNum;
            private String startTime;
            private String endTime;
            private int cri;
            private int status;

            public String getCarName() {
                return carName;
            }

            public void setCarName(String carName) {
                this.carName = carName;
            }

            public int getClearCount() {
                return clearCount;
            }

            public void setClearCount(int clearCount) {
                this.clearCount = clearCount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCarNum() {
                return carNum;
            }

            public void setCarNum(String carNum) {
                this.carNum = carNum;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getCri() {
                return cri;
            }

            public void setCri(int cri) {
                this.cri = cri;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}