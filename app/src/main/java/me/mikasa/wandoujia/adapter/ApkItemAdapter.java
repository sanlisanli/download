package me.mikasa.wandoujia.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.mikasa.wandoujia.R;
import me.mikasa.wandoujia.base.BaseRvAdapter;
import me.mikasa.wandoujia.bean.ApkItem;

public class ApkItemAdapter extends BaseRvAdapter<ApkItem> {
    private Context mContext;
    public ApkItemAdapter(Context context){
        this.mContext=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_apk,parent,false);
        return new ApkItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ApkItemHolder)holder).bindView(mDataList.get(position));
    }

    class ApkItemHolder extends BaseRvViewHolder{
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_size;
        TextView tv_times;
        ApkItemHolder(View itemView){
            super(itemView);
            iv_icon=itemView.findViewById(R.id.iv_icon);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_size=itemView.findViewById(R.id.tv_size);
            tv_times=itemView.findViewById(R.id.tv_download_times);
        }

        @Override
        protected void bindView(ApkItem item) {
            Glide.with(mContext).load(item.getImgUrl()).asBitmap()
                    .error(R.mipmap.ic_bili).placeholder(R.mipmap.ic_bili).into(iv_icon);
            tv_title.setText(item.getTitle());
            tv_size.setText(item.getSize());
            tv_times.setText(item.getDownloadTimes());
        }
    }
}
