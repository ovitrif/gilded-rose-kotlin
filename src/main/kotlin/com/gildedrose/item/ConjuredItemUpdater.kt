package com.gildedrose.item

import com.gildedrose.Item

class ConjuredItemUpdater : DeterioratingItemUpdater() {

    override fun update(item: Item) {
        super.update(item)
        item.updateQuality()
    }
}
