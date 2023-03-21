package org.daylab.githubuser.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.daylab.githubuser.R
import org.daylab.githubuser.adapter.SectionsPagerAdapter
import org.daylab.githubuser.databinding.FragmentDetailUsersBinding
import org.daylab.githubuser.utils.SettingPreferences

class MainActivity : AppCompatActivity() {
    companion object{
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}