package de.hglabor.bedwars.map.builder;

import de.hglabor.bedwars.map.Base;

public class BaseFactory {

    private TeamFactory team;
    private CuboidFactory cuboid;

    public TeamFactory getTeam() {
        return team;
    }

    public void setTeam(TeamFactory team) {
        this.team = team;
    }

    public void setCuboid(CuboidFactory cuboid) {
        this.cuboid = cuboid;
    }

    public CuboidFactory getCuboid() {
        return cuboid;
    }

    public Base constructOne() {
        return new Base(team.constructOne(), cuboid.constructOne());
    }

}
