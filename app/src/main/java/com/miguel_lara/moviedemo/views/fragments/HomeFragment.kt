package com.miguel_lara.moviedemo.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.miguel_lara.moviedemo.R
import com.miguel_lara.moviedemo.adapters.MoviesAdapter
import com.miguel_lara.moviedemo.adapters.TrailersAdapter
import com.miguel_lara.moviedemo.databinding.FragmentHomeBinding;
import com.miguel_lara.moviedemo.helpers.EndlessScrollListener
import com.miguel_lara.moviedemo.interfaces.MovieEvents
import com.miguel_lara.moviedemo.interfaces.ScrollToBottomListener
import com.miguel_lara.moviedemo.objects.Movie
import com.miguel_lara.moviedemo.views.viewmodels.HomeVM

@BindingAdapter("data")
fun setRecyclerViewProperties(recyclerView: RecyclerView?, data: MutableList<Movie>?) {
    val adapter = recyclerView?.adapter
    if (adapter is MoviesAdapter && data != null) {
        adapter.setData(data)
    }
}

class HomeFragment : Fragment(), ScrollToBottomListener, MovieEvents {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val homeVM =
            ViewModelProvider(requireActivity()).get(HomeVM::class.java)
        binding.viewModel = homeVM
        binding.lifecycleOwner = requireActivity()
        binding.swipeRefreshLayout.setOnRefreshListener {
            homeVM.loadData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        val layoutMgr = LinearLayoutManager(context)
        binding.recyclerView.addOnScrollListener(EndlessScrollListener(layoutMgr, this))
        binding.recyclerView.apply {
            layoutManager = layoutMgr
            adapter = MoviesAdapter(mutableListOf(), this@HomeFragment)
        }
        binding.tabOptions.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                homeVM.changeType(tab.position)
                homeVM.loadData()
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel?.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onScrollToBottom() {
        binding.viewModel?.loadData(true)
    }

    override fun onMovieClick(movie: Movie) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, DetailFragment.newInstance(movie)).addToBackStack("detail")
        transaction.commit()
    }

}