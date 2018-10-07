package com.gildedrose.item

import com.gildedrose.Item
import com.gildedrose.item.ItemUpdater.Companion.QUALITY_RANGE

class BackstagePassesItemUpdater : BaseItemUpdater() {

    override fun update(item: Item) {
        super.update(item)
        item.updateQuality()
    }

    private fun Item.updateQuality() {
        when {
            sellIn < 0 -> quality = 0
            sellIn < 5 -> quality += 3
            sellIn < 10 -> quality += 2
            quality in QUALITY_RANGE -> quality += 1
        }
        quality = quality.coerceIn(QUALITY_RANGE)
    }
}