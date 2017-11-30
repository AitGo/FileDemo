package com.xtool.filedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtool.filedemo.entity.Dir;

import java.util.List;

/**
 * Created by xtool on 2017/11/29.
 */

public class DirListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private Bitmap mIcon1;
    private Bitmap mIcon3;
    private List<Dir> dirs;

    public DirListAdapter(Context mContext, List<Dir> dirs) {
        this.mContext = mContext;
        this.dirs = dirs;
        mInflater = LayoutInflater.from(mContext);
        mIcon1 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.bg_folder_blue);
        mIcon3 = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.unknown);
    }

    public String getDirName(int position) {
        return dirs.get(position).getDirName();
    }

    public List<Dir> getChildrenList(int position) {
        return dirs.get(position).getChildren();
    }

    public void setDirs(List<Dir> dirs) {
        this.dirs = dirs;
    }

    public List<Dir> getDirs () {
        return dirs;
    }

    @Override
    public int getCount() {
        return dirs.size();
    }

    @Override
    public Object getItem(int position) {
        return dirs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_dir_list, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.tv_file_detail_item_name);
            holder.icon = (ImageView) convertView.findViewById(R.id.iv_fold_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.icon.setImageBitmap(mIcon1);
        holder.text.setText(dirs.get(position).getDirName());
        return convertView;
    }

    private class ViewHolder {
        TextView text;
        ImageView icon;
    }
}
