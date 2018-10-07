package com.gildedrose.core

import com.gildedrose.Item

fun List<Item>.excludeSulfuras() = this.filter { it.name != "Sulfuras, Hand of Ragnaros" }
