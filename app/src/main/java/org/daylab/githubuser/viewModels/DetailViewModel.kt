package org.daylab.githubuser.viewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.daylab.githubuser.databinding.FragmentDetailUsersBinding
import org.daylab.githubuser.models.Item
import org.daylab.githubuser.utils.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isToast = MutableLiveData<Boolean>()
    val isToast : LiveData<Boolean> = _isToast

    private val _username = MutableLiveData<String>()
    val username : LiveData<String> = _username

    private val _dataUser = MutableLiveData<Item?>()
    val dataUser : LiveData<Item?> = _dataUser

    private val _listFollowers = MutableLiveData<List<Item?>>()
    val listFollowers : LiveData<List<Item?>> =  _listFollowers

    private val _listFollowing = MutableLiveData<List<Item?>>()
    val listFollowing : LiveData<List<Item?>> =  _listFollowing

    private val apiConfig = ApiConfig.getApiService()

    fun setUsername(name : String){
        _username.value = name
    }
    fun showDetailUser(dataUsername : String) : LiveData<Item?>{
        apiConfig.getDetailUser(dataUsername).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                _isLoading.value = true
                if(response.isSuccessful){
                    _dataUser.value = response.body()
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                _isToast.value = true
            }

        })
        return dataUser
    }
    fun getFollowers(dataUsername: String){
        apiConfig.getFollowers(dataUsername).enqueue(object : Callback<List<Item>>{
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                _isLoading.value = true
                if(response.isSuccessful){
                    _listFollowers.value = response.body()
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                _isToast.value = true
            }

        })
    }
    fun getFollowing(dataUsername: String) : LiveData<List<Item?>>{
        apiConfig.getFollowing(dataUsername).enqueue(object : Callback<List<Item>>{
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                _isLoading.value = true
                if(response.isSuccessful){
                    _listFollowing.value = response.body()
                    _isLoading.value = false

                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                _isToast.value = true
            }

        })
        return listFollowing
    }
}