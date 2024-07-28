package com.giandev.belajarandroidretrofit

import retrofit2.Call

interface Api {
    fun getPosts(): Call<List<PostResponse>>
}