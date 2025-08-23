package com.settery.adappapr

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.settery.adappapr.databinding.ActivityMainBinding
import com.settery.adappapr.databinding.ItemAdBinding
import com.settery.adappapr.databinding.ItemArticleBinding
import com.settery.adappapr.domain.ListItem
import com.settery.adappapr.presentation.ArticleListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPagerWithTabs()
        setupWindowInsets()
    }

    private fun setupViewPagerWithTabs() {
        val pagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayoutMain, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_main)
                1 -> getString(R.string.tab_other)
                else -> ""
            }
        }.attach()
    }

    private fun setupWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()


class ContentAdapter(
    private val onArticleClick: (ListItem.ArticleItem) -> Unit,
    private val onAdClick: (ListItem.AdItem) -> Unit
) : ListAdapter<ListItem, RecyclerView.ViewHolder>(DiffCallback()) {

    // Два типа View: для статьи и для рекламы
    companion object {
        private const val VIEW_TYPE_ARTICLE = 1
        private const val VIEW_TYPE_AD = 2
    }

    // 1. Определяем тип элемента по его классу
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ListItem.ArticleItem -> VIEW_TYPE_ARTICLE
            is ListItem.AdItem -> VIEW_TYPE_AD
        }
    }

    // 2. Создаем нужный ViewHolder в зависимости от типа
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ARTICLE -> ArticleViewHolder(
                ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            VIEW_TYPE_AD -> AdViewHolder(
                ItemAdBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    // 3. Биндим данные в нужный ViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ArticleViewHolder -> {
                holder.bind(item as ListItem.ArticleItem)
                holder.itemView.setOnClickListener { onArticleClick(item) }
            }
            is AdViewHolder -> {
                holder.bind(item as ListItem.AdItem)
                holder.itemView.setOnClickListener { onAdClick(item) }
            }
        }
    }

    // ViewHolder для статьи (с ViewBinding)
    class ArticleViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ListItem.ArticleItem) {
            binding.tvHeader.text = article.title
            binding.tvDescription.text = article.description
            Glide.with(itemView.context)
                .load(article.imageUrl)
                // ...
                .into(binding.imgArticle)
        }
    }

    // ViewHolder для рекламы (с ViewBinding)
    class AdViewHolder(private val binding: ItemAdBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ad: ListItem.AdItem) {
            binding.tvHeader.text = ad.title
            binding.tvAdLabel.text = ad.label // "Реклама"
        }
    }

    // 4. Обновляем DiffUtil для работы с ListItem
    class DiffCallback : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }
}

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2  // Количество вкладок

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ArticleListFragment.Companion.newInstance("news")
            1 -> ArticleListFragment.Companion.newInstance("favorites")
            else -> throw IllegalArgumentException("Invalid tab index")
        }
    }
}


