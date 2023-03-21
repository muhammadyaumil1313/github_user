package org.daylab.githubuser.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.daylab.githubuser.adapter.LoveListAdapter
import org.daylab.githubuser.databinding.FragmentLoveBinding
import org.daylab.githubuser.helper.LoveHelper
import org.daylab.githubuser.helper.MappingHelper
import org.daylab.githubuser.models.Love
import org.daylab.githubuser.viewModels.DetailViewModel

class LoveFragment : Fragment() {
    private lateinit var binding : FragmentLoveBinding
    private lateinit var rvLoves : RecyclerView
    private val detailViewModel : DetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoveBinding.inflate(layoutInflater,container,false)
        activity?.actionBar?.title = "Love History"
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)

        rvLoves = binding.rvLoves
        rvLoves.setHasFixedSize(true)

        if(savedInstanceState == null){
            loadLovedAsync()
        }
        val layoutManager = LinearLayoutManager(activity)
        rvLoves.layoutManager = layoutManager
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loadLovedAsync(){
        lifecycleScope.launch {
            binding.pgLoves.visibility = View.VISIBLE
            val loveHelper = activity?.let { LoveHelper.getInstance(it.applicationContext) }
            loveHelper?.open()
            val defferedLove = async(Dispatchers.IO){
                val cursorQuery = loveHelper?.queryAll()
                MappingHelper.mapCursorToArrayList(cursorQuery)
            }
            binding.pgLoves.visibility = View.GONE
            val loves = defferedLove.await()
            if(loves.size > 0){
                setItem(loves)
            }else{
                Toast.makeText(activity,"Data Tidak Tersedia!", Toast.LENGTH_SHORT).show()
            }
            loveHelper?.close()
        }
    }
    private fun onClick(loveListAdapter: LoveListAdapter){
        loveListAdapter.setOnItemClickCallback(object : LoveListAdapter.OnItemClickCallback{
            override fun onItemClicked(dataLove: Love) {
                detailViewModel.showDetailUser(dataLove.login.toString())
                    .observe(viewLifecycleOwner) {
                        val toDetailUsersFragment = LoveFragmentDirections
                            .actionLoveFragmentToDetailUsersFragment()
                        toDetailUsersFragment.username = it?.login.toString()
                       findNavController().navigate(toDetailUsersFragment)
                    }
            }

        })
    }
    private fun setItem(modelItemArray: ArrayList<Love>) {
        val listLovedAdapter = LoveListAdapter(requireContext(),modelItemArray)
        rvLoves.adapter = listLovedAdapter
        listLovedAdapter.notifyDataSetChanged()
        onClick(listLovedAdapter)
    }


}