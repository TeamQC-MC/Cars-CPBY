# Progress Log: Cars Clean Room Remaster

## Memory Dump & Tech Intel (Important for Future)

### Binary Formats
- **`.m2` (Track Data)**: Custom container. Part 0: Width (byte). Part 1: Map grid data (bytes). Part 2: Waypoint pathing data. Map width for Track 0 is **142**. Tiles are rendered at **10x7** px.
- **`.img` (Sprite Containers)**: A custom binary wrapper around standard PNG data. Use `parseBinary` (VarInt length-prefixed parts) to extract the raw PNG bytes.
- **`coords.bin`**: Contains the frame metadata for all sprites. Each frame is **6 bytes**: `[srcX, srcY, width, height, offsetX, offsetY]`.
- **`game_menu.bin` / `ms.bin`**: Likely contain menu structures and mission data (not yet parsed).

### Coordinate Systems
- **Standardized to Y-UP**: The engine uses native LibGDX (0,0 is Bottom-Left).
- **Sprite Mapping**: 
    - J2ME offsets are from Top-Left. 
    - LibGDX_X = targetX - offsetX.
    - LibGDX_Y = targetY + offsetY - height.
- **Car Physics**: Player car uses frames **309 to 324** (16 frames). Original code had 48 but player car specific texture `12.png` aligns with the 16-frame cycle.

### Class Semantic Mapping
- `class_10` -> `GameCanvas` / `MenuScreen` / `GameScreen`
- `class_3` -> `GameEngine` / `CarsGame`
- `class_8` -> `SpriteManager` / `GameRenderer`
- `class_9` -> `CarEntity`
- `class_2` -> `Settings` / `ConfigParser`

---

## Phase 1: Reorganization & Setup [COMPLETE]
- [x] Create project structure (`qc.aeonis.cars`)
- [x] Migrate assets to `remaster/assets/`
- [x] Set up root `build.gradle` and subprojects
- [x] Initialize `PROGRESS.md`

## Phase 2: Asset Pipeline & Engine Core [COMPLETE]
- [x] Create base `CarsGame` and `DesktopLauncher`
- [x] Create Screen management system (`LoadingScreen`, `MenuScreen`, `GameScreen`)
- [x] Implement native LibGDX asset loading for PNGs
- [x] Implement custom binary parser for `.m2` and `.bin` files
- [x] Implement initial `InputHandler` with Keyboard + Mouse mapping
- [x] Enforce Nearest-Neighbor filtering for all textures (HD clarity)

## Phase 3: Game Logic Extraction [IN PROGRESS]
- [x] Reverse-engineer and implement `TrackData` loading
- [x] Reverse-engineer and implement `CarEntity` physics (ACCEL/FRICTION)
- [x] Implement `SpriteManager` with `.img` container support
- [x] Implement `GameRenderer` for track/car drawing
- [x] Implement rotation-to-frame mapping for cars (Fixed to 16 frames)
- [x] Implement initial `CarAI` skeleton
- [ ] Implement collision detection (Track tiles & Borders)

## Phase 4: Rendering & UI (Landscape) [COMPLETE]
- [x] Implement camera follow logic
- [x] Support landscape resolution scaling (`ExtendViewport`)
- [x] Implement mouse-click car steering (Target-based rotation)
- [x] Rebuild Menu system (`MenuScreen` with `scene2d.ui`)
- [x] Implement Hybrid Font System (TrueType HD default)
- [x] Implement `SettingsScreen` with GUI Scale and Language
- [x] Implement `PauseOverlay` (ESC to pause)

## Upcoming TODOs (Next Session)
- [ ] **Audio Engine**: Convert `.mid` to `.ogg` or implement MIDI synth to restore music.
- [ ] **Entity Placement**: Parse `coords.bin`/`ms.bin` to place coins, obstacles, and enemy cars.
- [ ] **Story/Flow**: Implement race logic (laps, countdown, finish line) and mission transitions.
- [ ] **Physics Polish**: Refine car handling (less "floaty") and add drift/skid effects.
- [ ] **UI Refinement**: Reduce HUD text size (currently too large) and add more vibrant race-day UI.

---
*Updated on 2026-05-20 by Gemini CLI*
