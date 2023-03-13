package org.daylab.githubuser.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.daylab.githubuser.R
import org.daylab.githubuser.adapter.FollowersListAdapter
import org.daylab.githubuser.databinding.FragmentFollowersBinding
import org.daylab.githubuser.models.Item
import org.daylab.githubuser.viewModels.DetailViewModel

class FollowersFragment : Fragment() {
    private lateinit var binding : FragmentFollowersBinding
    private lateinit var rvFollowers : RecyclerView
    private val detailViewModel by viewModels<DetailViewModel>({requireParentFragment()})
    private var username : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFollowers = binding.rvFollowers
        rvFollowers.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity)
        rvFollowers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        rvFollowers.addItemDecoration(itemDecoration)


        detailViewModel.username.observe(viewLifecycleOwner){
            username = it
        }

//        detailViewModel.getFollowers(dataUsername = username.toString())
        detailViewModel.listFollowers.observe(viewLifecycleOwner){
            runBlocking {
                delay(1000)
                val listFollowersAdapter = FollowersListAdapter(it as ArrayList<Item>)
                rvFollowers.adapter = listFollowersAdapter
                listFollowersAdapter.notifyDataSetChanged()
            }
        }


    }
}