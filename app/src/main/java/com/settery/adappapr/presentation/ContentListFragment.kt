package com.settery.adappapr.presentation

import SpacingDecorator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.settery.adappapr.ContentAdapter
import com.settery.adappapr.databinding.FragmentArticleListBinding
import com.settery.adappapr.dpToPx
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ContentListFragment : Fragment() {

    private lateinit var binding: FragmentArticleListBinding
    private lateinit var adapter: ContentAdapter

    // Получаем ViewModel родительского фрагмента (MainFragment)
    private val parentViewModel: MainViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val category = arguments?.getString(ARG_CATEGORY) ?: ""

        // Подписываемся на state родительской ViewModel и фильтруем данные для себя
        viewLifecycleOwner.lifecycleScope.launch {
            parentViewModel.container.stateFlow
                .map { state ->
                    // Фильтруем список, чтобы показать только нужные для этой вкладки элементы
                    if (state.currentTab
//                        .apiName
                        == category) state.items else emptyList()
                }
                .distinctUntilChanged() // Не обновлять, если список не изменился
                .collect { items ->
                    adapter.submitList(items)
                }
        }
    }

    private fun setupRecyclerView() {
        adapter = ContentAdapter(
            onArticleClick = {
//                parentViewModel.onArticleClicked(it)
                             }, // Делегируем клики
            onAdClick = {
//                parentViewModel.onAdClicked(it)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val divider = SpacingDecorator(
            dividerHeight = 1.dpToPx(),    // высота линии
            marginTop = 8.dpToPx(),        // отступ сверху до линии
            marginBottom = 8.dpToPx(),     // отступ снизу после линии
            paddingStart = 16.dpToPx(),    // отступ слева
            paddingEnd = 16.dpToPx(),      // отступ справа
            color = Color.parseColor("#E0E0E0")
        )
        binding.recyclerView.addItemDecoration(divider)

    }


//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        binding = FragmentArticleListBinding.inflate(inflater)
//        recyclerView = binding.recyclerView
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//        val adapter = ContentAdapter(
//            onArticleClick = { },
//            onAdClick = { },
//        )
//        val divider = SpacingDecorator(
//            dividerHeight = 1.dpToPx(),    // высота линии
//            marginTop = 8.dpToPx(),        // отступ сверху до линии
//            marginBottom = 8.dpToPx(),     // отступ снизу после линии
//            paddingStart = 16.dpToPx(),    // отступ слева
//            paddingEnd = 16.dpToPx(),      // отступ справа
//            color = Color.parseColor("#E0E0E0")
//        )
//        recyclerView.addItemDecoration(divider)
//        recyclerView.adapter = adapter
//
//        val category = arguments?.getString("category") ?: "news"
//
//        val articles = listOf(
//            ListItem.ArticleItem("1" ,"Hello1", "world", "https://random.imagecdn.app/600/600"),
//            ListItem.ArticleItem("2" ,"Good", "Morning", "https://random.imagecdn.app/600/600"),
//            ListItem.ArticleItem("3", "Article3", "desc", "https://random.imagecdn.app/600/600"),
//        )
//
//        adapter.submitList(articles)
//
//        return binding.root
//    }

    companion object {
        private const val ARG_CATEGORY = "category"
        fun newInstance(category: String) = ContentListFragment().apply {
            arguments = bundleOf(ARG_CATEGORY to category)
        }
    }
}
