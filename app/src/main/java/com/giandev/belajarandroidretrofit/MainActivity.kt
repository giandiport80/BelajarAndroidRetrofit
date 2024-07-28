package com.giandev.belajarandroidretrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.giandev.belajarandroidretrofit.databinding.ActivityMainBinding
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