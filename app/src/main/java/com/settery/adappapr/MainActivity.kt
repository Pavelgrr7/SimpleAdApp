package com.settery.adappapr

import SpacingDecorator
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.rv_main)
        val adapter = ArticleAdapter()
        val divider = SpacingDecorator(
            dividerHeight = 1.dpToPx(),    // высота линии
            marginTop = 8.dpToPx(),        // отступ сверху до линии
            marginBottom = 8.dpToPx(),     // отступ снизу после линии
            paddingStart = 16.dpToPx(),    // отступ слева
            paddingEnd = 16.dpToPx(),      // отступ справа
            color = Color.parseColor("#E0E0E0")
        )

        recyclerView.addItemDecoration(divider)
//        val divider = DividerItemDecoration(this, LinearLayoutManager.VERTICAL) // или HORIZONTAL
//        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider)!!)
//        recyclerView.addItemDecoration(divider)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val articles = listOf(
            Article( "Hello1", "world", "https://random.imagecdn.app/600/600", 1),
            Article( "Good", "Morning", "https://random.imagecdn.app/600/600", 2),
            Article( "Article3", "desc", "https://random.imagecdn.app/600/600", 3),
        )
        adapter.submitList(articles)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

class ArticleAdapter : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback()) {

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headerText: TextView = itemView.findViewById(R.id.tv_header)
        private val image: ImageView = itemView.findViewById(R.id.img_article)
        private val descriptionText: TextView = itemView.findViewById(R.id.tv_description)
        private val context: Context = itemView.context

        fun bind(article: Article) {
            headerText.text = article.title
            descriptionText.text = article.description
            Glide.with(context)
                .load(article.urlToImage)
                .placeholder(R.drawable.img_article_placeholder)
                .error(R.drawable.error_image)
                .override(400, 400)
                .into(image)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }
}
