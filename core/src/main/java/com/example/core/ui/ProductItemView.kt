package com.example.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.core.R

class ProductItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val nameView: TextView
    private val buyView: View
    private val counterView: View
    val plusView: ImageView
    private val countView: TextView
    val minusView: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.product_item_view, this, true)
        nameView = findViewById(R.id.product_item_view_name)
        buyView = findViewById(R.id.product_item_view_buy)
        counterView = findViewById(R.id.product_item_view_counter)
        plusView = findViewById(R.id.product_item_view_plus)
        countView = findViewById(R.id.product_item_view_count)
        minusView = findViewById(R.id.product_item_view_minus)
    }

    fun bind(state: ProductItemState) {
        nameView.text = state.name
        countView.text = state.cartCount.toString()
    }
}