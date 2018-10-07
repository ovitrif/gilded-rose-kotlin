package com.gildedrose.item

import com.gildedrose.BaseTest
import com.gildedrose.Item
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test

class AgedBrieItemUpdaterTest : BaseTest() {

    private lateinit var updater: ItemUpdater

    @Before
    fun setUp() {
        updater = AgedBrieItemUpdater()
    }

    @Test
    fun update_afterAnyNumberOfDays_shouldDecreaseSellInByNumberOfDays() {
        val days = generateRandomNumber()
        val initialSellIn = generateRandomNumber()
        val item = Item("Aged Brie", initialSellIn, generateRandomQualityValue())

        repeat(days, { updater.update(item) })

        assertThat(item.sellIn).isEqualTo(initialSellIn - days)
    }

    @Test
    fun update_afterAnyNumberOfDays_shouldHaveQualityInLimits() {
        (0 until 100).forEach { days ->
            val item = Item("Aged Brie", generateRandomNumber(), generateRandomQualityValue())

            repeat(days, { updater.update(item) })

            assertThat(item.quality).isBetween(0, 50)
        }
    }

    @Test
    fun update_whenSellInGreaterOrEqualToZero_shouldIncreaseQualityByNumberOfDays() {
        (1 until 1000).forEach { days ->
            val initialItemQuality = generateRandomNumber()
            val sellIn = days
            val item = Item("Aged Brie", sellIn, initialItemQuality)

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality + days).coerceAtMost(50)

            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun update_whenSellInNegative_shouldIncreaseQualityByNumberOfDaysMultipliedByTwo() {
        (-100 until 0).forEach { sellIn ->
            val days = -sellIn
            val initialItemQuality = generateRandomQualityValue()
            val item = Item("Aged Brie", sellIn, initialItemQuality)

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality + days * 2).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }
}