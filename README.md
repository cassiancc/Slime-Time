# Slime Time

<a href='https://modrinth.com/mod/slime-time/versions?l=fabric'><img alt="fabric" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/supported/fabric_vector.svg"></a>
<a href='https://modrinth.com/mod/slime-time/versions?l=neoforge&l=forge'><img alt="forge" height="56" src="https://raw.githubusercontent.com/cassiancc/Cassians-Badges/refs/heads/main/cozy/NeoForge.svg"></a>
<a href='https://modfest.net/26'><img alt="Made for ModFest: 26" height="56" src="https://raw.githubusercontent.com/cassiancc/Cassians-Badges/refs/heads/main/cozy/ModFest-26.svg"></a>

An expansion on Slime, providing tools like Slime Boots, throwable Slime Balls, Slime Buckets, dyeable Slime Blocks and more.

## Installation

Slime Time is a client and server mod for 26.1. Its dependencies are listed below.

## Features
- Slimes now generate in a variety of common colours.
  - Colourful Slimes drop coloured Slime Balls.
- Slime Balls can be thrown, and now come in a variety of colours.
  - Coloured Slime Balls stack together, to avoid inventory bloat. Mixing slime balls will merge the two dyes.
  - Throwing a Slime Ball at an entity will inflict it with **Slime Time**, a new effect that causes afflicted targets to bounce around.
- Slime Blocks now come in a variety of colours.
  - Coloured Slime Blocks do not stick to each other, allowing for more complex redstone contraptions.
- **Slime Boots**, which allow players wearing them to bounce around. Dyeable.
- **Slime Slings**, a tool used to launch players in the opposite direction they were facing. Dyeable.
- **Buckets of Slime**, which can be used to breed Frogs.
  - Slimes caught in buckets will preserve their colour, and the item will show the correct colour.
- **Buckets of Magma Cubes**, which can be fed to Frogs to receive Froglights.

<details>
<summary>Technical</summary>

- The force multiplier on a Slime Sling is component-driven, allowing it to be scaled with mods like [Default Components](https://modrinth.com/mod/default-components) using the following format: `[slime_time:force={horizontal_force:1,vertical_force:1}]`
- On 26.1 and below, the `bounciness` attribute from Minecraft 26.2 was backported.
- Froglight feeding has direct integration with Instant Feedback and No Man's Land. Other mods will have to be added manually.


</details>

### Dependencies
- [Fabric API](https://modrinth.com/mod/fabric-api) is required on Fabric.
- [McQoy](https://modrinth.com/mod/mcqoy) is recommended to configure the mod.
- [Reliable Recipe Viewer](https://modrinth.com/mod/rrv) or [EMI](https://modrinth.com/mod/emi) is recommended to see recipes.
- [Modefite](https://modrinth.com/mod/modefite-item-definition-backport) is required on 1.21.1.

## FAQ

- Will this mod be ported to other versions/loaders?
  - My focus is primarily on 26.1 at this time, though a 1.21.1 backport was made to allow the mod a wider audience. I do not plan to backport the mod further than 1.21.1, as the mod depends on 1.21+ exclusive features.

## License
[![Code license (MIT)](https://img.shields.io/badge/code%20license-MIT-green.svg?style=flat-square)](https://github.com/cassiancc/slime-time)

Slime Time is distributed under the open-source MIT license. Note that this only applies to the code.

Slime textures were designed by [ProbablyEkho](https://modrinth.com/user/ProbablyEkho).