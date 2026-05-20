# Cars J2ME Reloaded

A clean-room modernization and preservation project focused on rebuilding a legacy Java ME (J2ME/MIDP) racing game into a modern cross-platform Java 25 game powered by LibGDX.

## Goals

* Preserve the gameplay feel and structure of the original mobile racing game
* Replace obsolete Java ME APIs with modern cross-platform systems
* Rebuild rendering, input, audio, and asset pipelines for desktop/mobile environments
* Document and restore legacy game-engine behavior and binary formats
* Provide a maintainable and extensible modern codebase

## Features

* Modern Gradle multi-project structure
* Java 25 + LibGDX engine architecture
* Cross-platform desktop support
* Modern viewport/camera system
* Reimplemented asset loading pipeline
* Reverse-engineered binary resource parsers
* Keyboard + mouse input abstraction
* Modular renderer and entity systems
* Optional retro/HD remaster support planned

## Preservation Focus

This repository focuses on engine reconstruction, tooling, and modernization.

No proprietary commercial assets are distributed in this repository.

Users must provide their own legally obtained original game files/resources where required.
# Progress Log: Cars Clean Room Remaster

## Phase 1: Reorganization & Setup
- [x] Create project structure (`qc.aeonis.cars`)
- [x] Migrate assets to `remaster/assets/`
- [x] Set up root `build.gradle` and subprojects
- [x] Initialize `PROGRESS.md`

## Phase 2: Asset Pipeline & Engine Core
- [x] Create base `CarsGame` and `DesktopLauncher`
- [x] Create Screen management system (`LoadingScreen`, `GameScreen`)
- [x] Implement native LibGDX asset loading for PNGs
- [x] Implement custom binary parser for `.m2` and `.bin` files
- [x] Implement initial `InputHandler` with Keyboard + Mouse mapping

## Phase 3: Game Logic Extraction
- [x] Reverse-engineer and implement `TrackData` loading
- [x] Reverse-engineer and implement `CarEntity` physics
- [x] Implement `SpriteManager` for frame management
- [ ] Implement `GameRenderer` for track/car drawing
- [ ] Implement Car AI logic

---
*Updated on 2026-05-20*

## Status

Active experimental remaster/re-engineering project.

Current work includes:

* asset migration
* engine core rewrite
* rendering reconstruction
* gameplay logic extraction
* sprite/tilemap restoration
* physics reimplementation

## Tech Stack

* Java 25
* LibGDX
* Gradle
* Modern desktop JVM pipeline

## Project Vision

The goal is not merely to emulate the original J2ME game, but to respectfully modernize and preserve a piece of early mobile gaming history while making it accessible on modern platforms.
