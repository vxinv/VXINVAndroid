package com.example.wanqian.main.terminal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanqian.R;
import com.example.wanqian.bean.AllMessageDeviceBean;
import com.example.wanqian.main.terminal.adapter.Rlv_TerminalAdapter;
import com.example.wanqian.main.terminal.bean.TerminalBean;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.ui.locationMessage.LocationDialogFragment;
import com.example.wanqian.utitls.Myapi;
import com.example.wanqian.utitls.SPUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
 * 终端监控
 */
public class Terminal_Fragment extends Fragment {

    private static final String TAG = "Terminal_Fragment";
    private RecyclerView terminal_recycle;
    private ArrayList<TerminalBean.DataBean.ListBean> list;
    private Rlv_TerminalAdapter adapter;
    private int page = 1;
    private SmartRefreshLayout refreshLayout;
    private ImageView mRefreshShang, mRefreshXie, mRefreshXia;
    private TextView mPageNum, mPageNumAll;
    private TerminalBean.DataBean beans;
    private int mPageMxa;
    private LinearLayoutManager linearLayout;
    private RelativeLayout yuan;

    public Terminal_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terminal_, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        terminal_recycle = (RecyclerView) view.findViewById(R.id.terminal_recycle);
        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefreshShang = (ImageView) view.findViewById(R.id.refresh_shang);
        yuan = (RelativeLayout) view.findViewById(R.id.yuan);
        mRefreshXie = (ImageView) view.findViewById(R.id.refresh_xie);
        mRefreshXia = (ImageView) view.findViewById(R.id.refresh_xia);
        mPageNum = (TextView) view.findViewById(R.id.page_num);
        mPageNumAll = (TextView) view.findViewById(R.id.page_numAll);

        list = new ArrayList<>();

        adapter = new Rlv_TerminalAdapter(getContext(), list);
        adapter.setOnchangeDataClickListener(new Rlv_TerminalAdapter.OnchangeDataClickListener() {
            @Override
            public void onchangeDataClick(TerminalBean.DataBean.ListBean data) {
                if (data!=null){
                    List<String> de=new ArrayList<>();
                    de.add(data.getDevId());
                    initDeviceData(de);
//                    DeviceDetailPopWindow popWindow=new DeviceDetailPopWindow(getContext(),data);
//                    popWindow.showAtLocation(view, Gravity.TOP,0, ScreenUtils.dp2px(getContext(),115));
                }
            }
        });
        terminal_recycle.setAdapter(adapter);
        linearLayout = new LinearLayoutManager(getContext());
        terminal_recycle.setLayoutManager(linearLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                list.clear();
                initData();
                refreshLayout.finishRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPageMxa = beans.getTotal() / 20;
                if (beans.getTotal() % 20 == 0) {
                    mPageMxa = beans.getTotal() / 20 + 1;
                }
                if (page <= mPageMxa) {
                    page++;
                    initData();
                    adapter.notifyDataSetChanged();
                } else {
                     Toast.makeText(getActivity(), "已经是最后一页啦！", Toast.LENGTH_SHORT).show();
                }
                refreshLayout.finishLoadMore();
            }
        });
        mRefreshXia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (beans!=null){
                    mPageMxa = beans.getTotal() / 20;
                    if (beans.getTotal() % 20 == 0) {
                        mPageMxa = beans.getTotal() / 20 + 1;
                    }
                    if (page <= mPageMxa) {
                        refreshLayout.autoLoadMore();
                    }else {
                        Toast.makeText(getActivity(), "已经是最后一页啦！", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        yuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToPosition(linearLayout, terminal_recycle, 0);
                mPageNum.setText(1 + "");
            }
        });
        mRefreshShang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page > 1) {
                    page--;
                    int i = (page - 1) * 20;
                    if (i != 0)
                        MoveToPosition(linearLayout, terminal_recycle, i - 1);
                    else
                        MoveToPosition(linearLayout, terminal_recycle, i);
                    mPageNum.setText(page + "");
                } else {
                    Toast.makeText(getActivity(), "已经是第一页啦！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        terminal_recycle.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if (firstItemPosition >= 19) {
                        int position = (firstItemPosition + 1) / 20;
                        Log.i("aaaaa", firstItemPosition + "===" + position);
                        if (firstItemPosition % 20 == 0) {
                            mPageNum.setText(position + "");
                        } else {
                            mPageNum.setText((position + 1) + "");
                        }
                    } else {
                        mPageNum.setText(1 + "");
                    }
                }
            }
        });
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager       设置RecyclerView对应的manager
     * @param mRecyclerView 当前的RecyclerView
     * @param n             要跳转的位置
     */
    public static void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }

    }

    private void initData() {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(Myapi.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        TerminalService service = build.create(TerminalService.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("id", SPUtils.getInt("id") + "");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo", page);
            jsonObject.put("pageSize", 20);
            //jsonObject.put("pageSize", beans);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Observable<TerminalBean> observable = service.postFiled(requestBody, map);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<TerminalBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(TerminalBean terminalBean) {
                        beans = terminalBean.getData();
//                        if (terminalBean.getData().getTotal() % 20 == 0)
//                            mPageNumAll.setText(terminalBean.getData().getTotal() / 20 + "");
//                        else
//                            mPageNumAll.setText((terminalBean.getData().getTotal() / 20 + 1) + "");

                        if (beans!=null&&beans.getList()!=null&&beans.getList().size()>0){
                            list.addAll(beans.getList());
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void initDeviceData(List<String> devIds) {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(Myapi.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        TerminalService service = build.create(TerminalService.class);
        String jsonString = new Gson().toJson(devIds);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);

        Observable<BaseModel<List<AllMessageDeviceBean>>> observable = service.getNewInfo(requestBody);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<BaseModel<List<AllMessageDeviceBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(BaseModel<List<AllMessageDeviceBean>> terminalBean) {
                        if (terminalBean!=null){
                            LocationDialogFragment.newInstance(terminalBean.getData()).setOutCancel(true).show(getChildFragmentManager());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

}
