package com.emanh.mixivivu.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.emanh.mixivivu.databinding.ActivityIntroBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class IntroActivity : BaseActivity() {
    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@IntroActivity, MainActivity::class.java))
            finish()
        }
    }
}