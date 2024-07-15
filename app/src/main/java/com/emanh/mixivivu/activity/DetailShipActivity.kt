@file:Suppress("DEPRECATION")

package com.emanh.mixivivu.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.emanh.mixivivu.R
import com.emanh.mixivivu.adapter.PicListAdapter
import com.emanh.mixivivu.databinding.ActivityDetailShipBinding
import com.emanh.mixivivu.databinding.EditInfoShipBinding
import com.emanh.mixivivu.fragment.BlogFragment
import com.emanh.mixivivu.fragment.EnterpriseFragment
import com.emanh.mixivivu.fragment.PlaneFragment
import com.emanh.mixivivu.fragment.ShipFragment
import com.emanh.mixivivu.model.InfoShipModel
import com.emanh.mixivivu.model.ShipModel

class DetailShipActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailShipBinding
    private lateinit var ship: ShipModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailShipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initListPic()
        initDetailShip()
    }

    private fun init() {
        ship = intent.getParcelableExtra("object")!!

        binding.buttonBack.setOnClickListener { finish() }
    }

    private fun initListPic() {
        val colorList =  ArrayList<String>()

        for (imageUrl in ship.picUrl) {
            colorList.add(imageUrl)
        }

        Glide.with(this)
            .load(colorList[0])
            .into(binding.picMain)

        binding.listPic.adapter = PicListAdapter(colorList, binding.picMain)
        binding.listPic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    @SuppressLint("SetTextI18n")
    private fun initDetailShip() {
        binding.title.text = "Du thuyền ${ship.title}"
        binding.price.text = "${ship.price}đ/ khách"
        binding.infoEvaluate.text = "4.5 (2 đánh giá)"

        val infoShipItem = listOf(
            InfoShipModel(binding.launching, R.drawable.anchor, "Hạ thủy", 0),
            InfoShipModel(binding.cabin, R.drawable.bed, "Cabin", 1),
            InfoShipModel(binding.body, R.drawable.ship, "Thân vỏ", 2),
            InfoShipModel(binding.trip, R.drawable.marker, "Hành trình", 3),
            InfoShipModel(binding.operating, R.drawable.briefcase, "Điều hành", 4)
        )

        infoShipItem.forEachIndexed { _, item ->
            setupInfoShip(item.binding, item.iconRes, item.title, item.label)
        }

    }

    private fun setupInfoShip(itemBinding: EditInfoShipBinding, iconRes: Int, textTitle: String, idInfoShip: Int) {
        itemBinding.apply {
            icon.setImageResource(iconRes)
            title.text = textTitle
            label.text = ship.infoShip[idInfoShip]
        }
    }
}