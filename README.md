# Gilded Rose Refactoring Kata
[![Build Status](https://img.shields.io/travis/com/ovitrif/gilded-rose-kotlin/master.svg)](https://travis-ci.com/ovitrif/gilded-rose-kotlin)
[![Coverage](https://img.shields.io/codecov/c/github/ovitrif/gilded-rose-kotlin/master.svg)](https://codecov.io/gh/ovitrif/gilded-rose-kotlin)
[![Code Quality](https://api.codacy.com/project/badge/Grade/eed75167e5614581905f4a1e161ace65)](https://www.codacy.com/app/ovitrif/gilded-rose-kotlin)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)][license-url]

My shot at the GildedRose refactoring Kata, created by [@TerryHughes](https://twitter.com/) and ported to
Kotlin by [@emilybache](https://twitter.com/emilybache).
Original requirements:
[GildedRose-Refactoring-Kata](https://github.com/emilybache/GildedRose-Refactoring-Kata) repository.

## Build
- Open project with [IntelliJ IDEA Community edition](https://www.jetbrains.com/idea/download/)
- Navigate using the Project viewer to `src > test > kotlin`
- Right click on **"🗂 kotlin"** and pick **"▶ Run all tests"**

## Limitations
Since it is specified as a project requirement to keep the Item class unchanged,
it could be considered that the `ItemUpdaters` class violates the OCP principle.

Lifting this specification would allow to move the updating logic in the typed items
which would implement an `Updatable` interface, and thanks to polymorphism this
would further simplify the program and add to its readability and maintainability.

## License
Distributed under the MIT license. See [LICENSE][license-url] for details.

[license-url]: https://github.com/ovitrif/gilded-rose-kotlin/blob/master/LICENSE
