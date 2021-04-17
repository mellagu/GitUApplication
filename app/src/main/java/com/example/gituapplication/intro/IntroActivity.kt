package com.example.gituapplication.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.gituapplication.MainActivity
import com.example.gituapplication.R
import com.example.gituapplication.adapter.IntroSliderAdapter
import com.example.gituapplication.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.introSliderViewPager2.adapter = introSliderAdapter

        //calling function
        setupIndicators()
        setCurrentIndicator(0)
        binding.introSliderViewPager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        binding.btnNext.setOnClickListener{
            if (binding.introSliderViewPager2.currentItem +1 < introSliderAdapter.itemCount) {
                binding.introSliderViewPager2.currentItem +=1
            } else {
                Intent(applicationContext, MainActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        binding.tvSkipIntro.setOnClickListener{
            Intent(applicationContext, MainActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide(
                "Say Hi",
                "Say hi to many developers from around the world",
                R.drawable.world
            ),
            IntroSlide(
                "Surfing",
                "Surfing to the Github's users just use your phone",
                R.drawable.mobile
            ),
            IntroSlide(
                "Connecting",
                "Build a programming relation with GitU",
                R.drawable.code_together
            )
        )
    )

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView2 = binding.indicatorsContainer[i] as ImageView
            if (i == index) {
                imageView2.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView2.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }

    private fun setupIndicators(){
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.indicatorsContainer.addView(indicators[i])
        }
    }
}
