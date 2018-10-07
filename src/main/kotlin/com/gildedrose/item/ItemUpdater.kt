package com.gildedrose.item

import com.gildedrose.Item

interface ItemUpdater {

    fun update(item: Item) = Unit

    companion object {
        val QUALITY_RANGE = 0..50
    }
}
