package com.gildedrose.acceptance

import com.gildedrose.GildedRose
import com.gildedrose.Item
import com.gildedrose.core.TestFixture
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import java.io.File

class AcceptanceTests {

    @Test
    fun withInitialTextFixture_whenRunForThirtyDays_itShouldHaveSameOutputAsLegacyCode() {
        val initialTextFixture = TestFixture.get().toTypedArray()

        val output = runWithItemsForDays(initialTextFixture, 30)

        assertThat(output).isEqualTo(getExpectedOutput())
    }

    private fun runWithItemsForDays(items: Array<Item>, days: Int): String {
        var output = ""

        val app = GildedRose(items)

        for (i in 0 until days) {
            output += "\nDAY $i\n"

            items.forEach {
                val newLine = if (it == items.last()) "" else "\n"
                output += "$it$newLine"
            }

            app.updateQuality()
        }

        return output
    }

    private fun getExpectedOutput(): String {
        val filePath = "spec/ThirtyDaysOutput.txt"
        return File(filePath).bufferedReader().readLines().joinToString(separator = "\n")
    }
}