package com.gildedrose.item

import com.gildedrose.Item

class ItemUpdaters(
        private val map: HashMap<String, ItemUpdater> = hashMapOf()) {

    fun updateItem(item: Item) = item.getUpdater().update(item)

    private fun Item.getUpdater(): ItemUpdater {
        return map[this.name] ?: ItemUpdater.NULL
    }
}
