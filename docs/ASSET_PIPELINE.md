# Asset And Texture Pipeline

This document expands the project notes for creating, importing, documenting, and testing textures and graphics inside the native Android version of Labyrinth.

It is written as a Q&A because asset work tends to raise practical questions: where should a brick texture live, what file type should it use, how big should it be, how do we avoid bloating the app, and how do we prove that an asset is legal and performant?

## Foundation

The Android app must ship as a native offline game. Assets should be included in the Android project or delivered through official Android/Google Play mechanisms if the project eventually becomes too large. The game should not depend on web-hosted images, runtime scraping, remote texture downloads, or hidden server assets.

Use official Android concepts for storage:

- `res/drawable*` for ordinary Android drawable resources such as icons, UI images, and density-specific bitmaps.
- `res/mipmap*` for launcher icons and adaptive icon layers.
- `res/raw` for small raw files that need generated Android resource IDs.
- `assets/` for files that should be opened by filename and kept in a custom folder hierarchy, such as renderer-managed texture sets, material manifests, or larger non-UI files.
- Play Asset Delivery only later, if the project grows beyond a simple install-time asset set.

Official references:

- Android app resources: https://developer.android.com/guide/topics/resources/providing-resources
- Drawable resources: https://developer.android.com/guide/topics/resources/drawable-resource
- Android Studio Resource Manager: https://developer.android.com/studio/write/resource-manager
- Android texture compression for games: https://developer.android.com/games/optimize/textures
- Texture compression format targeting in App Bundles: https://developer.android.com/guide/playcore/asset-delivery/texture-compression
- Reduce app size: https://developer.android.com/topic/performance/reduce-apk-size
- Support different pixel densities: https://developer.android.com/training/multiscreen/screendensities

## Q&A

### What is a texture in this project?

A texture is an image used by the renderer to describe the visible surface of a game object. A brick wall texture, stone floor texture, glowing exit texture, or hazard decal can all be textures.

In a 3D renderer, textures are usually mapped onto geometry. For example, one rectangular wall segment may use a repeating brick image. In a 2D or raycast renderer, the same concept still applies: the wall slice or floor sample uses an image rather than a flat color.

### What is the difference between a texture, sprite, icon, and UI graphic?

- Texture: an image applied to a world surface such as a wall, floor, ceiling, door, or exit.
- Sprite: an image or animation frame representing an object or effect, such as a collectible, spark, hint marker, or future enemy.
- Icon: a small symbolic image for launcher, buttons, menus, or settings.
- UI graphic: a non-symbolic interface image such as a panel background, score badge, or tutorial illustration.

These categories matter because Android stores and scales them differently. UI icons often belong in `res/drawable` as vector drawables. Launcher icons belong in `res/mipmap`. Renderer-managed textures often belong in `assets/textures` or a later asset pack because the game renderer may need direct filenames, mipmaps, or compressed GPU texture formats.

### Where should a brick wall texture live?

Before the renderer exists, store source art and documentation outside the final Android resource tree, such as:

`art/source/textures/walls/brick_wall_01/`

When the Android project exists, exported runtime assets should follow the renderer decision:

- Android Canvas prototype: likely `app/src/main/assets/textures/walls/brick_wall_01.webp` or `app/src/main/res/drawable-nodpi/brick_wall_01.webp`.
- OpenGL ES or Filament renderer: likely `app/src/main/assets/textures/walls/brick_wall_01/` with exported compressed textures and a small material manifest.
- UI-only brick preview: `app/src/main/res/drawable*` if it participates in normal Android density handling.

Avoid putting large game-world textures in density-specific drawable folders unless Android's resource scaling is actually desired. Game textures usually need renderer-controlled pixel dimensions.

### What makes a good maze wall texture?

A good wall texture for this game should:

- Tile seamlessly on the horizontal axis, and preferably vertically too.
- Read clearly while the player is moving.
- Avoid tiny high-contrast noise that shimmers on mobile screens.
- Have enough variation to prevent the maze from feeling flat, but not so much that navigation becomes visually confusing.
- Keep important cues consistent: exits, hints, start zones, hazards, and interactable areas should remain visually distinct.
- Work at several brightness levels because Android devices vary widely in display tuning.
- Have documented source, license, dimensions, color space, export format, and intended in-game scale.

### How do we make a brick texture asset?

Recommended learning workflow:

1. Create or source a legal high-resolution base image.
2. Record the source, license, author, and modification rights.
3. Make it seamless using an image editor or procedural material tool.
4. Create an albedo/color map first. Add normal, roughness, metalness, ambient occlusion, or emission maps only if the chosen renderer can use them.
5. Export a working master in a lossless editable format.
6. Export runtime copies at controlled sizes such as 512x512, 1024x1024, or 2048x2048.
7. Test the texture repeated across long corridors, near corners, and under game lighting.
8. Compress it for Android once the renderer and target format are known.
9. Add the asset to the asset manifest.

### What file formats should we use?

