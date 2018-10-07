package com.gildedrose.item

import com.gildedrose.Item

class ItemUpdaters(
        private val map: HashMap<String, ItemUpdater> = hashMapOf(
                ItemTypes.backstagePasses to BackstagePassesItemUpdater(),
                ItemTypes.sulfuras to ImmutableItemUpdater())) {

    fun updateItem(item: Item) = item.getUpdater().update(item)

    private fun Item.getUpdater(): ItemUpdater {
        return map[this.name] ?: DeterioratingItemUpdater()
    }
}
