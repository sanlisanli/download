package me.mikasa.wandoujia.mvp.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.List;

import me.mikasa.wandoujia.R;
import me.mikasa.wandoujia.adapter.ApkItemAdapter;
import me.mikasa.wandoujia.base.BaseToolbarActivity;
import me.mikasa.wandoujia.bean.ApkItem;
import me.mikasa.wandoujia.listener.OnRvItemClickListener;
import me.mikasa.wandoujia.listener.PermissionListener;
import me.mikasa.wandoujia.mvp.contract.GetApkItemContract;
import me.mikasa.wandoujia.mvp.presenter.GetApkItemPresenterImpl;

public class MainActivity extends BaseToolbarActivity implements GetApkItemContract.View,
        PermissionListener ,OnRvItemClickListener {
    private ApkItemAdapter mAdapter;
    private Context mContext;
    private GetApkItemPresenterImpl mPresenter;
    private static List<ApkItem>itemList=null;
    private Toolbar mToolbar;
    @Override
    protected int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {
        mToolbar=findViewById(R.id.toolbar_include);
        TextView mTitle=findViewById(R.id.toolbar_tv);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        mTitle.setText("手机应用市场");
    }

    @Override
    protected void initAll() {
        String[] permissions={Manifest.permission.READ_CALENDAR};
        requestRuntimePermission(permissions,this);
    }

    @Override
    protected void initData() {
        mContext=this;
        mPresenter=new GetApkItemPresenterImpl(this);
        mAdapter=new ApkItemAdapter(mContext);
    }

    @Override
    protected void initView() {
        RecyclerView mRecyclerView=findViewById(R.id.rv_apk_item);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getItem(mContext);//开始请求网络数据
    }

    @Override
    protected void initListener() {
        mAdapter.setOnRvItemClickListener(this);
    }

    @Override
    public void onGranted() {
        init();
    }
    private void init(){
        initData();
        initView();
        initListener();
    }
    @Override
    public void onDenied(List<String> deniedPermission) {
    }

    @Override
    public void getItemSuccess(List<ApkItem> list) {
        mAdapter.updateData(list);//请求网络数据成功,recyclerView加载数据
        itemList=list;
    }

    @Override
    public void getItemError(String msg) {
        showToast(msg);
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent=new Intent(MainActivity.this,ApkDetailActivity.class);
        intent.putExtra("contentlink",itemList.get(pos).getContentLink());
        startActivity(intent);
    }
}
