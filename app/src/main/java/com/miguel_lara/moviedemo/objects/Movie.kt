package com.miguel_lara.moviedemo.objects

import com.miguel_lara.moviedemo.App
import java.io.Serializable

data class Movie (
    var poster_path: String?="",
    var adult: Boolean?=false,
    var overview: String?="",
    var release_date: String?="",
    var genre_ids: List<Int>?=ArrayList(),
    var id: Int,
    var original_title: String?="",
    var original_language: String?="",
    var title: String?="",
    var backdrop_path: String?="",
    var popularity: Float?=0.0f,
    var vote_count: Int?=0,
    var video: Boolean?=false,
    var vote_average: Float?=0.0f
): Serializable {
    companion object {
        fun getGenres(movie: Movie): String {
            var genresString = ""
            movie.genre_ids?.forEach { event ->
                App.genres?.forEach { genre ->
                    if (genre.id == event) {
                        genresString += genre.name + " "
                    }
                }
            }
            return genresString
        }

        fun getFullImageUrl(movie: Movie): String? {
            val imagePath: String = if (movie.poster_path != null && movie.poster_path!!.isNotEmpty()) {
                movie.poster_path!!
            } else {
                movie.backdrop_path.toString()
            }
            val images = App.images
            if (images?.base_url != null && !images.base_url!!.isEmpty()) {
                images.base_url = images.base_url!!.replace("http:", "https:");
                if (images.poster_sizes != null) {
                    return if (images.poster_sizes!!.size > 4) {
                        // usually equal to 'w500'
                        images.base_url + images.poster_sizes!!.get(4).toString() + imagePath
                    } else {
                        // back-off to hard-coded value
                        images.base_url.toString() + "w500" + imagePath
                    }
                }
            }
            return ""
        }
    }
}