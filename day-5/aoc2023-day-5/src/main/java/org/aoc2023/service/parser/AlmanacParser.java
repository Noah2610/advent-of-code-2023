package org.aoc2023.service.parser;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.exception.ParseException;
import org.aoc2023.model.*;
import org.aoc2023.model.entity.Seed;
import org.aoc2023.model.entityMap.*;

import java.util.LinkedList;
import java.util.List;

public class AlmanacParser implements Parser<Almanac> {
    public AlmanacParser() {
    }

    @Override
    public ParseResult<Almanac> parse(String input) throws ParseException {
        Parser<Almanac> parser = new MapperParser<>(
                new SequenceParser<>(List.of(
                        new TokenParser("seeds: "),
                        new RepeatParser<>(new SequenceParser<>(List.of(
                                new MapperParser<>(new IntegerParser(), n -> new Seed(Id.of(n))),
                                new SpaceParser(),
                                new IntegerParser(),
                                new OptionalParser<>(new SpaceParser())
                        ))),
                        new NewlineParser(),
                        new RepeatParser<>(new SequenceParser<>(List.of(
                                new OneOfParser<>(List.of(
                                        new MapperParser<>(new TokenParser("seed-to-soil" + " map:"), (_s) -> new SeedToSoil()),
                                        new MapperParser<>(new TokenParser("soil-to-fertilizer" + " map:"), (_s) -> new SoilToFertilizer()),
                                        new MapperParser<>(new TokenParser("fertilizer-to-water" + " map:"), (_s) -> new FertilizerToWater()),
                                        new MapperParser<>(new TokenParser("water-to-light" + " map:"), (_s) -> new WaterToLight()),
                                        new MapperParser<>(new TokenParser("light-to-temperature" + " map:"), (_s) -> new LightToTemperature()),
                                        new MapperParser<>(new TokenParser("temperature-to-humidity" + " map:"), (_s) -> new TemperatureToHumidity()),
                                        new MapperParser<>(new TokenParser("humidity-to-location" + " map:"), (_s) -> new HumidityToLocation())
                                )),
                                new NewlineParser(),
                                new RepeatParser<>(new SequenceParser<>(List.of(
                                        new MapperParser<>(
                                                new SequenceParser<>(List.of(
                                                        new IntegerParser(),
                                                        new SpaceParser(),
                                                        new IntegerParser(),
                                                        new SpaceParser(),
                                                        new IntegerParser()
                                                )),
                                                idsAndSpaces -> {
                                                    var dstId = (Long) idsAndSpaces.get(0);
                                                    var srcId = (Long) idsAndSpaces.get(2);
                                                    var range = (Long) idsAndSpaces.get(4);
                                                    return new EntityMapConfig(srcId, dstId, range);
                                                }
                                        ),
                                        new NewlineParser()
                                )))
                        )))
                )),
                results -> {
                    var seedRangesAndSpaces = (List<List<Object>>) results.get(1);
                    List<Range<Seed>> seedRanges = new LinkedList<>();

                    for (var seedOrRangeOrSpace : seedRangesAndSpaces) {
                        Range<Seed> seedRange;
                        try {
                            seedRange = new EntityRange<>(
                                    (Seed) seedOrRangeOrSpace.get(0),
                                    (Long) seedOrRangeOrSpace.get(2),
                                    Seed.class
                            );
                        } catch (EntityInstantiationException ex) {
                            throw new ParseException("couldn't create EntityRange<Seed>", ex);
                        }
                        seedRanges.add(seedRange);
                    }

                    var mapDefs = (List<List<Object>>) results.get(3);
                    List<EntityMap<?, ?>> entityMaps = new LinkedList<>();

                    for (var mapDef : mapDefs) {
                        var entityMap = (EntityMap) mapDef.get(0);
                        var mapConfigs = (List<List<Object>>) mapDef.get(2);
                        for (var mapConfigAndNewline : mapConfigs) {
                            var mapConfig = (EntityMapConfig) mapConfigAndNewline.get(0);
                            try {
                                entityMap.addMap(mapConfig);
                            } catch (EntityInstantiationException ex) {
                                throw new ParseException("couldn't add EntityMapConfig to EntityMap", ex);
                            }
                        }
                        entityMaps.add(entityMap);
                    }

                    return new Almanac(seedRanges, entityMaps);
                });

        return parser.parse(input);
    }
}