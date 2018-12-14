package me.mikasa.wandoujia.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.mikasa.wandoujia.listener.OnRvItemClickListener;


/**
 * Created by mikasacos on 2018/9/7.
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter {
    protected List<T>mDataList=new ArrayList<>();
    private OnRvItemClickListener mListener;
    //更新数据,refreshData
    public void updateData(List<T>dataList){
        mDataList.clear();//??
        appendData(dataList);
    }
    //分页加载，追加数据
    public void appendData(List<T>dataList){
        if (null!=dataList&&!dataList.isEmpty()){
            //addAll()追加
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }else if (dataList!=null&&dataList.isEmpty()){
            notifyDataSetChanged();//空更新？？
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    public void setOnRvItemClickListener(OnRvItemClickListener listener){
        this.mListener=listener;
    }
    //abstract viewHolder
    public abstract class BaseRvViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        public BaseRvViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);//itemView
            //itemView.setOnLongClickListener(this);//itemView
        }
        protected abstract void bindView(T t);//bindView

        @Override
        public void onClick(View v) {
            if (mListener!=null){
                mListener.onItemClick(getLayoutPosition());//getLayoutPosition()，mDataList从0开始
            }
        }
    }

}
