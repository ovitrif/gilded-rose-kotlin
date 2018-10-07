package com.gildedrose.item

import com.gildedrose.Item

abstract class BaseItemUpdater : ItemUpdater {

    override fun update(item: Item) {
        item.decrementSellIn()
    }

    private fun Item.decrementSellIn() {
        sellIn -= 1
    }
}
