package net.xcyan.citybuild.slums.region;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegionController {

    private final Map<UUID, Region> regionMap;

    public RegionController() {
        this.regionMap = new HashMap<>();
    }

    public Map<UUID, Region> regionMap() {
        return regionMap;
    }
}
