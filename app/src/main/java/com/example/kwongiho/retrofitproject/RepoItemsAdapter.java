package com.example.kwongiho.retrofitproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by adminstrator on 2016-03-14.
 */
public class RepoItemsAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Contributor> contributorList;
    public RepoItemsAdapter(Context context, List<Contributor> contributorList) {
        this.context = context;
        this.contributorList=contributorList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return contributorList.size();
    }

    @Override
    public Object getItem(int position) {
        return contributorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.list_view_items,null);
        ViewHolder viewHolder=null;
        if(convertView==null) {
            viewHolder = new ViewHolder();
            viewHolder.login =(TextView) view.findViewById(R.id.login);
            viewHolder.id= (TextView) view.findViewById(R.id.id);
            convertView = layoutInflater.inflate(R.layout.list_view_items,parent,false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder =(ViewHolder) convertView.getTag();
        }
        viewHolder.login.setText(contributorList.get(position).getLogin());
        viewHolder.id.setText( contributorList.get(position).getId());
        return view;
    }
    private class ViewHolder {
        TextView login;
        TextView id;

        public ViewHolder() {
        }

        public ViewHolder(TextView login, TextView id) {
            this.login = login;
            this.id = id;
        }
    }
}
