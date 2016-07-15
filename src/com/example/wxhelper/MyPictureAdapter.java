package com.example.wxhelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * TODO 这里描述下用途吧
 * Created by lingxiaoming on 2016/7/15 0015.
 */
public class MyPictureAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context mContext;
    private List<String> mDatas;
    private int screenWidth;
    private int screenHeight;

    public MyPictureAdapter(Context context, List<String> pathLists) {
        this.mContext = context;
        this.mDatas = pathLists;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String path = mDatas.get(position);
        Bitmap bitmap = ImageUtils.getBitmap(path, screenWidth / 3, -1);
//        Bitmap bitmap = ImageUtils.getBitmap(path, -1, screenHeight/3);
        holder.imageView.setImageBitmap(bitmap);
        holder.picname.setText(path);

//        ViewGroup.LayoutParams lp = holder.imageView.getLayoutParams();
//        lp.height = 150;
//        holder.imageView.setLayoutParams(lp);

        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public String getItem(int position){
        return mDatas.get(position);
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView picname;
    ImageView imageView;

    public MyViewHolder(View view) {
        super(view);
        picname = (TextView) view.findViewById(R.id.tv_imagename);
        imageView = (ImageView) view.findViewById(R.id.iv_image);
    }
}
