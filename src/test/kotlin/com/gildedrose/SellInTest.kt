package com.gildedrose

import com.gildedrose.core.TestFixture
import com.gildedrose.core.excludeSulfuras
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test

class SellInTest : BaseTest() {

    @Before
    fun setUp() {
        items = TestFixture.get()
    }

    @Test
    fun givenAnyItemExceptSulfuras_afterAnyNumberOfDays_shouldDecreaseSellInByNumberOfDays() {
        items = items.excludeSulfuras()
        val days = generateRandomNumber()
        val initialSellIn = generateRandomNumber()
        val item = pickRandomItem(items).copy(sellIn = initialSellIn)
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        assertThat(item.sellIn).isEqualTo(initialSellIn - days)
    }

    @Test
    fun givenSulfuras_afterAnyNumberOfDays_shouldHaveSameSellIn() {
        val days = generateRandomNumber()
        val initialSellIn = generateRandomNumber()
        val item = Item("Sulfuras, Hand of Ragnaros", initialSellIn, generateRandomQualityValue())
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        assertThat(item.sellIn).isEqualTo(initialSellIn)
    }
}
