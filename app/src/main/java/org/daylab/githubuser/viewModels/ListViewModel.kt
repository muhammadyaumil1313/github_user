package org.daylab.githubuser.viewModels

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.daylab.githubuser.R
import org.daylab.githubuser.UI.ListFragment
import org.daylab.githubuser.UI.ListFragmentDirections
import org.daylab.githubuser.models.Item
import org.daylab.githubuser.models.ResponseUser
import org.daylab.githubuser.utils.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel() {

    private val _listUsers = MutableLiveData<List<Item>?>()
    val listUsers : LiveData<List<Item>?> = _listUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isToast = MutableLiveData<Boolean>()
    val isToast : LiveData<Boolean> = _isToast

    private val _DetailUser = MutableLiveData<Item?>()
    private val DetailUser : LiveData<Item?> = _DetailUser


    private val _responseSearch = MutableLiveData<List<Item>>()
    private val responseSearch : LiveData<List<Item>> = _responseSearch

    private val apiConfig = ApiConfig.getApiService()


    init {
        getAllUsers()
    }
    //get all users
     fun getAllUsers(){
        _isLoading.value = true
        val client = apiConfig.getListUsers()
        client.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                _isLoading.value = true
                if(response.isSuccessful){
                    val responseBody = response.body()
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        if (responseBody != null) {
                            _listUsers.value = responseBody
                        }
                    }else{
                        _isToast.value = true
                        Log.e(ListFragment.TAG, "onFailure response: Error response")
                    }
                }
            }
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                _isLoading.value = true
                Log.e(ListFragment.TAG, "onFailure: ${t.message} call : ${call}")
            }

        })
    }

    fun searchItems(username : String) : LiveData<List<Item>>{
        val client = apiConfig.searchData(username = username)
        client.enqueue(object : Callback<ResponseUser>{
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
               runBlocking {
                   _isLoading.value = true
                   delay(2000)
                   val responseBody = response.body()
                   if(response.isSuccessful){
                       if (responseBody != null) {
                           _responseSearch.value = responseBody.items
                       }else{
                           _isToast.value = true
                           Log.e("Error Response Body","Response Body Null")
                       }
                   }else{
                       _isToast.value = true
                       Log.e(ListFragment.TAG, "onFailure: failure response is null")
                   }
                   _isLoading.value = false
               }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Log.e(ListFragment.TAG, "onFailure: ${t.message} call : ${call}")
            }

        })
        return responseSearch
    }

    fun detailUser(username: String) : LiveData<Item?>{
        val apiService = apiConfig.getDetailUser(username = username)
        apiService.enqueue(object  : Callback<Item>{
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                val responseBody = response.body()
                if(response.isSuccessful){
                    if(responseBody != null){
                        _DetailUser.value = responseBody
                    }else{
                        _isToast.value = true
                        Log.d("response body failure", response.message())
                    }
                }else{
                    _isToast.value = true
                    Log.d("response failure", response.message())
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                _isToast.value = true
                Log.d("failure","error : ${t.message}")
            }

        })

        return DetailUser
    }
}