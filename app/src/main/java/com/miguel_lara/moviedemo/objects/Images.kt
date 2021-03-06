package com.miguel_lara.moviedemo.objects

data class Images (
    var base_url: String? = null,
    var secure_base_url: String? = null,
    var backdrop_sizes: List<String>? = null,
    var logo_sizes: List<String>? = null,
    var poster_sizes: List<String>? = null,
    var profile_sizes: List<String>? = null,
    var still_sizes: List<String>? = null
)
