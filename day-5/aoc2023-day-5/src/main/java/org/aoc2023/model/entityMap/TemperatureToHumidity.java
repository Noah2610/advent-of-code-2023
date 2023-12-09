package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Humidity;
import org.aoc2023.model.entity.Temperature;

import java.util.List;

public class TemperatureToHumidity extends EntityMapBase<Temperature, Humidity> {
    public TemperatureToHumidity(List<EntityMapConfig> maps) throws EntityInstantiationException {
        super(maps, Temperature.class, Humidity.class);
    }

    public TemperatureToHumidity() {
        super(Temperature.class, Humidity.class);
    }

    @Override
    public EntityMapType getEntityMapType() {
        return new EntityMapType(EntityType.TEMPERATURE, EntityType.HUMIDITY);
    }
}