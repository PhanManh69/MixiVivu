package com.emanh.mixivivu.activity

import android.content.Intent
import android.os.Bundle
import com.emanh.mixivivu.databinding.ActivityWarningBinding

class WarningActivity : BaseActivity() {
    private lateinit var binding: ActivityWarningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.start.setOnClickListener {
            startActivity(Intent(this@WarningActivity, MainActivity::class.java))
        }
    }
}