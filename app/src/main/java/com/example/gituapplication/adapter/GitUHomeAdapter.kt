package com.example.gituapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gituapplication.R
import com.example.gituapplication.data.local.User
import com.example.gituapplication.databinding.FollowDetailBinding
import com.example.gituapplication.databinding.UserHomeBinding

class GitUHomeAdapter(val viewType: ViewType, private val listener: (User) -> Unit) :
    RecyclerView.Adapter<GitUHomeAdapter.CardViewHolder>() {

    enum class ViewType(val layout: Int) {
        HOME(R.layout.user_home),
        DETAIL(R.layout.follow_detail),
    }

    private val list = ArrayList<User>()
    fun setList(users: ArrayList<User>) {
        Log.d("GitU Adp", "size = ${users.size}")
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val gitu = list[position]
            itemView.setOnClickListener {
                listener(list[adapterPosition])
            }
            if (viewType == ViewType.HOME) {
                val binding = UserHomeBinding.bind(itemView)
                val txtName = binding.txtName
                val avatar = binding.imgAvatar
                val urlGithub = binding.githubUrl

                Glide.with(itemView.context)
                    .load(gitu.avatar_url)
                    .into(avatar)
                txtName.text = gitu.login
                urlGithub.text = gitu.html_url.removeRange(0, 8)

            } else {
                val binding = FollowDetailBinding.bind(itemView)
                val txtName = binding.tvLoginFollow
                val avatar = binding.imgItemFollow
                Glide.with(itemView.context)
                    .load(gitu.avatar_url)
                    .into(avatar)
                txtName.text = gitu.login
            }
        }
    }

    override fun getItemViewType(position: Int): Int = viewType.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(this.viewType.layout, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("list", list.size.toString())
        return list.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(position)
    }

}