Use different formats for different jobs:

- Source files: `.kra`, `.psd`, `.xcf`, `.blend`, `.sbs`, `.sbsar`, or another editable format, depending on the tool.
- UI vectors and icons: Android `VectorDrawable` XML when practical.
- UI bitmaps: WebP or PNG depending on transparency and visual requirements.
- Simple non-renderer images: WebP is usually a good Android runtime choice.
- GPU textures for 3D rendering: ASTC as the preferred modern texture compression target, with ETC2 as a practical fallback for broad OpenGL ES 3.0-era support.
- Audio is documented separately later, but short effects should use formats appropriate to Android's low-latency playback path.

Android's game texture guidance describes ASTC as the best primary option for modern games, with ETC2 as a fallback when ASTC is not supported. The exact export path should wait until the renderer is chosen.

### Should maze textures be PNG, JPEG, WebP, ASTC, or ETC2?

For early prototypes, WebP or PNG is acceptable because iteration speed matters.

For release-quality rendered game surfaces, use GPU-friendly texture compression:

- Prefer ASTC for modern devices.
- Provide ETC2 fallback if the supported device range requires it.
- Use Play Asset Delivery texture compression targeting if multiple compressed texture sets become large enough to justify it.

Avoid shipping large uncompressed PNG textures for world surfaces in the final build unless there is a specific measured reason.

### What image sizes should we use?

Start small and test. Reasonable early sizes:

- 256x256 for tiny repeating prototypes or debug textures.
- 512x512 for many repeating wall/floor textures.
- 1024x1024 for important hero surfaces or close-up materials.
- 2048x2048 only when profiling proves the detail is visible and affordable.

Prefer power-of-two dimensions for renderer-managed textures unless the chosen renderer explicitly removes that constraint. Even when non-power-of-two textures work, power-of-two assets often simplify mipmapping, compression, and tooling.

### Do textures need mipmaps?

Usually yes for 3D surfaces. Mipmaps reduce shimmer, aliasing, and texture bandwidth when a surface is viewed at a distance or steep angle.

For UI icons or fixed-size interface graphics, Android density buckets and vector drawables often solve the scaling problem instead. Do not treat game texture mipmaps and Android UI density resources as the same thing; they solve related but different problems.

### How do density buckets apply?

Android density buckets matter for normal app UI resources. Launcher icons, menu icons, button graphics, and static UI imagery should respect Android density guidance through vectors or density-qualified drawable resources.

Renderer-managed maze textures should usually use `drawable-nodpi` or `assets/` so Android does not rescale them behind the renderer's back. The renderer should decide how a texture maps to world units.

### Should icons be PNGs?

Prefer vector drawables for simple icons because they scale cleanly and can reduce package size. Use bitmap icons only when the artwork is too complex for a vector or when the icon is actually a small illustration.

Launcher icons should use Android's launcher icon tooling and `mipmap` output. Keep the launcher icon separate from in-game texture decisions.

### How should assets be named?

Use stable, lowercase, descriptive names:

- `wall_brick_red_01_albedo`
- `wall_brick_red_01_normal`
- `floor_stone_blue_01_albedo`
- `exit_gate_gold_01_emissive`
- `hint_path_glow_01`
- `sprite_key_silver_01`

Avoid spaces, vague names, and tool-export clutter such as `final_final_new2.png`.

### What metadata should every asset have?

Every non-trivial asset should have a record in an asset manifest. At minimum:

- Asset ID.
- Human-readable name.
- Category: texture, sprite, icon, UI, sound, music, model, shader, material.
- Source file path.
- Runtime file path.
- Author or generator.
- License.
- Whether commercial use is allowed.
- Whether modification is allowed.
- Required attribution text, if any.
- Original dimensions.
- Runtime dimensions.
- Export format.
- Compression format, if applicable.
- Intended renderer.
- Intended in-game scale.
- Date added.
- Notes and known limitations.

The manifest can begin as `docs/ASSET_MANIFEST.md` or a CSV. If the asset list grows, move to a structured format such as JSON, YAML, or TOML.

### Can AI-generated textures be used?

Possibly, but do not treat generated art as license-free by default.

For each generated asset, record:

- Tool or model used.
- Prompt summary.
- Date generated.
- Terms that applied at generation time.
- Whether commercial use is allowed.
- Whether the output was edited.
- Any restrictions around trademarked, copyrighted, or recognizable source material.

Do not generate assets "in the style of" a living artist or a copyrighted game. Do not use prompts that ask for recognizable protected characters, logos, or brand materials. Prefer original descriptions such as "seamless worn red brick wall texture for a stylized first-person maze game, no logos, no text."

### Can we use downloaded texture packs?

Yes, if the license allows the intended use.

Before importing:

- Check commercial use rights.
- Check attribution requirements.
- Check redistribution rights, because the texture will be bundled inside an app.
- Check modification rights.
- Keep a copy of the license text or URL.
- Record the source in the manifest.

