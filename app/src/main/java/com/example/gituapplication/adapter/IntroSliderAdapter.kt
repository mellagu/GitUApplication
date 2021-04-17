package com.example.gituapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gituapplication.R
import com.example.gituapplication.databinding.SlideItemContainerBinding
import com.example.gituapplication.intro.IntroSlide

class IntroSliderAdapter(private val introSlides: List<IntroSlide>) :
    RecyclerView.Adapter<IntroSliderAdapter.IntoSlideViewHolder>() {

    inner class IntoSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = SlideItemContainerBinding.bind(view)

        var textTitle = binding.tvTitle
        var textDesc = binding.tvDescription
        var imageIcon = binding.imgSlideIcon

        fun bind(introSlide: IntroSlide) {
            textTitle.text = introSlide.title
            textDesc.text = introSlide.description
            imageIcon.setImageResource(introSlide.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntoSlideViewHolder {
        return IntoSlideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    override fun onBindViewHolder(holder: IntoSlideViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }


}