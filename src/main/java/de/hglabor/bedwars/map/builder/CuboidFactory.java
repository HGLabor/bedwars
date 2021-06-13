package de.hglabor.bedwars.map.builder;

import de.hglabor.bedwars.region.Cuboid;
import org.bukkit.Location;

public class CuboidFactory {

    private Location first;
    private Location second;

    public Location getFirst() {
        return first;
    }

    public void setFirst(Location first) {
        this.first = first;
    }

    public Location getSecond() {
        return second;
    }

    public void setSecond(Location second) {
        this.second = second;
    }

    public Cuboid constructOne() {
        return new Cuboid(first, second);
    }
}
