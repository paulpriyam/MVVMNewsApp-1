package com.androiddevs.mvvmnewsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.model.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter : PagingDataAdapter<Article, NewsAdapter.NewsViewHolder>(differCallback) {

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
    }


    val differ = AsyncListDiffer(this, differCallback)

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article) {
            Glide.with(itemView).load(article.urlToImage).into(itemView.ivArticleImage)
            itemView.tvTitle.text = article.title
            itemView.tvDescription.text = article.description
            itemView.tvSource.text = article.source?.name
            itemView.tvPublishedAt.text = article.publishedAt
            itemView.rootView.setOnClickListener {
                onItemClickListener?.invoke(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        return NewsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article_preview, parent, false)
        )
    }

//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article =getItem(position)
        if (article != null) {
            holder.bind(article)
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Article) -> Unit)) {
        onItemClickListener = listener
    }
}