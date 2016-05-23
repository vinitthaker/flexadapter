package com.github.ajalt.flexadapter.sample

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.github.ajalt.flexadapter.FlexAdapter
import com.github.ajalt.flexadapter.FlexAdapterExtensionItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_picture.view.*
import kotlinx.android.synthetic.main.item_text.view.*

val HORIZONTAL = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
val VERTICAL = ItemTouchHelper.UP or ItemTouchHelper.DOWN
val ALL_DIRS = HORIZONTAL or VERTICAL

class TextItem(var text: String, dragDirs: Int = 0) :
        FlexAdapterExtensionItem(R.layout.item_text, dragDirs = dragDirs, span = 3) {
    override fun bindItemView(itemView: View, position: Int) {
        itemView.text_view.text = text
    }
}

class WidePictureItem(@DrawableRes val imageRes: Int, val swipe: Boolean = false) :
        FlexAdapterExtensionItem(R.layout.item_picture, span = 3) {
    override fun swipeDirs(): Int = if (swipe) HORIZONTAL else 0

    override fun bindItemView(itemView: View, position: Int) {
        itemView.image_view.setImageResource(imageRes)
    }
}

class SquarePictureItem(@DrawableRes val imageRes: Int) :
        FlexAdapterExtensionItem(R.layout.item_picture_square) {
    override fun dragDirs(): Int = ALL_DIRS

    override fun bindItemView(itemView: View, position: Int) {
        itemView.image_view.setImageResource(imageRes)
    }
}


class MainActivity : AppCompatActivity() {
    val adapter = FlexAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 3).apply {
            spanSizeLookup = adapter.spanSizeLookup
        }
        adapter.itemTouchHelper.attachToRecyclerView(recyclerView)


        val header1 = TextItem("This Burt is going for a drive")
        val items = listOf(
                header1,
                WidePictureItem(R.drawable.burt_wide_1, swipe = true),
                TextItem("This Burt is staying right where he is"),
                WidePictureItem(R.drawable.burt_wide_2),
                TextItem("Move these Burts"),
                SquarePictureItem(R.drawable.burt_square_1),
                SquarePictureItem(R.drawable.burt_square_2),
                SquarePictureItem(R.drawable.burt_square_3),
                SquarePictureItem(R.drawable.burt_square_4),
                SquarePictureItem(R.drawable.burt_square_5),
                SquarePictureItem(R.drawable.burt_square_6),
                SquarePictureItem(R.drawable.burt_square_7),
                SquarePictureItem(R.drawable.burt_square_8),
                SquarePictureItem(R.drawable.burt_square_9)
        )

        adapter.addItems(items)
        adapter.itemSwipedListener = {
            if (it == items[1]) {
                header1.text = "Vroom vroom"
                adapter.notifyItemChanged(0)
            }
        }
    }
}