package com.settery.adappapr.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.settery.adappapr.R
import com.settery.adappapr.ViewPagerAdapter
import com.settery.adappapr.databinding.FragmentMainBinding
import com.settery.adappapr.domain.MainScreenState
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment: Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Настраиваем ViewPager с адаптером
        val pagerAdapter = ViewPagerAdapter(this) // `this` - ссылка на MainFragment
        binding.viewPager.adapter = pagerAdapter

        // 2. Связываем TabLayout и ViewPager
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = if (position == 0) "Одни способы" else "Другие способы"
        }.attach()

        // 3. Подписываемся на ViewModel (один ViewModel на весь экран!)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.container.stateFlow.collect { state ->
                renderState(state)
            }
        }
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { viewModel.onTabSelected(it) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun renderState(state: MainScreenState) {
        binding.progressBar.isVisible = state.isLoading
    }
    private fun handleSideEffect(sideEffect: MainScreenSideEffect) {
        when (sideEffect) {
            is MainScreenSideEffect.NavigateToArticle -> {
                // Используем Navigation Component для перехода
//                val action = MainFragmentDirections.actionMainFragmentToArticleFragment(sideEffect.article)
//                findNavController().navigate(action)
            }
            is MainScreenSideEffect.OpenBrowser -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sideEffect.url))
                startActivity(intent)
            }
            is MainScreenSideEffect.ShowErrorSnackbar -> {
                Snackbar.make(requireView(), sideEffect.message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}