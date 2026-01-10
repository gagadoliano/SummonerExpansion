package summonerexpansion.allpresets.MiniBiomes;

import necesse.engine.gameLoop.tickManager.Performance;
import necesse.engine.gameLoop.tickManager.PerformanceTimerManager;
import necesse.engine.registries.BiomeRegistry;
import necesse.engine.registries.ObjectRegistry;
import necesse.engine.registries.TileRegistry;
import necesse.engine.util.*;
import necesse.engine.util.voronoi.DelaunayTriangulator;
import necesse.engine.util.voronoi.TriangleLine;
import necesse.engine.world.biomeGenerator.BiomeGeneratorStack;
import necesse.engine.world.worldPresets.*;
import necesse.level.gameObject.GameObject;
import necesse.level.maps.generationModules.CellAutomaton;
import necesse.level.maps.generationModules.LinesGeneration;
import necesse.level.maps.presets.Preset;
import necesse.level.maps.presets.PresetUtils;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public class AncientForestMiniBiomeWorldPreset extends WorldPreset
{
    public int size = 75;

    public boolean shouldAddToRegion(LevelPresetsRegion presetsRegion)
    {
        return presetsRegion.identifier.equals(LevelIdentifier.SURFACE_IDENTIFIER) && presetsRegion.hasAnyOfBiome(BiomeRegistry.SWAMP.getID());
    }

    public void addToRegion(GameRandom random, final LevelPresetsRegion presetsRegion, final BiomeGeneratorStack generatorStack, PerformanceTimerManager performanceTimer)
    {
        int total = getTotalBiomePoints(random, presetsRegion, BiomeRegistry.SWAMP, 0.005F);

        for(int i = 0; i < total; ++i)
        {
            Dimension dimension = new Dimension(this.size, this.size);
            Point placeTile = findRandomBiomePresetTile(random, presetsRegion, generatorStack, BiomeRegistry.SWAMP, 50, dimension, new String[]{"minibiomes", "loot"}, (tileX, tileY) -> runCornerCheck(tileX, tileY, size, size, (tileX1, tileY1) -> generatorStack.getLazyBiomeID(tileX1, tileY1) == BiomeRegistry.SWAMP.getID()));
            if (placeTile != null)
            {
                LinesGenerationWorldPreset lg = (new LinesGenerationWorldPreset(placeTile.x + this.size / 2, placeTile.y + this.size / 2)).addRandomArms(random, 16, (float)this.size / 2.0F - 10.0F, (float)this.size / 2.0F, 6.0F, 8.0F);
                if (lg.isWithinPresetRegionBounds(presetsRegion))
                {
                    RegionTileWorldPresetGenerator tileGenerator = new RegionTileWorldPresetGenerator();
                    Objects.requireNonNull(lg);
                    final PointHashSet validTiles = Performance.record(performanceTimer, "getDiamondPoints", lg::getDiamondPoints);
                    for (Point tile : validTiles)
                    {
                        tileGenerator.addTile(tile.x, tile.y, (random1, level, tileX, tileY, timer) ->
                        {
                            level.setTile(tileX, tileY, TileRegistry.getTileID("ancientgrasstile"));
                            level.setObject(tileX, tileY, 0);
                        });
                    }
                    Rectangle bounds = lg.getOccupiedTileRectangle();
                    ArrayList<Point2D.Float> voronoiPoints = new ArrayList<>();
                    int voronoiPadding = -20;
                    int usableWidth = bounds.width - voronoiPadding * 2;
                    int usableHeight = bounds.height - voronoiPadding * 2;
                    int resolution = 20;
                    int resWidth = usableWidth / resolution;
                    int resHeight = usableHeight / resolution;
                    int xOffset = bounds.x + voronoiPadding + usableWidth % resolution / 2;
                    int yOffset = bounds.y + voronoiPadding + usableHeight % resolution / 2;
                    for(int x = 0; x < resWidth; ++x)
                    {
                        int minX = x * resolution + xOffset;
                        int maxX = minX + resolution;

                        for(int y = 0; y < resHeight; ++y)
                        {
                            int minY = y * resolution + yOffset;
                            int maxY = minY + resolution;
                            Point2D.Float point = new Point2D.Float((float)random.getIntBetween(minX, maxX), (float)random.getIntBetween(minY, maxY));
                            voronoiPoints.add(point);
                        }
                    }
                    ArrayList<TriangleLine> voronoiLines = new ArrayList<>();
                    DelaunayTriangulator.compute(voronoiPoints, false, voronoiLines);
                    final PointHashSet floorTiles = new PointHashSet();
                    PointHashSet houseTiles = new PointHashSet();
                    for(TriangleLine line : voronoiLines)
                    {
                        LinesGeneration.pathTiles(new Line2D.Float(line.p1, line.p2), true, (from, next) ->
                        {
                            if (validTiles.contains(next.x, next.y))
                            {
                                tileGenerator.addTile(next.x, next.y, (random2, level, tileX, tileY, timer) ->
                                {
                                    if (random2.getChance(0.8F))
                                    {
                                        level.setTile(tileX, tileY, TileRegistry.getTileID("graveltile"));
                                    }
                                    level.setObject(tileX, tileY, 0);
                                });
                                floorTiles.add(next.x, next.y);
                            }
                        });
                    }
                    AtomicInteger lootRotation = new AtomicInteger();
                    ArrayList<BiFunction<GameRandom, AtomicInteger, Preset>> housePresets = new ArrayList<>();
                    housePresets.add(DollMakerHousePreset::new);
                    housePresets.add(AncientSunflowerFarmPreset::new);
                    housePresets.add(AncientPicnicPreset::new);
                    LinkedList<Runnable> housePresetRunners = new LinkedList<>();
                    while(!voronoiPoints.isEmpty())
                    {
                        int pointsIndex = random.nextInt(voronoiPoints.size());
                        Point2D.Float centerTile = voronoiPoints.remove(pointsIndex);
                        int centerTileX = (int)centerTile.x;
                        int centerTileY = (int)centerTile.y;
                        if (!housePresets.isEmpty())
                        {
                            Dimension houseSize = new Dimension(10, 10);
                            int houseTileX = centerTileX - houseSize.width / 2;
                            int houseTileY = centerTileY - houseSize.height / 2;
                            boolean canPlaceHouse = this.runGridCheck(houseTileX, houseTileY, houseSize.width, houseSize.height, 2, (tileX, tileY) -> !floorTiles.contains(tileX, tileY) && validTiles.contains(tileX, tileY));
                            if (canPlaceHouse)
                            {
                                BiFunction<GameRandom, AtomicInteger, Preset> presetGetter = housePresets.remove(0);
                                housePresetRunners.add(() -> presetsRegion.addPreset(this, houseTileX, houseTileY, houseSize, (String)null, (random3, level, timer) ->
                                {
                                    Preset preset = presetGetter.apply(random3, lootRotation);
                                    preset = PresetUtils.randomizeRotationAndMirror(preset, random3);
                                    int placeOffsetX = houseSize.width / 2 - preset.width / 2;
                                    int placeOffsetY = houseSize.height / 2 - preset.height / 2;
                                    preset.applyToLevel(level, houseTileX + placeOffsetX, houseTileY + placeOffsetY);
                                    CellAutomaton ca = lg.doCellularAutomaton(random);
                                    ca.spawnMobs(level, random, "woodmob", 4, 8, 1, 10);
                                }));
                            }
                        }
                        else if (!floorTiles.contains(centerTileX, centerTileY) && !houseTiles.contains(centerTileX, centerTileY) && !random.getChance(0.2F))
                        {
                            LinesGeneration lakeLG = new LinesGeneration(centerTileX, centerTileY);
                            int lakeArms = random.getIntBetween(5, 7);
                            int angle = random.nextInt(360);
                            int anglePerArm = 360 / lakeArms;
                            for(int j = 0; j < lakeArms; ++j)
                            {
                                angle += random.getIntOffset(anglePerArm, anglePerArm / 2);
                                Point2D.Float lakeDir = GameMath.getAngleDir((float)angle);
                                AtomicReference<Point> armEndPointRef = new AtomicReference<>(new Point(centerTileX, centerTileY));
                                LinesGeneration.pathTilesBreak(new Line2D.Float(centerTile.x, centerTile.y, centerTile.x + lakeDir.x * (float)resolution, centerTile.y + lakeDir.y * (float)resolution), true, (from, next) ->
                                {
                                    int tileX = next.x;
                                    int tileY = next.y;
                                    if (floorTiles.contains(tileX, tileY))
                                    {
                                        return false;
                                    }
                                    else if (houseTiles.contains(tileX, tileY))
                                    {
                                        return false;
                                    }
                                    else
                                    {
                                        armEndPointRef.set(new Point(tileX, tileY));
                                        return true;
                                    }
                                });
                                Point armEndPoint = armEndPointRef.get();
                                float width = random.getFloatBetween(2.0F, 3.0F);
                                lakeLG.addLine((int)centerTile.x, (int)centerTile.y, armEndPoint.x, armEndPoint.y, width);
                            }
                            GameObject cattail = ObjectRegistry.getObject("cattail");
                            GameObject waterGrass = ObjectRegistry.getObject("ancientwatergrass");
                            CellAutomaton lakeCA = lakeLG.doCellularAutomaton(random);
                            lakeCA.streamAliveOrdered().forEach((tilex) ->
                            {
                                if (validTiles.contains(tilex.x, tilex.y))
                                {
                                    if (!floorTiles.contains(tilex.x, tilex.y))
                                    {
                                        if (!houseTiles.contains(tilex.x, tilex.y))
                                        {
                                            tileGenerator.addTile(tilex.x, tilex.y, (random4, level, tileX, tileY, timer) ->
                                            {
                                                level.setTile(tileX, tileY, TileRegistry.waterID);
                                                level.setObject(tileX, tileY, 0);
                                                if (random4.getChance(0.1F) && waterGrass.canPlace(level, 0, tileX, tileY, 0, false, false) == null)
                                                {
                                                    waterGrass.placeObject(level, 0, tileX, tileY, 0, false);
                                                }
                                                if (random4.getChance(0.1F) && cattail.canPlace(level, 0, tileX, tileY, 0, false, false) == null)
                                                {
                                                    cattail.placeObject(level, 0, tileX, tileY, 0, false);
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    }
                    for (Point floorTile : floorTiles)
                    {
                        for(int x = -1; x <= 1; ++x)
                        {
                            int tileX = floorTile.x + x;

                            for(int y = -1; y <= 1; ++y)
                            {
                                int tileY = floorTile.y + y;
                                if (!floorTiles.contains(tileX, tileY) && !houseTiles.contains(tileX, tileY))
                                {
                                    tileGenerator.addTile(tileX, tileY, (random5, level, tileX2, tileY2, timer) -> level.setTile(tileX2, tileY2, TileRegistry.getTileID("ancientgrasstile")));
                                }
                            }
                        }
                    }
                    final TicketSystemList<GameObject> randomObjects = new TicketSystemList<>();
                    randomObjects.addObject(5, ObjectRegistry.getObject("mushroom"));
                    randomObjects.addObject(10, ObjectRegistry.getObject("ancientwoodroot"));
                    randomObjects.addObject(25, ObjectRegistry.getObject("ancienttree"));
                    randomObjects.addObject(30, ObjectRegistry.getObject("ancienttallgrass"));
                    for(Point tile : validTiles)
                    {
                        if (!floorTiles.contains(tile.x, tile.y) && random.getChance(0.2F))
                        {
                            tileGenerator.addTile(tile.x, tile.y, (random6, level, tileX, tileY, timer) ->
                            {
                                GameObject obj = randomObjects.getRandomObject(random6);
                                if (obj.canPlace(level, 0, tileX, tileY, 0, false, true) == null) {
                                    obj.placeObject(level, 0, tileX, tileY, 0, false);
                                }

                            });
                        }
                    }
                    tileGenerator.forEachRegion((regionX, regionY, placeFunction) ->
                    {
                        int tileX = GameMath.getTileCoordByRegion(regionX);
                        int tileY = GameMath.getTileCoordByRegion(regionY);
                        presetsRegion.addPreset(AncientForestMiniBiomeWorldPreset.this, tileX, tileY, new Dimension(16, 16), new String[]{"minibiomes", "loot"}, placeFunction);
                    });
                    housePresetRunners.forEach(Runnable::run);
                }
            }
        }
    }
}