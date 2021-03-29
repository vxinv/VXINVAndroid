package com.example.wanqian.main.history;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.wanqian.R;
import com.example.wanqian.main.history.adapter.Rlv_HistoryAdapter;
import com.example.wanqian.main.history.bean.HistoryBean;
import com.example.wanqian.main.terminal.TerminalService;
import com.example.wanqian.main.terminal.bean.TerminalBean;
import com.example.wanqian.utitls.Myapi;
import com.example.wanqian.utitls.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 *   历史作业
 */
public class History_Fragment extends Fragment {

    private RecyclerView history_recycle;

    public History_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initView(View view) {
        history_recycle = (RecyclerView) view.findViewById(R.id.history_recycle);
    }

    private void initData() {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(Myapi.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        TerminalService service = build.create(TerminalService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("userName",SPUtils.getValue("yhm"));
        map.put("pageNo","1");
        map.put("pageSize","20");
        Observable<HistoryBean> historyWork = service.getHistoryWork(map);
        historyWork.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<HistoryBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HistoryBean historyBean) {
                        List<HistoryBean.DataBean.ListBean> list = historyBean.getData().getList();
                        Rlv_HistoryAdapter adapter = new Rlv_HistoryAdapter(getActivity(), list);
                        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
                        history_recycle.setLayoutManager(linearLayout);
                        history_recycle.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
