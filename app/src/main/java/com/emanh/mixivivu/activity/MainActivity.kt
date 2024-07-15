package com.emanh.mixivivu.activity

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.emanh.mixivivu.R
import com.emanh.mixivivu.databinding.ActivityMainBinding
import com.emanh.mixivivu.databinding.EditButtonBarItemBinding
import com.emanh.mixivivu.fragment.BlogFragment
import com.emanh.mixivivu.fragment.EnterpriseFragment
import com.emanh.mixivivu.fragment.PlaneFragment
import com.emanh.mixivivu.fragment.ShipFragment
import com.emanh.mixivivu.model.ButtonBarItemModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initButtonMenuBar()
    }

    private fun initButtonMenuBar() {
        val buttonBarItems = listOf(
            ButtonBarItemModel(binding.buttonShip, R.drawable.ship, "Du Thuyền", 0),
            ButtonBarItemModel(binding.buttonPlane, R.drawable.plane, "Vé máy bay", 1),
            ButtonBarItemModel(binding.buttonBlog, R.drawable.blog, "Blog", 2),
            ButtonBarItemModel(binding.buttonEnterprise, R.drawable.enterprise, "Giới thiệu", 3)
        )

        var selectedPosition = 0

        buttonBarItems.forEachIndexed { index, item ->
            setupButtonBarItem(item.binding, item.iconRes, item.text, selectedPosition)
            replaceFragment(ShipFragment())
            item.binding.root.setOnClickListener {
                selectedPosition = index
                buttonBarItems.forEach { otherItem ->
                    setupButtonBarItem(otherItem.binding, otherItem.iconRes, otherItem.text, selectedPosition)
                }

                when (index) {
                    0 -> replaceFragment(ShipFragment())
                    1 -> replaceFragment(PlaneFragment())
                    2 -> replaceFragment(BlogFragment())
                    3 -> replaceFragment(EnterpriseFragment())
                }
            }
        }
    }

    private fun setupButtonBarItem(itemBinding: EditButtonBarItemBinding, iconRes: Int, text: String, selectedPosition: Int) {
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

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayoutMain, fragment)
        fragmentTransaction.commit()
    }
}