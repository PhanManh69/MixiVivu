package com.emanh.mixivivu.model

import com.emanh.mixivivu.databinding.EditButtonBarItemBinding

data class ButtonBarItemModel(
    val binding: EditButtonBarItemBinding,
    val iconRes: Int,
    val text: String,
    val position: Int
)
