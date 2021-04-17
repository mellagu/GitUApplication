package com.example.gitufavorite

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitufavorite.databinding.UserHomeBinding

class FavoriteAdapter(private val viewType: ViewType, private val listener: (User) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.CardViewHolder>() {

    enum class ViewType(val layout: Int) {
        HOME(R.layout.user_home)
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

            val binding = UserHomeBinding.bind(itemView)
            val txtName = binding.txtName
            val avatar = binding.imgAvatar
            val urlGithub = binding.githubUrl

            Glide.with(itemView.context)
                .load(gitu.avatar_url)
                .into(avatar)
            txtName.text = gitu.login
            urlGithub.text = gitu.html_url.removeRange(0, 8)


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