package org.daylab.githubuser.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.daylab.githubuser.utils.SettingPreferences

class MainViewModel(private val preferences: SettingPreferences) : ViewModel() {
    fun getThemeSetting() : LiveData<Boolean> = preferences.getThemeSetting().asLiveData()
    fun saveThemeSetting(isDarkModeActive : Boolean) =
        viewModelScope.launch { preferences.saveThemeSetting(isDarkModeActive) }
}