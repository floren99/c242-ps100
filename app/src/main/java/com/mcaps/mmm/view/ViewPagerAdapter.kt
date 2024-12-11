package com.mcaps.mmm.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mcaps.mmm.view.dashboard.test.TestFragment
import com.mcaps.mmm.view.dashboard.home.HomeFragment
import com.mcaps.mmm.view.dashboard.path.PathFragment
import com.mcaps.mmm.view.dashboard.discussion.DiscussionFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> TestFragment()
            2 -> PathFragment()
            3 -> DiscussionFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
