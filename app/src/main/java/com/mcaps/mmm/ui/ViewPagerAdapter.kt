package com.mcaps.mmm.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mcaps.mmm.ui.dashboard.DashboardFragment
import com.mcaps.mmm.ui.home.HomeFragment
import com.mcaps.mmm.ui.notifications.NotificationsFragment
import com.mcaps.mmm.ui.menu.MenuFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 4 // Number of fragments

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> DashboardFragment()
            2 -> NotificationsFragment()
            3 -> MenuFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
