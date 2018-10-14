package com.gildedrose.item

import com.gildedrose.BaseTest
import com.gildedrose.Item
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test

class AgedBrieItemUpdaterTest : BaseTest() {

    private lateinit var updater: ItemUpdater
    private lateinit var item: Item

    @Before
    fun setUp() {
        updater = AgedBrieItemUpdater()
        item = Item("Aged Brie", generateRandomNumber(), generateRandomQualityValue())
    }

    @Test
    fun update_afterAnyNumberOfDays_shouldDecreaseSellInByNumberOfDays() {
        val days = generateRandomNumber()
        val initialSellIn = generateRandomNumber()
        item = item.copy(sellIn = initialSellIn)

        repeat(days, { updater.update(item) })

        assertThat(item.sellIn).isEqualTo(initialSellIn - days)
    }

    @Test
    fun update_afterAnyNumberOfDays_shouldHaveQualityInLimits() {
        for (days in 0 until 100) {
            repeat(days, { updater.update(item) })

            assertThat(item.quality).isBetween(0, 50)
        }
    }

    @Test
    fun update_whenSellInGreaterOrEqualToZero_shouldIncreaseQualityByNumberOfDays() {
        for (days in 1 until 1000) {
            val initialItemQuality = generateRandomNumber()
            item = item.copy(sellIn = days, quality = initialItemQuality)

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality + days).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun update_whenSellInNegative_shouldIncreaseQualityByNumberOfDaysMultipliedByTwo() {
        for (sellIn in -100 until 0) {
            val days = -sellIn
            val initialItemQuality = generateRandomQualityValue()
            item = item.copy(sellIn = sellIn, quality = initialItemQuality)

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality + days * 2).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }
}