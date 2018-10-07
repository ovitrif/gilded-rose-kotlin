package com.gildedrose.item

import com.gildedrose.BaseTest
import com.gildedrose.Item
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

class DeterioratingItemUpdaterTest : BaseTest() {

    private lateinit var updater: ItemUpdater
    private lateinit var item: Item

    @Before
    fun setUp() {
        val randomName = UUID.randomUUID().toString()

        updater = DeterioratingItemUpdater()
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
        (0 until 100).forEach { days ->
            repeat(days, { updater.update(item) })

            assertThat(item.quality).isBetween(0, 50)
        }
    }

    @Test
    fun update_whenSellInIsGreaterThanOrEqualToZero_shouldDegradeQualityByNumberOfDays() {
        (0 until 100).forEach { sellIn ->
            item = item.copy(sellIn =  sellIn)

            val days = generateRandomNumber().coerceAtLeast(0)
            val initialItemQuality = item.quality

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality - days).coerceAtLeast(0)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun update_whenSellInIsNegative_shouldDegradeQualityByTwiceTheNumberOfDays() {
        (-100 until 0).forEach { sellIn ->
            item = item.copy(sellIn = sellIn)

            val days = generateRandomNumber().coerceAtLeast(0)
            val initialItemQuality = item.quality

            repeat(days, { updater.update(item) })

            val expectedQuality = (initialItemQuality - days * 2).coerceAtLeast(0)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }
}
