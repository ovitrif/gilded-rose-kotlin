package com.gildedrose.item

import com.gildedrose.BaseTest
import com.gildedrose.Item
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test

class BackstagePassesItemUpdaterTest : BaseTest() {

    private lateinit var updater: ItemUpdater

    @Before
    fun setUp() {
        updater = BackstagePassesItemUpdater()
    }

    @Test
    fun update_afterAnyNumberOfDays_shouldDecreaseSellInByNumberOfDays() {
        val days = generateRandomNumber()
        val initialSellIn = generateRandomNumber()
        val item = Item("Backstage passes to a TAFKAL80ETC concert", initialSellIn, generateRandomQualityValue())

        repeat(days, { updater.update(item) })

        assertThat(item.sellIn).isEqualTo(initialSellIn - days)
    }

    @Test
    fun update_afterAnyNumberOfDays_shouldHaveQualityInLimits() {
        (0 until 100).forEach { days ->
            val item = Item("Backstage passes to a TAFKAL80ETC concert", generateRandomNumber(), generateRandomQualityValue())

            repeat(days, { updater.update(item) })

            assertThat(item.quality).isBetween(0, 50)
        }
    }

    @Test
    fun update_whenSellInNegativeOrZero_shouldSetQualityToZero() {
        (-100 until 0).forEach { sellIn ->
            val days = generateRandomNumber()
            val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, generateRandomQualityValue())

            repeat(days, { updater.update(item) })

            assertThat(item.quality).isZero()
        }
    }

    @Test
    fun update_whenSellInMoreThanZeroAndLessThanSix_shouldIncreaseQualityByNumberOfDaysMultipliedByThree() {
        (1 until 6).shuffled().forEach { sellIn ->
            val days = sellIn
            val initialItemQuality = generateRandomQualityValue()
            val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialItemQuality)

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality + days * 3).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun update_whenSellInMoreThanFiveAndLessThanEleven_shouldIncreaseQualityByNumberOfDaysMultipliedByTwo() {
        (6 until 11).shuffled().forEach {
            val sellIn = it
            val days = sellIn - 6
            val initialItemQuality = generateRandomQualityValue()
            val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialItemQuality)

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality + days * 2).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun update_whenSellInMoreThanEleven_shouldIncreaseQualityByNumberOfDays() {
        (11 until 1000).shuffled().forEach {
            val sellIn = it
            val days = sellIn - 11
            val initialItemQuality = generateRandomQualityValue()
            val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialItemQuality)

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality + days).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }
}