package com.example.kwongiho.retrofitproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kwongiho.retrofitproject.model.dto.Contributor;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by adminstrator on 2016-03-11.
 */
public class RepoItemsAdapter extends RecyclerView.Adapter<RepoItemsAdapter.ViewHolder>{
    private Context context;
    private List<Contributor> list;

    public RepoItemsAdapter(Context context ) {
        this.context = context;
        this.list = new ArrayList<Contributor>();
    }
    public void add(Contributor contributor) {
        Log.i("add",list.size()+"");
        list.add(contributor);
        this.notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(ViewHolder holder , int position) {
        Log.i("onBindViewHolder",position+"");
        holder.id.setText(list.get(position).getId());
        holder.login.setText(list.get(position).getLogin());
    }

    @Override
    public int getItemCount() {
        Log.i("getItemCount",list.size()+"");
        return this.list.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("onCreateViewHolder",viewType+"");
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_view_items,null));

    }
    /*LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_view_items,null);
        return new ViewHolder(view);*/

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.login)
        TextView login;
        @Bind(R.id.id)
        TextView id;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }


}
/* @Override
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
        ViewHolder viewHolder;
        if(convertView==null) {
            viewHolder = new ViewHolder();
            viewHolder.login =(TextView) view.findViewById(R.id.login);
            viewHolder.id= (TextView) view.findViewById(R.id.id);
            convertView = layoutInflater.inflate(R.layout.list_view_items,parent,false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder =(ViewHolder) convertView.getTag();
        }
        //이 부분에서 계속 빡 나는데..왜 그런건지 잘 못잡겠네요ㅠ;
        Log.d("errorLine",position+"");
        try {
            viewHolder.login.setText(contributorList.get(position).getLogin());
            viewHolder.id.setText(Integer.toString(contributorList.get(position).getId()));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("errrrrr",ex.getMessage());
        }
        return view;
    }
    static class ViewHolder {
        @Bind(R.id.login)
        TextView login;
        @Bind(R.id.id)
        TextView id;

        public ViewHolder() {
        }

        public ViewHolder(TextView login, TextView id) {
            ButterKnife.bind(MainActivity.this);
            this.login = login;
            this.id = id;
        }
    }*/
