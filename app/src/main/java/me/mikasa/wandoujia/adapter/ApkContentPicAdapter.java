package me.mikasa.wandoujia.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.mikasa.wandoujia.R;
import me.mikasa.wandoujia.base.BaseRvAdapter;

public class ApkContentPicAdapter extends BaseRvAdapter<String> {
    private Context mContext;
    public ApkContentPicAdapter(Context context){
        this.mContext=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_apk_content_pic,parent,false);
        return new ApkContentPicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ApkContentPicHolder)holder).bindView(mDataList.get(position));
    }

    class ApkContentPicHolder extends BaseRvViewHolder{
        ImageView iv;
        ApkContentPicHolder(View itemView){
            super(itemView);
            iv=itemView.findViewById(R.id.iv_content_pic);
        }

        @Override
        protected void bindView(String s) {
            Glide.with(mContext).load(s)
                    .error(R.mipmap.ic_bili).placeholder(R.mipmap.ic_bili)
                    .crossFade(1600).into(iv);
        }
    }
}
