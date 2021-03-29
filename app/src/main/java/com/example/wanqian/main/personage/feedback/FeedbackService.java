package com.example.wanqian.main.personage.feedback;

import com.example.wanqian.main.personage.feedback.bean.FeedbackBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FeedbackService {
//   帮助反馈
//   http://112.126.100.73:9092/hyjj_back/uploadFeedBack
//    {
//        "description":"问题描述",
//         "pics":"上传图片"
//         "linkMethods":"联系方式"
//    }

    String url = "http://112.126.100.73:9092/hyjj_back/";

    // @Multipart
    @POST("uploadFeedBack")
    Observable<FeedbackBean> feedbackpost(@Body MultipartBody multipartBody);
}
