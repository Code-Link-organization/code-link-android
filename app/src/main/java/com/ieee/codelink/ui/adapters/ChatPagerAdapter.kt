package com.ieee.codelink.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ieee.codelink.domain.models.FragmentTab

class ChatPagerAdapter (fragment: Fragment, private val fragmentList: List<FragmentTab>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragmentList.size
    override fun createFragment(position: Int): Fragment = fragmentList[position].fragment
    fun getPositionTitle(position: Int): CharSequence? =fragmentList[position].title
}