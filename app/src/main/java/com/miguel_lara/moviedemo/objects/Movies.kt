package com.miguel_lara.moviedemo.objects

data class Movies (
    var page: Int?=0,
    var results: List<Movie>?=ArrayList(),
    var total_results: Int?=0,
    var total_pages:Int?=0
)
