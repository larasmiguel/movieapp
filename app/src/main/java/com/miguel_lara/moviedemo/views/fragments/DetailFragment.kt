package com.miguel_lara.moviedemo.views.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miguel_lara.moviedemo.R
import com.miguel_lara.moviedemo.adapters.MoviesAdapter
import com.miguel_lara.moviedemo.adapters.TrailersAdapter
import com.miguel_lara.moviedemo.databinding.FragmentDetailBinding
import com.miguel_lara.moviedemo.helpers.EndlessScrollListener
import com.miguel_lara.moviedemo.interfaces.TrailerEvents
import com.miguel_lara.moviedemo.objects.Movie
import com.miguel_lara.moviedemo.objects.Trailer
import com.miguel_lara.moviedemo.views.viewmodels.DetailVM
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, poserPath: String) {
    Picasso.get().load(poserPath).into(view)
}

@BindingAdapter("data")
fun setTrailerRVProperties(recyclerView: RecyclerView?, data: MutableList<Trailer>?) {
    val adapter = recyclerView?.adapter
    if (adapter is TrailersAdapter && data != null) {
        adapter.setData(data)
    }
}

class DetailFragment : Fragment(), TrailerEvents {
    private var movie: Movie? = null
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getSerializable("movie") as Movie?
        }
    }

    private fun setupBackButton() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val detailVM =
            ViewModelProvider(requireActivity()).get(DetailVM::class.java)
        movie?.let { detailVM.setMovie(it) }
        binding.viewModel = detailVM
        binding.lifecycleOwner = requireActivity()
        binding.tvLink.setOnClickListener {
            val url = binding.tvLink.text.toString()
            if (url.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
        val layoutMgr = LinearLayoutManager(context)
        binding.listTrailers.apply {
            layoutManager = layoutMgr
            adapter = TrailersAdapter(mutableListOf(), this@DetailFragment)
        }
        setHasOptionsMenu(true)
        setupBackButton()
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("movie", movie)
                }
            }
    }

    override fun onTrailerClick(trailer: Trailer) {
        if (trailer.site?.lowercase().equals("youtube")) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=" + trailer.key)
                )
            )
        }
        else {
            Toast.makeText(context, getString(R.string.only_youtube), Toast.LENGTH_SHORT).show();
        }
    }
}