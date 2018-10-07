# Gilded Rose Refactoring Kata

## Build
- Open project with [IntelliJ IDEA Community edition](https://www.jetbrains.com/idea/download/)
- Navigate using the Project viewer to `src > test > kotlin` 
- Right click on 'kotlin' and pick 'Run all tests'

## Limitations
- Since it is specified as a project requirement to keep the Item class unchanged,
  it could be considered that the `ItemUpdaters` class violates the OPC principle.
  
  Lifting this specification would allow to move the updating logic in the typed items
  which would implement an Updatable interface, and thanks to polimorphism this
  would further simplify the program and add to its readability and maintainability.
