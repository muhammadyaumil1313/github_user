package org.daylab.githubuser.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.daylab.githubuser.UI.FollowersFragment
import org.daylab.githubuser.UI.FollowingFragment

class SectionsPagerAdapter(activity : FragmentActivity?) : FragmentStateAdapter(activity!!) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }

}