Avoid assets with unclear license terms.

### How do we keep the game offline while using assets?

Bundle required release assets with the app or with official Play delivery mechanisms. The base offline game must not need to download textures at startup.

If optional online asset packs are ever considered, they must be designed as additive content. The core game must still work offline and must handle missing packs gracefully.

### How do we stop assets from making the app too large?

Use a budget before importing art:

- Limit texture dimensions.
- Use compressed texture formats for rendered surfaces.
- Use vector drawables for simple UI icons.
- Use WebP for many non-vector bitmaps.
- Avoid duplicated source files inside runtime folders.
- Remove unused assets before release.
- Use Android Studio APK Analyzer or App Bundle analysis tools during release preparation.
- Consider Play Asset Delivery only when asset size justifies extra complexity.

### How do we test a texture?

A texture is not approved just because it looks good in an editor. Test it inside the maze:

- One wall segment at close range.
- A long corridor with repeated tiling.
- A corner where texture seams are obvious.
- A low-light scene.
- A bright scene.
- A moving camera path to check shimmer.
- A low-end device to check performance and memory.
- A tablet or larger display to check scale.
- A color-blindness or contrast pass when the texture carries gameplay meaning.

### What should the first brick texture test prove?

The first brick texture should prove the pipeline, not the final art style.

It should answer:

- Can the asset be imported cleanly?
- Can the renderer load it by a stable ID or path?
- Does it tile on maze walls?
- Does it align at corners?
- Does it remain readable while moving?
- Does it avoid visible shimmer?
- Is its memory cost known?
- Is its license/source recorded?
- Can it be swapped without code changes?

### How do we represent materials?

A material is more than one image. It describes how the surface should look and react to light.

A simple material may include:

- `albedo`: base color.
- `normal`: fake small surface bumps.
- `roughness`: how sharp or diffuse highlights are.
- `metallic`: usually zero for stone, brick, and maze walls.
- `ambient_occlusion`: soft contact-darkening baked into crevices.
- `emissive`: glow for exits, hints, runes, or portals.

The first app version may use only albedo textures. Add richer material maps only when the renderer and performance budget support them.

### How should level themes use textures?

The current web game changes level color over time. The Android app can expand this into material themes:

- Early levels: clear, readable stone or brick.
- Mid levels: stronger color accents and decorative variation.
- Later levels: more dramatic emissive hints, worn surfaces, or themed corridors.
- Practice mode: high readability and lower visual noise.
- Timed/high-score modes: stronger contrast for exits and hints.

Themes should never hide navigation-critical information.

### How do assets affect accessibility?

Textures can make a game harder to read. Avoid relying on color alone. Keep exits, hints, hazards, and starts distinguishable through shape, brightness, motion, position, or sound/haptic feedback.

Provide settings where needed:

- Reduce motion.
- Lower visual noise.
- Increase contrast.
- Simplified textures.
- Larger HUD and minimap.
- Haptic intensity.
- Sound effect volume.

### What are the asset review rules?

Before an asset can be considered release-ready:

- It has a manifest record.
- Its license is clear.
- It has no unwanted text, logos, signatures, or protected marks.
- It is exported at the correct size.
- It is compressed appropriately for its use.
- It has been tested in-game.
- It has been checked on at least one low-end device or emulator profile.
- It does not create accessibility problems.
- It does not meaningfully increase app size without a documented reason.

## Proposed Folder Layout

This layout should be refined once the Android project exists:

```text
art/
  source/
    textures/
    sprites/
    icons/
    ui/
  exports/
    prototype/
    release/
docs/
  ASSET_PIPELINE.md
  ASSET_MANIFEST.md
app/
  src/
    main/
      assets/
        textures/
          walls/
          floors/
          exits/
          hints/
        materials/
        sprites/
      res/
        drawable/
        drawable-nodpi/
        mipmap-anydpi-v26/
        mipmap-hdpi/
        mipmap-mdpi/
        mipmap-xhdpi/
        mipmap-xxhdpi/
        mipmap-xxxhdpi/
```

Do not create this entire folder structure until the Android project exists. This section is a target layout, not an instruction to add empty folders now.

## Asset Manifest Template

Use this shape for the first manifest entries:

```text
Asset ID:
Name:
Category:
Source path:
Runtime path:
Author/generator:
License:
Commercial use allowed:
Modification allowed:
Attribution required:
Original dimensions:
Runtime dimensions:
Export format:
Compression:
Renderer:
In-game scale:
Date added:
Review status:
Notes:
```

## Open Decisions

- Which renderer will own runtime texture loading?
- Will the first playable prototype use Android Canvas, OpenGL ES, Filament, or libGDX?
- Will first-release walls use single albedo textures only, or a fuller material set?
- What is the first release texture size budget?
- Will texture compression be handled directly in the build, through renderer tooling, or through Play Asset Delivery later?
- Where will the first asset manifest live: Markdown, CSV, JSON, YAML, or TOML?

