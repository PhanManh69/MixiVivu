package com.emanh.mixivivu.activity

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.emanh.mixivivu.R
import com.emanh.mixivivu.databinding.ActivityMainBinding
import com.emanh.mixivivu.databinding.ButtonBarItemBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val bottomBarItems = listOf(
            BottomBarItem(binding.buttonShip, R.drawable.ship, "Du Thuyền", 0),
            BottomBarItem(binding.buttonPlane, R.drawable.plane, "Vé máy bay", 1),
            BottomBarItem(binding.buttonBlog, R.drawable.blog, "Blog", 2),
            BottomBarItem(binding.buttonEnterprise, R.drawable.enterprise, "Giới thiệu", 3)
        )

        var selectedPosition = 0

        bottomBarItems.forEachIndexed { index, item ->
            setupButtonBarItem(item.binding, item.iconRes, item.text, selectedPosition)
            item.binding.root.setOnClickListener {
                selectedPosition = index
                bottomBarItems.forEach { otherItem ->
                    setupButtonBarItem(otherItem.binding, otherItem.iconRes, otherItem.text, selectedPosition)
                }
            }
        }
    }

    private fun setupButtonBarItem(itemBinding: ButtonBarItemBinding, iconRes: Int, text: String, selectedPosition: Int) {
        val position = when (itemBinding) {
            binding.buttonShip -> 0
            binding.buttonPlane -> 1
            binding.buttonBlog -> 2
            binding.buttonEnterprise -> 3
            else -> -1
        }

        val isSelected = position == selectedPosition
        val colors = intArrayOf(
            ContextCompat.getColor(this, R.color.grey),
            ContextCompat.getColor(this, R.color.darkBlue)
        )

        itemBinding.apply {
            icon.setImageResource(iconRes)
            icon.setColorFilter(colors[if (isSelected) 1 else 0])
            title.text = text
            title.setTextColor(colors[if (isSelected) 1 else 0])
            indicator.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE
        }
    }

    private data class BottomBarItem(
        val binding: ButtonBarItemBinding,
        val iconRes: Int,
        val text: String,
        val position: Int
    )
}