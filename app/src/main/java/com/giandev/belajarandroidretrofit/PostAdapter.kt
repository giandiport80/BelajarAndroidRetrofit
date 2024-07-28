package com.giandev.belajarandroidretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giandev.belajarandroidretrofit.databinding.ItemPostBinding
import com.giandev.belajarandroidretrofit.response.PostResponse

class PostAdapter(private val listPost: List<PostResponse>) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listPost.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = listPost[position]
        holder.binding.textViewId.text = "id: ${post.id}"
        holder.binding.textViewUserId.text = "userID: ${post.userId}"
        holder.binding.textViewTitle.text = post.title
        holder.binding.textViewBody.text = post.body
    }
}