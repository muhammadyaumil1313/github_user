package org.daylab.githubuser.UI

import android.content.ContentValues
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.daylab.githubuser.R
import org.daylab.githubuser.adapter.SectionsPagerAdapter
import org.daylab.githubuser.databinding.FragmentDetailUsersBinding
import org.daylab.githubuser.db.DatabaseContract
import org.daylab.githubuser.helper.LoveHelper
import org.daylab.githubuser.models.Item
import org.daylab.githubuser.models.Love
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
    private lateinit var loveHelper:LoveHelper
    private lateinit var fab : FloatingActionButton
    private var love:Love?=null
    private val detailViewModel: DetailViewModel by viewModels()
    companion object{
        const val EXTRA_USERNAME = "extra_username"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController()
                    .navigate(DetailUsersFragmentDirections.actionDetailUsersFragmentToListFragment())
            }

        })
    }
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

        loveHelper = LoveHelper.getInstance(requireContext())
        loveHelper.open()

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

        //getFollowers
        detailViewModel.getFollowers(dataUsername = username)
        detailViewModel.listFollowers.observe(viewLifecycleOwner){
            runBlocking {
                delay(1000)
                binding.countFollowers.text = "Followers : ${it.count()}"
            }
        }

        //getFollowing
        detailViewModel.getFollowing(dataUsername = username)
        detailViewModel.listFollowing.observe(viewLifecycleOwner){
            binding.countFollowing.text = "Following : ${it.count()}"
        }

        //floating action button love
        fab = binding.fabLove

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
            fab.setOnClickListener {
                val values = ContentValues()
                values.put(DatabaseContract.LoveColumn.name, dataName)
                values.put(DatabaseContract.LoveColumn.login,dataUsername)
                values.put(DatabaseContract.LoveColumn.avatar_url,dataAvatar)
                values.put(DatabaseContract.LoveColumn.followersUrl,"null")
                values.put(DatabaseContract.LoveColumn.followingUrl,"null")
                val isExist = loveHelper.checkIfExist(dataUsername)
                if(isExist){
                    Toast.makeText(activity,"Data has been loved!",Toast.LENGTH_SHORT).show()
                }else{
                    val isInserted = loveHelper.insert(values)
                    if(isInserted > 0){
                        love?.id = isInserted.toString()
                        Toast.makeText(activity,"Lovely! thanks bro",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity,"Failed! Gagal menambahkan love!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}