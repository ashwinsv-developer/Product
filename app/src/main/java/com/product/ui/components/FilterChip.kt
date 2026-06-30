package com.product.ui.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ChipColor = Color(0xFF6C3BFF)

@Composable
fun CategoryChip(
    category: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(category)
        },
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = ChipColor,
            selectedBorderColor = ChipColor
        ),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = ChipColor,
            selectedLabelColor = Color.White
        )
    )
}