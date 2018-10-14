package com.gildedrose

import com.gildedrose.core.excludeAgedBrie
import com.gildedrose.core.excludeBackstagePasses
import com.gildedrose.core.excludeSulfuras
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test

class QualityTest : BaseTest() {

    @Test
    fun givenSulfuras_afterAnyNumberOfDays_shouldHaveQualityEighty() {
        val days = generateRandomNumber()
        val item = Item("Sulfuras, Hand of Ragnaros", generateRandomNumber(), 80)
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        assertThat(item.quality).isEqualTo(80)
    }

    @Test
    fun givenAnyItemExceptSulfuras_afterAnyNumberOfDays_shouldHaveQualityGreaterThanOrEqualToZero() {
        items = items.excludeSulfuras()
        val days = generateRandomNumber()
        val app = GildedRose(items.toTypedArray())

        app.advanceTimeBy(days)

        items.forEach { item ->
            assertThat(item.quality).isGreaterThanOrEqualTo(0)
        }
    }

    @Test
    fun givenAnyItemExceptSulfuras_afterAnyNumberOfDays_shouldHaveQualityLessThanOrEqualToFifty() {
        items = items.excludeSulfuras()
        val days = generateRandomNumber()
        val app = GildedRose(items.toTypedArray())

        app.advanceTimeBy(days)

        items.forEach { item ->
            assertThat(item.quality).isLessThanOrEqualTo(50)
        }
    }

    @Test
    fun givenAnyDegradableItem_whenSellInIsGreaterThanOrEqualToZero_shouldDegradeQualityByNumberOfDays() {
        items = items.excludeSulfuras()
                .excludeBackstagePasses()
                .excludeAgedBrie()
        val days = generateRandomNumber().coerceAtLeast(0)
        val item = pickRandomItem(items)
        val initialItemQuality = item.quality
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        val expectedQuality = (initialItemQuality - days).coerceAtLeast(0)
        assertThat(item.quality).isEqualTo(expectedQuality)
    }

    @Test
    fun givenAnyDegradableItem_whenSellInIsLessThanZero_shouldDegradeQualityByTwiceTheNumberOfDays() {
        items = items.excludeSulfuras()
                .excludeBackstagePasses()
                .excludeAgedBrie()
        val sellIn = (-generateRandomNumber() until 0).randomize()
        val days = (0 until 100).randomize()
        val item = pickRandomItem(items).copy(sellIn = sellIn)
        val initialItemQuality = item.quality
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        val expectedQuality = (initialItemQuality - days * 2).coerceAtLeast(0)
        assertThat(item.quality).isEqualTo(expectedQuality)
    }

    @Test
    fun givenAgedBrie_whenSellInGreaterOrEqualToZero_shouldIncreaseQualityByNumberOfDays() {
        for (sellIn in 0 until 1000) {
            val days = (0..sellIn).randomize()
            val initialItemQuality = generateRandomQualityValue()
            val item = Item("Aged Brie", sellIn, initialItemQuality)
            val app = GildedRose(arrayOf(item))

            app.advanceTimeBy(days)

            val expectedQuality = (initialItemQuality + days).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun givenAgedBrie_whenSellInNegative_shouldIncreaseQualityByNumberOfDaysMultipliedByTwo() {
        for (sellIn in -100 until 0) {
            val days = (0..-sellIn).randomize()
            val initialItemQuality = generateRandomQualityValue()
            val item = Item("Aged Brie", sellIn, initialItemQuality)
            val app = GildedRose(arrayOf(item))

            app.advanceTimeBy(days)

            val expectedQuality = (initialItemQuality + days * 2).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun givenBackstagePasses_whenSellInNegativeOrZero_shouldSetQualityToZero() {
        val days = generateRandomNumber()
        val sellIn = (-generateRandomNumber()..0).randomize()
        val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, generateRandomQualityValue())
        val app = GildedRose(arrayOf(item))

        app.advanceTimeBy(days)

        assertThat(item.quality).isZero()
    }

    @Test
    fun givenBackstagePasses_whenSellInMoreThanZeroAndLessThanSix_shouldIncreaseQualityByNumberOfDaysMultipliedByThree() {
        for (sellIn in 1 until 6) {
            val days = (0..sellIn).randomize()
            val initialItemQuality = generateRandomQualityValue()
            val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialItemQuality)
            val app = GildedRose(arrayOf(item))

            app.advanceTimeBy(days)

            val expectedQuality = (initialItemQuality + days * 3).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun givenBackstagePasses_whenSellInMoreThanFiveAndLessThanEleven_shouldIncreaseQualityByNumberOfDaysMultipliedByTwo() {
        for (sellIn in 6 until 11) {
            val days = (0..sellIn - 6).randomize()
            val initialItemQuality = generateRandomQualityValue()
            val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialItemQuality)
            val app = GildedRose(arrayOf(item))

            app.advanceTimeBy(days)

            val expectedQuality = (initialItemQuality + days * 2).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }

    @Test
    fun givenBackstagePasses_whenSellInMoreThanEleven_shouldIncreaseQualityByNumberOfDays() {
        for (sellIn in 11 until 999) {
            val days = (0..sellIn - 11).randomize()
            val initialItemQuality = generateRandomQualityValue()
            val item = Item("Backstage passes to a TAFKAL80ETC concert", sellIn, initialItemQuality)
            val app = GildedRose(arrayOf(item))

            app.advanceTimeBy(days)

            val expectedQuality = (initialItemQuality + days).coerceAtMost(50)
            assertThat(item.quality).isEqualTo(expectedQuality)
        }
    }
}
