package com.emanh.mixivivu.model

import android.widget.ArrayAdapter
import com.emanh.mixivivu.databinding.EditInputSpinnerBinding

data class InputSpinnerItem(
    val binding: EditInputSpinnerBinding,
    val adapter: ArrayAdapter<String>,
    val initialSelection: String?
)
