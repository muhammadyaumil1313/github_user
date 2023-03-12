package org.daylab.githubuser.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.daylab.githubuser.R
import org.daylab.githubuser.adapter.SectionsPagerAdapter
import org.daylab.githubuser.databinding.FragmentDetailUsersBinding
import org.daylab.githubuser.models.Item
import org.daylab.githubuser.models.ResponseUser
import org.daylab.githubuser.utils.ApiConfig
import org.daylab.githubuser.utils.ApiService
import org.daylab.githubuser.viewModels.DetailViewModel
import org.daylab.githubuser.viewModels.ListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUsersFragment : Fragment() {
    private lateinit var binding : FragmentDetailUsersBinding
    private lateinit var apiConfig: ApiService
    lateinit var username : String
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailUsersBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get data from bundling safeargs
        username = DetailUsersFragmentArgs.fromBundle(arguments as Bundle).username
        detailViewModel.setUsername(username)
        //declare api service
        apiConfig = ApiConfig.getApiService()
        //set loading
        detailViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
        //set toast
        detailViewModel.isToast.observe(viewLifecycleOwner){
            showToast(it)
        }
        //implement the data
        detailViewModel.showDetailUser(username).observe(viewLifecycleOwner){
            runBlocking {
                delay(3000)
                setDetail(dataUsername=it?.login, dataAvatar = it?.avatarUrl, dataName = it?.name)
            }
        }
        detailViewModel.getFollowers(dataUsername = username)
        //getFollowers
        detailViewModel.listFollowers.observe(viewLifecycleOwner){
            runBlocking {
                delay(1000)
                binding.countFollowers.text = "Followers : ${it.count()}"
            }
        }

        //getFollowing
//        detailViewModel.getFollowing(username).observe(viewLifecycleOwner){
//            runBlocking {
//                delay(1000)
//                binding.countFollowing.text = "Following : ${it.count()}"
//            }
//        }

        binding.arrowBack.setOnClickListener {
            findNavController().navigate(DetailUsersFragmentDirections.actionDetailUsersFragmentToListFragment())
        }

        //view pager and tablayout
        val sectionsPagerAdapter = SectionsPagerAdapter(childFragmentManager,this)
        val viewPager : ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs : TabLayout = binding.tabFollowingAndFollowers
        TabLayoutMediator(tabs,viewPager){tab,position ->
            tab.text = resources.getString(MainActivity.TAB_TITLES[position])
        }.attach()

    }

    private fun showToast(isToast: Boolean){
        if(isToast) Toast.makeText(
            activity,
            "Error! please check the error",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pgDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setDetail(dataUsername : String?, dataAvatar : String?,dataName : String?) {
        Glide.with(this).load(dataAvatar).into(binding.avatar)
        if (dataAvatar != null && dataName != null && dataUsername != null){
            binding.name.text = dataName
            binding.username.text = dataUsername
        }

    }
}