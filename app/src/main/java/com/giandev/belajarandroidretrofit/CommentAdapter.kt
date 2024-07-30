package com.giandev.belajarandroidretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giandev.belajarandroidretrofit.databinding.ItemCommentBinding
import com.giandev.belajarandroidretrofit.response.CommentResponse

class CommentAdapter(private val listComment: List<CommentResponse>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), null, false)
        )
    }

    override fun getItemCount(): Int {
        return listComment.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = listComment[position]
        holder.binding.textViewId.text = "id: ${comment.id}"
        holder.binding.textViewPostId.text = "postID: ${comment.postId}"
        holder.binding.textViewName.text = comment.name
        holder.binding.textViewEmail.text = comment.email
        holder.binding.textViewBody.text = comment.body
    }
}