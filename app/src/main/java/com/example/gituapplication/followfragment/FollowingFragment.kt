package com.example.gituapplication.followfragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gituapplication.adapter.GitUHomeAdapter
import com.example.gituapplication.DetailUserActivity
import com.example.gituapplication.R
import com.example.gituapplication.databinding.FragmentFollowBinding
import com.example.gituapplication.model.FollowingViewModel

class FollowingFragment : Fragment(R.layout.fragment_follow) {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var adapter: GitUHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followingViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        adapter = GitUHomeAdapter(GitUHomeAdapter.ViewType.DETAIL) {
            val intent = Intent(requireContext(), DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, it)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uname = arguments?.getString(USERNAME) ?: ""
        Log.d("Follower Frag", "uname= $uname")
        followingViewModel.setListFollowing(uname)
        //call the function
        observeViewModel()
        showRecycleList()
    }

    private fun observeViewModel() {
        followingViewModel.getListFollowing().observe(viewLifecycleOwner, Observer { users ->
            if (users != null) {
                Log.d("Followers Fragment", users.size.toString())
                adapter.setList(users)
            }
        })
    }

    private fun showRecycleList() {
        binding.rvFollow.layoutManager = LinearLayoutManager(activity)
        binding.rvFollow.adapter = adapter
    }

    companion object {

        const val USERNAME = "user_name"

        @JvmStatic
        fun newInstance(user: String): FollowersFragment {
            return FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, user)
                }
            }
        }
    }


}