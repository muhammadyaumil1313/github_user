package org.daylab.githubuser.UI

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.daylab.githubuser.R
import org.daylab.githubuser.adapter.UserListAdapter
import org.daylab.githubuser.databinding.FragmentListBinding
import org.daylab.githubuser.models.Item
import org.daylab.githubuser.utils.ApiConfig
import org.daylab.githubuser.utils.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.daylab.githubuser.viewModels.ListViewModel
import kotlin.math.log

class ListFragment : Fragment() {
    private lateinit var binding : FragmentListBinding
    private lateinit var rvUsers : RecyclerView
    private lateinit var apiConfig: ApiService
    private val listViewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    companion object{
        const val TAG = "Main Activity"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater)

        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        apiConfig = ApiConfig.getApiService()
        rvUsers = binding.listUsers
        rvUsers.setHasFixedSize(true)

        listViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        listViewModel.listUsers.observe(viewLifecycleOwner) { listuser ->
            if (listuser != null) {
                setItem(listuser as ArrayList<Item>)
            }
        }

        listViewModel.isToast.observe(viewLifecycleOwner){
            showToast(it)
        }

        val layoutManager = LinearLayoutManager(activity)
        rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        rvUsers.addItemDecoration(itemDecoration)

    }
    private fun showLoading(isLoading: Boolean) {
        binding.pgUsers.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(isToast: Boolean){
        if(isToast)
            Toast.makeText(activity,"Error! please check the error",Toast.LENGTH_SHORT).show()
    }
    private fun setItem(modelItemArray: ArrayList<Item>) {
        val listUsersAdapter = UserListAdapter(modelItemArray)
        rvUsers.adapter = listUsersAdapter
        listUsersAdapter.notifyDataSetChanged()
        itemClicked(listUsersAdapter)
    }
    private fun itemClicked(listUsersAdapter : UserListAdapter){
        listUsersAdapter.setOnItemClickCallback(object : UserListAdapter.onitemClickCallback{
            override fun onItemClicked(dataUser: Item) {
                listViewModel.detailUser(dataUser.login.toString())
                listViewModel.detailUserData.observe(viewLifecycleOwner){
                    val toDetailUsersFragment =
                        ListFragmentDirections.actionListFragmentToDetailUsersFragment()
                    toDetailUsersFragment.username = it?.login.toString()
                    findNavController().navigate(toDetailUsersFragment)
                }
            }

        })
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when(item.itemId){
            R.id.love_bar -> {
                //TODO navigation to detail love
                true
            }
            else -> {
                true
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.option_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_bar).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

        searchView.queryHint = resources.getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                // do query here
                if (query != null) {
                    searchView.clearFocus()
                    listViewModel.searchItems(query)
                    listViewModel.responseSearch.observe(viewLifecycleOwner){
                       runBlocking {
                           delay(2000)
                           val item = it as ArrayList<Item>
                           setItem(item)
                       }
                    }
                }else{
                    Toast.makeText(activity,"Please fill the form search",Toast.LENGTH_SHORT).show()
                }
                //it just for clear your input
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }
}