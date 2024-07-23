@file:Suppress("DEPRECATION")

package com.emanh.mixivivu.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.emanh.mixivivu.R
import com.emanh.mixivivu.adapter.PicListAdapter
import com.emanh.mixivivu.databinding.ActivityDetailShipBinding
import com.emanh.mixivivu.databinding.EditInfoShipBinding
import com.emanh.mixivivu.fragment.CharacteristicFragment
import com.emanh.mixivivu.fragment.EvaluationFragment
import com.emanh.mixivivu.fragment.IntroFragment
import com.emanh.mixivivu.fragment.PriceRoomFragment
import com.emanh.mixivivu.model.InfoShipModel
import com.emanh.mixivivu.model.ShipModel
import com.emanh.mixivivu.viewModel.ReviewsViewModel
import java.text.NumberFormat
import java.util.Locale

class DetailShipActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailShipBinding
    private lateinit var ship: ShipModel
    private lateinit var reviewsViewModel: ReviewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailShipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initListPic()
        initDetailShip()
    }

    private fun init() {
        ship = intent.getParcelableExtra("objectShip")!!
        reviewsViewModel = ViewModelProvider(this)[ReviewsViewModel::class.java]
        reviewsViewModel.loadReviews(ship.id.toString())

        reviewsViewModel.reviewsSummary.observe(this) {
            updateReviewInfo(it)
        }

        binding.buttonBack.setOnClickListener { finish() }

        val buttons = mapOf(
            binding.characteristic to CharacteristicFragment(),
            binding.priceRoom to PriceRoomFragment(),
            binding.intro to IntroFragment(),
            binding.evaluation to EvaluationFragment()
        )

        buttons.forEach { (button, fragment) ->
            button.setOnClickListener {
                replaceFragment(fragment)
                updateButtonBackgrounds(button)
            }
        }
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
        binding.price.text = "${formatPrice(ship.price)}đ/ khách"

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

    private fun updateButtonBackgrounds(selectedButton: View) {
        val buttons = listOf(
            binding.characteristic,
            binding.priceRoom,
            binding.intro,
            binding.evaluation
        )

        buttons.forEach { button ->
            val backgroundRes = if (button == selectedButton) {
                R.drawable.bg_button_list1
            } else {
                R.drawable.bg_button_list2
            }
            button.setBackgroundResource(backgroundRes)
        }
    }

    private fun setupInfoShip(itemBinding: EditInfoShipBinding, iconRes: Int, textTitle: String, idInfoShip: Int) {
        itemBinding.apply {
            icon.setImageResource(iconRes)
            title.text = textTitle
            label.text = ship.infoShip[idInfoShip]
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayoutDetail, fragment)
        fragmentTransaction.commit()
    }

    @SuppressLint("SetTextI18n")
    private fun updateReviewInfo(summary: ReviewsViewModel.ReviewsSummary) {
        val formattedRating = String.format("%.1f", summary.averageRating)
        binding.infoEvaluate.text = "$formattedRating (${summary.count} đánh giá)"
    }

    private fun formatPrice(price: Int): String {
        val numberFormat = NumberFormat.getNumberInstance(Locale.US)
        return numberFormat.format(price)
    }
}