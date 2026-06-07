## [1.2.0]

### Added
- Slime Balls, Slime Blocks, Slime Slings, and Slime Boots are all now dyeable!
    - Dyed Slime Blocks do not stick to each other, allowing for more complex redstone contraptions.
    - Dyed Slime Balls stack together, to avoid inventory bloat. Mixing slime balls will merge the two dyes.
    - Small slimes are dyeable.
- Slime Blocks have been given a more fair crafting recipe that better matches Honey Blocks.

### Changed
- Now built with a Stonecutter multiloader setup rather than a Yumi Commons multiloader setup. This should improve compatibility at the cost of separating the mod's JAR.

### Fixed
- Feeding Mud Frogs from No Man's Land now produces the correct Froglight.
- Subtitle when picking up Slimes and Magma Cubes in buckets.
- Subtitle when throwing Slime Balls.


## [1.1.6]

### Changed
- Buffed bed bounciness to match 26.2-snapshot-8.
- Slime blocks are now tagged as bouncy blocks.

### Fixed
- Fixes compatibility with other mods that add attributes via mixins, such as Artifacts (thanks @unilock!)

## [1.1.5]

### Added
- English (Upside Down) translation.

### Fixed
- Tag translation

## [1.1.4]

### Fixed
- Typo in new data component.

## [1.1.3]

### Added
- Conventional tag for Froglights.
- Translations and descriptions for the mod's tags.

### Changed
- Improved horizontal movement checks to be more mod compatible.
- Slime Sling force multiplier is now a data component rather than a config.

## [1.1.1]

### Added
- Sound effects for slime boot bouncing.

### Fixed
- Slime Slings can no longer be used in the air.

## [1.1.0]

### Added
- Slime Slings, a tool used to launch players in the opposite direction they were facing.

## [1.0.3]

### Changed
- Bouncing in shallow water is no longer possible.

### Fixed
- Item Descriptions support

## [1.0.2]

### Added
- Slime Buckets can now be used to breed Frogs.
- Pistons now cause bouncy entities to bounce.

### Changed
- Slime Balls now melt in water.
- Slime Time is now washed off by water.

## [1.0.1]

### Added
- Slime and Magma Buckets can now be dispensed.
- A new mob effect, Slime Time, obtained from throwing slimeballs at entities.

### Fixed
- Config notices.

## [1.0.0]

### Added
- Slime Boots, which allow players wearing them to bounce around.
- Buckets of Slime and Buckets of Magma Cubes. Buckets of Magma Cubes can be fed to Frogs to recieve Froglights.
- A backport of the `bounciness` attribute from Minecraft 26.2.