package com.hlox.app.wan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hlox.app.wan.bean.HomeArticle;
import com.hlox.app.wan.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;


public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {
    private List<HomeArticle.Data.Article> data = new ArrayList<>();

    public ArticleAdapter() {

    }

    public List<HomeArticle.Data.Article> getData() {
        return data;
    }

    public void setData(List<HomeArticle.Data.Article> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<HomeArticle.Data.Article> data){
        int position = this.data.size();
        this.data.addAll(data);
        notifyItemRangeChanged(position,data.size());
    }


    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        ArticleHolder holder = new ArticleHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        HomeArticle.Data.Article article = data.get(position);
        holder.uploadTime.setText(DateTimeUtils.format(article.getPublishTime()));
        holder.author.setText(article.getAuthor());
        holder.title.setText(article.getTitle());
        holder.fav.setImageResource(R.drawable.ic_fav_unselected);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    protected class ArticleHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        TextView uploadTime;
        ImageView fav;

        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            author = itemView.findViewById(R.id.tv_author);
            uploadTime = itemView.findViewById(R.id.tv_upload_time);
            fav = itemView.findViewById(R.id.iv_fav);
        }
    }
}
