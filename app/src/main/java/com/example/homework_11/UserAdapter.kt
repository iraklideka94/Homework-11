package com.example.homework_11


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework_11.databinding.UserItemBinding
import com.example.homework_11.databinding.UserItemImageBinding

class UserAdapter : ListAdapter<User, RecyclerView.ViewHolder>(UserItemCallback()) {

    var onDeleteClickListener: ((User) -> Unit)? = null
    var onEditClickListener: ((User) -> Unit)? = null


    companion object {
        const val USER_ITEM = 1
        const val USER_ITEM_IMAGE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            USER_ITEM -> UserViewHolder(
                UserItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> ImageUserViewHolder(
                UserItemImageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserViewHolder -> holder.bind()
            is ImageUserViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).image) {
            null -> USER_ITEM
            else -> USER_ITEM_IMAGE
        }
    }

    private inner class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val note = getItem(adapterPosition)
            binding.apply {
                textName.text = note.name
                textLastname.text = note.lastName
                deleteBtn.setOnClickListener { onDeleteClickListener?.invoke(note) }
                editBtn.setOnClickListener { onEditClickListener?.invoke(note) }
            }
        }
    }

    private inner class ImageUserViewHolder(private val binding: UserItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val note = getItem(adapterPosition)
            binding.apply {
                textName.text = note.name
                textView3.text = note.lastName
                Glide.with(this.root)
                    .load(note.image)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView)
                deleteBtn.setOnClickListener { onDeleteClickListener?.invoke(note) }
                editBtn.setOnClickListener { onEditClickListener?.invoke(note) }
            }
        }
    }

    private class UserItemCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

}