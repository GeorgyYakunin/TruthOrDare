package com.example.truthordare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.List;

public class SimplestAdapter<T, C extends SimplestAdapter.ViewHolder<T>> extends BaseAdapter {
    /* renamed from: c */
    private Class<C> f21c;
    private Context context;
    private List<T> data;
    private ViewHolder holder;
    private LayoutInflater inflater;
    private int resource;

    public static abstract class ViewHolder<T> {
        public SimplestAdapter adapter;
        public View view;

        public abstract void setData(Context context, T t);

        public ViewHolder(View view) {
            this.view = view;
        }

        public void setAdapter(SimplestAdapter adapter) {
            this.adapter = adapter;
        }

        public SimplestAdapter getAdapter() {
            return this.adapter;
        }
    }

    public SimplestAdapter(Context context, Class<C> cls, int resource, List<T> data) {
        this.context = context;
        this.resource = resource;
        if (data == null) {
            data = new ArrayList();
        }
        this.data = data;
        this.f21c = cls;
    }

    public int getCount() {
        return this.data.size();
    }

    public Object getItem(int position) {
        return this.data.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.inflater == null) {
            this.inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        }
        if (convertView == null) {
            try {
                convertView = this.inflater.inflate(this.resource, parent, false);
                this.holder = (ViewHolder) this.f21c.getDeclaredConstructor(new Class[]{View.class}).newInstance(new Object[]{convertView});
            } catch (Exception e) {
                e.printStackTrace();
            }
            convertView.setTag(this.holder);
        } else {
            this.holder = (ViewHolder) convertView.getTag();
        }
        this.holder.setData(this.context, getItem(position));
        this.holder.setAdapter(this);
        return convertView;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
