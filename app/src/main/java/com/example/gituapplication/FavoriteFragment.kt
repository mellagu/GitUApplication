package com.example.gituapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gituapplication.adapter.GitUHomeAdapter
import com.example.gituapplication.databinding.FragmentFavoriteBinding
import com.example.gituapplication.model.FavoriteViewModel


class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var adapter: GitUHomeAdapter
    private lateinit var viewModel: FavoriteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Add view model
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.rvFavorite.layoutManager = LinearLayoutManager(activity)
        adapter = GitUHomeAdapter(GitUHomeAdapter.ViewType.HOME) {
            //intent for click list data
            val intent = Intent(requireContext(), DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, it)
            startActivity(intent)
        }
        adapter.notifyDataSetChanged()
        binding.rvFavorite.adapter = adapter

        //getfavoriteuser
        viewModel.getFavoriteUser()?.observe(viewLifecycleOwner, Observer { users ->
            if (users != null) {
                val list = if (users is ArrayList) users else ArrayList(users)
                adapter.setList(list)
            }
        })

    }


}