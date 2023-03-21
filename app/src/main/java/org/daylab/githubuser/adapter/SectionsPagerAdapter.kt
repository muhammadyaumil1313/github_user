package org.daylab.githubuser.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.daylab.githubuser.UI.FollowersFragment
import org.daylab.githubuser.UI.FollowingFragment

class SectionsPagerAdapter(fragmentManager: FragmentManager, fragment: Fragment)
    : FragmentStateAdapter(fragmentManager, fragment.lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowersFragment()
        }
        return fragment as Fragment
    }

}