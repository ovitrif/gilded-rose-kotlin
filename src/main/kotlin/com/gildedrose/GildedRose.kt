package com.gildedrose

import com.gildedrose.item.ItemUpdaters

class GildedRose(
        var items: Array<Item>,
        private val updaters: ItemUpdaters = ItemUpdaters()) {

    fun updateQuality() {
        items.forEach { item -> updaters.updateItem(item) }
    }

}

