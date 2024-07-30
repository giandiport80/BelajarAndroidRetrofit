package com.giandev.belajarandroidretrofit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.giandev.belajarandroidretrofit.databinding.ActivityMainBinding
import com.giandev.belajarandroidretrofit.response.CommentResponse
import com.giandev.belajarandroidretrofit.response.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val listPost = mutableListOf<PostResponse>()
    private val listComment = mutableListOf<CommentResponse>()
    private lateinit var adapter: PostAdapter
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        showListPost()
//        showListPostWithQuery()
//        showListPostWithQueryMap()
//        createPost()
//        showComments()
//        showCommentsWithUrl()

//        updatePutPost()
        updatePatchPost()
    }

    private fun showComments() {
        commentAdapter = CommentAdapter(listComment)
        binding.recyclerViewPost.setHasFixedSize(true);
        binding.recyclerViewPost.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerViewPost.adapter = commentAdapter

        RetrofitClient.instance.getComments(1).enqueue(object : Callback<List<CommentResponse>> {
            override fun onResponse(
                call: Call<List<CommentResponse>>,
                response: Response<List<CommentResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { listComment.addAll(it) }
                    val adapter = CommentAdapter(listComment)
                    binding.recyclerViewPost.adapter = adapter
                }
            }

            override fun onFailure(p0: Call<List<CommentResponse>>, p1: Throwable) {
                binding.textViewHeaderTitle.text = p1.message
            }

        })
    }

    private fun showCommentsWithUrl() {
        commentAdapter = CommentAdapter(listComment)
        binding.recyclerViewPost.setHasFixedSize(true);
        binding.recyclerViewPost.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerViewPost.adapter = commentAdapter

        RetrofitClient.instance.getCommentsWithUrl("posts/1/comments")
            .enqueue(object : Callback<List<CommentResponse>> {
                override fun onResponse(
                    call: Call<List<CommentResponse>>,
                    response: Response<List<CommentResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { listComment.addAll(it) }
                        val adapter = CommentAdapter(listComment)
                        binding.recyclerViewPost.adapter = adapter
                    }
                }

                override fun onFailure(p0: Call<List<CommentResponse>>, p1: Throwable) {
                    binding.textViewHeaderTitle.text = p1.message
                }
            })
    }

    private fun createPost() {
        RetrofitClient.instance.createPost(userId = 1, "retrofit tutorial", "retrofit description")
            .enqueue(
                object : Callback<PostResponse> {
                    override fun onResponse(
                        call: Call<PostResponse>,
                        response: Response<PostResponse>
                    ) {
                        Log.d("RETROFIT_RESPONSE", response.isSuccessful.toString())

                        if (response.isSuccessful) {
                            binding.textViewHeaderTitle.text = response.code().toString()
                            Toast.makeText(
                                applicationContext,
                                "Berhasil menyimpan data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                        binding.textViewHeaderTitle.text = t.message

                    }

                })
    }

    private fun showListPostWithQuery() {
        adapter = PostAdapter(listPost)
        binding.recyclerViewPost.setHasFixedSize(true);
        binding.recyclerViewPost.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerViewPost.adapter = adapter

        RetrofitClient.instance.getPostsWithQuery(4, 32)
            .enqueue(object : Callback<List<PostResponse>> {
                override fun onResponse(
                    call: Call<List<PostResponse>>,
                    response: Response<List<PostResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { Log.d("RETROFIT_SUCCESS_COBA", it.toString()) }
                        binding.textViewHeaderTitle.text = response.code().toString()

                        response.body()?.let {
                            Log.d("RETROFIT_SUCCESS", it.toString())
                            listPost.addAll(it)
                            adapter.notifyDataSetChanged()
                        }
                    } else {
                        binding.textViewHeaderTitle.text = response.code().toString()
                    }
                }

                override fun onFailure(p0: Call<List<PostResponse>>, t: Throwable) {
                    Log.d("RETROFIT_ERROR", "onFailure: ${t.localizedMessage}")
                }
            })
    }

    private fun showListPostWithQueryMap() {
        adapter = PostAdapter(listPost)
        binding.recyclerViewPost.setHasFixedSize(true);
        binding.recyclerViewPost.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerViewPost.adapter = adapter

        val parameters = HashMap<String, String>()
        parameters["userId"] = "4"
        parameters["id"] = "32"

        RetrofitClient.instance.getPostsWithQueryMap(parameters)
            .enqueue(object : Callback<List<PostResponse>> {
                override fun onResponse(
                    call: Call<List<PostResponse>>,
                    response: Response<List<PostResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { Log.d("RETROFIT_SUCCESS_COBA", it.toString()) }
                        binding.textViewHeaderTitle.text = response.code().toString()

                        response.body()?.let {
                            Log.d("RETROFIT_SUCCESS", it.toString())
                            listPost.addAll(it)
                            adapter.notifyDataSetChanged()
                        }
                    } else {
                        binding.textViewHeaderTitle.text = response.code().toString()
                    }
                }

                override fun onFailure(p0: Call<List<PostResponse>>, t: Throwable) {
                    Log.d("RETROFIT_ERROR", "onFailure: ${t.localizedMessage}")
                }
            })
    }

    private fun showListPost() {
        adapter = PostAdapter(listPost)
        binding.recyclerViewPost.setHasFixedSize(true);
        binding.recyclerViewPost.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerViewPost.adapter = adapter

        RetrofitClient.instance.getPosts().enqueue(object : Callback<List<PostResponse>> {
            override fun onResponse(
                call: Call<List<PostResponse>>,
                response: Response<List<PostResponse>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { Log.d("RETROFIT_SUCCESS_COBA", it.toString()) }
                    binding.textViewHeaderTitle.text = response.code().toString()

                    response.body()?.let {
                        Log.d("RETROFIT_SUCCESS", it.toString())
                        listPost.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    binding.textViewHeaderTitle.text = response.code().toString()
                }
            }

            override fun onFailure(p0: Call<List<PostResponse>>, t: Throwable) {
                Log.d("RETROFIT_ERROR", "onFailure: ${t.localizedMessage}")
            }
        })
    }

    private fun updatePutPost() {
        RetrofitClient.instance.putPost(
            id = 5,
            userId = 4,
            idField = 5,
            title = null,
            body = "hello body"
        )
            .enqueue(
                object : Callback<PostResponse> {
                    override fun onResponse(
                        call: Call<PostResponse>,
                        response: Response<PostResponse>
                    ) {
                        Log.d("RETROFIT_RESPONSE", response.isSuccessful.toString())

                        if (response.isSuccessful) {
                            binding.textViewHeaderTitle.text = response.code().toString()
                            response.body()?.toString()?.let { Log.d("RETROFIT_BODY", it) }
                            Toast.makeText(
                                applicationContext,
                                "Berhasil update data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                        binding.textViewHeaderTitle.text = t.message

                    }

                })
    }

    private fun updatePatchPost() {
        RetrofitClient.instance.patchPost(
            id = 5,
            userId = 4,
            idField = 5,
            title = "hello",
            body = "hello body"
        )
            .enqueue(
                object : Callback<PostResponse> {
                    override fun onResponse(
                        call: Call<PostResponse>,
                        response: Response<PostResponse>
                    ) {
                        Log.d("RETROFIT_RESPONSE", response.isSuccessful.toString())

                        if (response.isSuccessful) {
                            binding.textViewHeaderTitle.text = response.code().toString()
                            response.body()?.toString()?.let { Log.d("RETROFIT_BODY", it) }
                            Toast.makeText(
                                applicationContext,
                                "Berhasil update data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                        binding.textViewHeaderTitle.text = t.message

                    }

                })
    }

}