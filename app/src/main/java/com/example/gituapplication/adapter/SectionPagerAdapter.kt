package com.example.gituapplication.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gituapplication.followfragment.FollowersFragment
import com.example.gituapplication.followfragment.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, val login: String) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }

        return (fragment as Fragment).apply {
            arguments = Bundle().apply {
                putString(FollowersFragment.USERNAME, login)
            }
        }
    }

}