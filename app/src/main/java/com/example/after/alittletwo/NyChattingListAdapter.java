package com.example.after.alittletwo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.after.alittletwo.entity.Constant;
import com.example.after.alittletwo.views.MyImageView;

import java.util.List;


public class NyChattingListAdapter extends BaseAdapter {

    private final int VIEW_TYPE_COUNT = 8;
    private final int VIEW_TYPE_LEFT_TEXT = 0;
    private final int VIEW_TYPE_LEFT_IMAGE = 1;
    private final int VIEW_TYPE_RIGTH_TEXT = 2;
    private final int VIEW_TYPE_RIGTH_IMAGE = 3;

    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<Data_ReceiverNews.NewsBean> mData;
    private String myuserid;
    public int isMesend;
    public int iconId;
    private Bitmap bitmap;

    public NyChattingListAdapter(Activity activity, Bitmap bitmap, String id, int iconId, List<Data_ReceiverNews.NewsBean> list) {
        this.mActivity = activity;
        this.myuserid = id;
        this.mData = list;
        this.iconId = iconId;
        this.bitmap = bitmap;
        mInflater = LayoutInflater.from(activity);
    }

    public void setsendtype(int i) {
        isMesend = i;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) == null) {
            return -1;
        }
        if (mData.get(position).getUserid() != null && mData.get(position).getUserid().equals(myuserid) || isMesend == 1) {
            return VIEW_TYPE_RIGTH_TEXT;
        } else {
            return VIEW_TYPE_LEFT_TEXT;
        }
//        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Data_ReceiverNews.NewsBean bean = mData.get(position);
        int type = getItemViewType(position);
        ViewHolderText rightholder;
        if (convertView == null) {
            if (type == VIEW_TYPE_RIGTH_TEXT) {
                convertView = mInflater.inflate(R.layout.listitem_cha_right_text, null);//接收的消息
            } else if (type == VIEW_TYPE_LEFT_TEXT) {
                convertView = mInflater.inflate(R.layout.listitem_cha_left_text, null);//发送的消息
            }
            rightholder = new ViewHolderText();

            rightholder.iv_avatar = convertView.findViewById(R.id.iv_avatar);
            rightholder.tv_content = convertView.findViewById(R.id.tv_content);
            rightholder.sendtime = convertView.findViewById(R.id.sendtime);
            rightholder.name = convertView.findViewById(R.id.chatname);

            convertView.setTag(rightholder);
        } else {
            rightholder = (ViewHolderText) convertView.getTag();
        }

        if (type == VIEW_TYPE_RIGTH_TEXT) {
            String time = bean.getCreatime();
            String tim = time.substring(0, time.length() - 2);
            rightholder.sendtime.setText(tim);//设置头像和姓名注释了
            rightholder.tv_content.setText(bean.getNewscontent());
            rightholder.iv_avatar.setImageBitmap(bitmap);
            rightholder.name.setText("");
//            if (SharedPreferencesUtil.readAvatar(mActivity)!=null){
//                Glide.with(mActivity).load(SharedPreferencesUtil.readAvatar(mActivity)).into(rightholder.iv_avatar);
//            }
        } else if (type == VIEW_TYPE_LEFT_TEXT) {
            String time = bean.getCreatime();
            String tim = time.substring(0, time.length() - 2);
            rightholder.sendtime.setText(tim);//设置头像和姓名注释了
            rightholder.tv_content.setText(bean.getNewscontent());
            rightholder.iv_avatar.setImageResource(iconId);

            rightholder.name.setText("");
//            if (bean.getPath()!=null){
//                Glide.with(mActivity).load(bean.getPath()).into(rightholder.iv_avatar);
//            }
        }


        return convertView;
    }


    public final class ViewHolderText {
        public MyImageView iv_avatar;
        public TextView tv_content;
        public TextView sendtime;
        public TextView name;
    }


}