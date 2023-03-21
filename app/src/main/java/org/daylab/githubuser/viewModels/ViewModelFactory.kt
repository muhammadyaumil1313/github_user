package org.daylab.githubuser.viewModels

import androidx.lifecycle.*
import org.daylab.githubuser.utils.SettingPreferences

class ViewModelFactory(private val preferences: SettingPreferences)
    : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
          return MainViewModel(preferences) as T
        }
        throw IllegalArgumentException("Unkown viewmodel class ${modelClass.name}")
    }

}