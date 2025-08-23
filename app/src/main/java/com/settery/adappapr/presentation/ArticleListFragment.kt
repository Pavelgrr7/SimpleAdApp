package com.settery.adappapr.presentation

import SpacingDecorator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.settery.adappapr.ContentAdapter
import com.settery.adappapr.databinding.FragmentArticleListBinding
import com.settery.adappapr.domain.ListItem
import com.settery.adappapr.dpToPx

class ArticleListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContentAdapter
    private lateinit var binding: FragmentArticleListBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentArticleListBinding.inflate(inflater)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = ContentAdapter(
            onArticleClick = { },
            onAdClick = { },
        )
        val divider = SpacingDecorator(
            dividerHeight = 1.dpToPx(),    // высота линии
            marginTop = 8.dpToPx(),        // отступ сверху до линии
            marginBottom = 8.dpToPx(),     // отступ снизу после линии
            paddingStart = 16.dpToPx(),    // отступ слева
            paddingEnd = 16.dpToPx(),      // отступ справа
            color = Color.parseColor("#E0E0E0")
        )
        recyclerView.addItemDecoration(divider)
        recyclerView.adapter = adapter

        val category = arguments?.getString("category") ?: "news"

        val articles = listOf(
            ListItem.ArticleItem("1" ,"Hello1", "world", "https://random.imagecdn.app/600/600"),
            ListItem.ArticleItem("2" ,"Good", "Morning", "https://random.imagecdn.app/600/600"),
            ListItem.ArticleItem("3", "Article3", "desc", "https://random.imagecdn.app/600/600"),
        )

        adapter.submitList(articles)

        return binding.root
    }

    companion object {
        fun newInstance(category: String) = ArticleListFragment().apply {
            arguments = bundleOf("category" to category)
        }
    }
}
