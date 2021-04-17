package com.example.gituapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.gituapplication.adapter.SectionPagerAdapter
import com.example.gituapplication.data.local.User
import com.example.gituapplication.databinding.ActivityDetailUserBinding
import com.example.gituapplication.model.UserViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var mainViewModel: UserViewModel
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra(EXTRA_USER) as User
        val username = user.login
        val avatarUrl = user.avatar_url
        val id = user.id
        val htmlUrl = user.html_url


        //Share username following followers
        bundle = Bundle()
        bundle.putString(EXTRA_USER, user.login)

        //Set For Back Arrow
        binding.back.setOnClickListener {
            onBackPressed()
        }
        //View Model
        mainViewModel = ViewModelProvider(
            this
        ).get(UserViewModel::class.java)
        user.login.let { mainViewModel.setDetailUser(it) }

        observeViewModel()
        pagerAdapterFollow(user.login)

        //Check for favorite
        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = id.let { mainViewModel.checkUser(it) }
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }

                }
            }

        }
        //Add or delete in Favorite
        binding.toggleFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                mainViewModel.addToFavorite(username, id, avatarUrl, htmlUrl)
                Toast.makeText(this,getString(R.string.addFavorite),Toast.LENGTH_LONG).show()
            } else {
                id.let { it1 -> mainViewModel.removeFromFavorite(it1) }
                Toast.makeText(this,getString(R.string.deletedFavorite),Toast.LENGTH_LONG).show()
            }
            binding.toggleFavorite.isChecked = _isChecked
        }

    }

    //Observe data model
    private fun observeViewModel() {
        mainViewModel.getDetailUsers().observe(this, Observer { user ->
            //Data parcelable
            Log.d("Detail", user.toString())
            binding.tvNama.text = user.name
            binding.tvUname.text = user.login
            binding.tvLocation.text = user.location
            binding.tvCompany.text = user.company
            binding.tvFollowers.text = user.followers.toString()
            binding.tvFollowing.text = user.following.toString()
            binding.tvRepo.text = user.public_repos.toString()
            Glide.with(this)
                .load(user.avatar_url)
                .into(binding.ivAvatar)

            showLoading(false)
        })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }

    //Pager Adapter for Following Followers
    private fun pagerAdapterFollow(login: String) {
        val sectionsPagerAdapter = SectionPagerAdapter(this, login)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_1,
            R.string.tab_2
        )
    }

}