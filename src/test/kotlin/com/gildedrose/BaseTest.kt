package com.gildedrose

import com.gildedrose.core.TestFixture
import org.junit.Before
import java.util.*

abstract class BaseTest {

    protected lateinit var items: List<Item>

    @Before
    fun setUpItems() {
        items = TestFixture.get()
    }

    fun IntRange.randomize() = this.shuffled().last()

    fun generateRandomNumber() = (0 until 999).randomize()

    fun pickRandomItem(items: List<Item>) = items[items.indices.randomize()]

    fun generateRandomQualityValue() = (0..50).randomize()

    fun GildedRose.advanceTimeBy(days: Int) = repeat(days, { this.updateQuality() })
}
