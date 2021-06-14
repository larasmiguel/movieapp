package com.miguel_lara.moviedemo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miguel_lara.moviedemo.R
import com.miguel_lara.moviedemo.interfaces.MovieEvents
import com.miguel_lara.moviedemo.objects.Movie
import com.squareup.picasso.Picasso

class MoviesAdapter(private val movies: MutableList<Movie>, private val events: MovieEvents) :
    RecyclerView.Adapter<MoviesAdapter.CustomViewHolder>() {

    fun setData(data: MutableList<Movie>) {
        movies.clear()
        movies.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            val imageView = itemView.findViewById<ImageView>(R.id.movieImage)
            val imagePath = Movie.getFullImageUrl(movie)
            itemView.setOnClickListener {
                events.onMovieClick(movie)
            }
            if (!imagePath.isNullOrEmpty())  {
                Picasso.get().load(imagePath).into(imageView)
            }
            val genresString = Movie.getGenres(movie)
            val year = movie.release_date?.substring(0,4)
            val labelText = "($year)  $genresString"
            itemView.findViewById<TextView>(R.id.GenreYearTitle).text = labelText
            itemView.findViewById<TextView>(R.id.movieTitle).text = movie.original_title
            itemView.findViewById<TextView>(R.id.movieRate).text = movie.popularity.toString()
        }
    }
}