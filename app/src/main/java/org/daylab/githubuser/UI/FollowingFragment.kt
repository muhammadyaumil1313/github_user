package org.daylab.githubuser.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.daylab.githubuser.R
import org.daylab.githubuser.adapter.FollowersListAdapter
import org.daylab.githubuser.databinding.FragmentFollowingBinding
import org.daylab.githubuser.models.Item
import org.daylab.githubuser.viewModels.DetailViewModel

class FollowingFragment : Fragment() {
    private lateinit var binding : FragmentFollowingBinding
    private val detailViewModel by viewModels<DetailViewModel>({requireParentFragment()})
    private lateinit var rvFollowing : RecyclerView
    private var username : String ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFollowing = binding.rvFollowing
        rvFollowing.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity)
        rvFollowing.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        rvFollowing.addItemDecoration(itemDecoration)

        detailViewModel.username.observe(viewLifecycleOwner){
            username = it
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        detailViewModel.listFollowing.observe(viewLifecycleOwner){
            runBlocking {
                delay(3000)
                val listFollowingAdapter = FollowersListAdapter(it as ArrayList<Item>)
                rvFollowing.adapter = listFollowingAdapter
                listFollowingAdapter.notifyDataSetChanged()
            }
        }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.pgFollowing.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}