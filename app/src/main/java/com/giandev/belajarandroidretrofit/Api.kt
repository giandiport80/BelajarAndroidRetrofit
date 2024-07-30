package com.giandev.belajarandroidretrofit

import com.giandev.belajarandroidretrofit.response.CommentResponse
import com.giandev.belajarandroidretrofit.response.PostResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface Api {
    /**
     * jika kita menuliskan full url nya, maka akan menimpa baseUrl nya
     */
    @GET("posts")
    fun getPosts(): Call<List<PostResponse>>

    @GET("posts")
    fun getPostsWithQuery(
        @Query("userId") userId: Int,
        @Query("id") id: Int,
    ): Call<List<PostResponse>>

    @GET("posts")
    fun getPostsWithQueryMap(@QueryMap parameters: Map<String, String>): Call<List<PostResponse>>

    @FormUrlEncoded
    @POST("posts")
    fun createPost(
        @Field("userId") userId: Int,
        @Field("title") title: String,
        @Field("body") body: String,
    ): Call<PostResponse>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") postId: Int): Call<List<CommentResponse>>

    @GET
    fun getCommentsWithUrl(@Url url: String): Call<List<CommentResponse>>

    @FormUrlEncoded
    @PUT("posts/{id}")
    fun putPost(
        @Path("id") id: Int,
        @Field("id") idField: Int,
        @Field("userId") userId: Int,
        @Field("title") title: String?,
        @Field("body") body: String?,
    ): Call<PostResponse>

    @FormUrlEncoded
    @PATCH("posts/{id}")
    fun patchPost(
        @Path("id") id: Int,
        @Field("id") idField: Int,
        @Field("userId") userId: Int,
        @Field("title") title: String,
        @Field("body") body: String?,
    ): Call<PostResponse>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<Void>
}