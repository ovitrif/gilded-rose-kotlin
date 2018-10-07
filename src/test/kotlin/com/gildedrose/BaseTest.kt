package com.gildedrose

import com.gildedrose.core.TestFixture
import org.junit.Before
import java.util.*

abstract class BaseTest() {

    protected lateinit var items: List<Item>

    @Before
    fun setUpItems() {
        items = TestFixture.get()
    }

    fun generateRandomNumber() = (0 until 999).shuffled().last()

    fun pickRandomItem(items: List<Item>) = items[items.indices.shuffled().last()]

    fun generateRandomQualityValue() = (0..50).shuffled().last()

    fun GildedRose.advanceTimeBy(days: Int) = repeat(days, { this.updateQuality() })
}
