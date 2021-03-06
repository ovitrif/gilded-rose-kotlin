package com.gildedrose.item

import com.gildedrose.BaseTest
import com.gildedrose.Item
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BackstagePassesItemUpdaterTest : BaseTest() {

    private lateinit var updater: ItemUpdater
    private lateinit var item: Item

    @Before
    fun setUp() {
        updater = BackstagePassesItemUpdater()
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
    fun update_whenSellInNegativeOrZero_shouldSetQualityToZero() {
        for (sellIn in -100 until 0) {
            val days = generateRandomNumber() + 1
            item = item.copy(sellIn = sellIn)

            repeat(days, { updater.update(item) })

            assertThat(item.quality).isZero()
        }
    }

    @Test
    fun update_whenSellInMoreThanZeroAndLessThanSix_shouldIncreaseQualityByNumberOfDaysMultipliedByThree() {
        for (sellIn in 1 until 6) {
            val days = sellIn
            val initialItemQuality = generateRandomQualityValue()
            item = item.copy(sellIn = sellIn, quality = initialItemQuality)

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality + days * 3).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun update_whenSellInMoreThanFiveAndLessThanEleven_shouldIncreaseQualityByNumberOfDaysMultipliedByTwo() {
        for (sellIn in 6 until 11) {
            val days = (0..sellIn - 6).randomize()
            val initialItemQuality = generateRandomQualityValue()
            item = item.copy(sellIn = sellIn, quality = initialItemQuality)

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality + days * 2).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun update_whenSellInMoreThanEleven_shouldIncreaseQualityByNumberOfDays() {
        for (sellIn in 11 until 1000) {
            val days = (0..sellIn - 11).randomize()
            val initialItemQuality = generateRandomQualityValue()
            item = item.copy(sellIn = sellIn, quality = initialItemQuality)

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality + days).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }
}