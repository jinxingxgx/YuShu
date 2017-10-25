package com.xgx.yushu.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.EmptyUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaeger.ninegridimageview.ItemImageClickListener;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.enitity.ThumbViewInfo;
import com.xgx.yushu.R;
import com.xgx.yushu.bean.TransferInfo;
import com.xgx.yushu.utils.MyUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xgx on 2017/9/8 0008 for YuShu
 */

public class TransferListAdapter extends BaseQuickAdapter<TransferInfo, BaseViewHolder> {
    public TransferListAdapter(List<TransferInfo> data) {
        super(R.layout.adapter_transfer_item, data);
    }


    private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String s) {
            Glide.with(context).load(s).placeholder(R.drawable.bg_first).into(imageView);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }

        @Override
        protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
            //  Toast.makeText(context, "image position is " + index, Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    protected void convert(BaseViewHolder helper, TransferInfo item) {
        helper.addOnClickListener(R.id.titleLayout);
        Glide.with(mContext).load(MyUrl.baseIp + item.getUser_headpic()).error(R.mipmap.head_me).into((ImageView) helper.getView(R.id.avatarTv));
        helper.setText(R.id.nameTv, item.getUser_name());
        helper.setText(R.id.type, "1".equals(item.getTransfer_type()) ? "馈赠" : "置换");
        helper.setText(R.id.contentTv, item.getTransfer_title());
        helper.setText(R.id.transfer_commentTv, item.getTransfer_comment_count());
        helper.setText(R.id.transfer_dianzanTv, item.getTransfer_praise_count());
        final NineGridImageView<String> mNglContent = helper.getView(R.id.ngl_images);
        final ArrayList<ThumbViewInfo> mThumbViewInfoList = new ArrayList<>();
        mNglContent.setAdapter(mAdapter);
        mNglContent.setItemImageClickListener(new ItemImageClickListener<String>() {
            @Override
            public void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
                Log.d("onItemImageClick", list.get(index));
                computeBoundsBackward(list, mNglContent, mThumbViewInfoList);//组成数据
                GPreviewBuilder.from((Activity) mContext)
                        .setData(mThumbViewInfoList)
                        .setCurrentIndex(index)
                        .setType(GPreviewBuilder.IndicatorType.Dot)
                        .start();//启动
            }
        });
        if (EmptyUtils.isNotEmpty(item.getImages()) && item.getImages().size() > 0) {
            List<String> photos = new ArrayList<>();
            for (int i = 0; i < item.getImages().size(); i++) {
                photos.add(item.getImages().get(i).getUploadimage_path_small());
            }
            mNglContent.setImagesData(photos);
            mNglContent.setVisibility(View.VISIBLE);

        } else {
            mNglContent.setVisibility(View.GONE);
        }
    }


    /**
     * 查找信息
     *
     * @param list               图片集合
     * @param mNglContent
     * @param mThumbViewInfoList
     */
    private void computeBoundsBackward(List<String> list, NineGridImageView<String> mNglContent, ArrayList<ThumbViewInfo> mThumbViewInfoList) {
        ThumbViewInfo item;
        mThumbViewInfoList.clear();
        for (int i = 0; i < mNglContent.getChildCount(); i++) {
            View itemView = mNglContent.getChildAt(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = (ImageView) itemView;
                thumbView.getGlobalVisibleRect(bounds);
            }
            item = new ThumbViewInfo(list.get(i));
            item.setBounds(bounds);
            mThumbViewInfoList.add(item);
        }

    }

}
