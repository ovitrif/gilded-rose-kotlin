package com.gildedrose.item

import com.gildedrose.BaseTest
import com.gildedrose.Item
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

class ConjuredItemUpdaterTest : BaseTest() {

    private lateinit var updater: ItemUpdater
    private lateinit var item: Item

    @Before
    fun setUp() {
        val randomName = UUID.randomUUID().toString()

        updater = ConjuredItemUpdater()
        item = Item(randomName, generateRandomNumber(), generateRandomQualityValue())
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
    fun update_whenSellInIsGreaterThanOrEqualToZero_shouldDegradeQualityByTwiceTheNumberOfDays() {
        for (sellIn in 1 until 100) {
            item = item.copy(sellIn = sellIn)

            val days = generateRandomQualityValue() * 2
            val initialItemQuality = item.quality

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality - days * 2).coerceAtLeast(0)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun update_whenSellInIsNegative_shouldDegradeQualityByFourTimesTheNumberOfDays() {
        for (sellIn in -100 until 0) {
            item = Item("Some Conjured Item", sellIn, generateRandomQualityValue())

            val days = generateRandomNumber()
            val initialItemQuality = item.quality

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality - days * 4).coerceIn(0..50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }
}
