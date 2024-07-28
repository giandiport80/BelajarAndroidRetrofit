package com.giandev.belajarandroidretrofit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.giandev.belajarandroidretrofit.databinding.ActivityMainBinding
import com.giandev.belajarandroidretrofit.response.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val listPost = mutableListOf<PostResponse>()
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = PostAdapter(listPost)
        binding.recyclerViewPost.setHasFixedSize(true);
        binding.recyclerViewPost.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerViewPost.adapter = adapter

//        showListPost()
        createPost()
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

    private fun showListPost() {
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
}