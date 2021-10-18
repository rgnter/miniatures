package xyz.rgnt.miniatures;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import xyz.rgnt.miniatures.model.Miniature;

public class MiniatureAPI {

    /**
     * Creates Miniature Builder filled with blocks of specified area in specified world
     *
     * @param world    Area world
     * @param mostPos  Area most pos
     * @param leastPos Area least pos
     * @param small    Whether miniature is small or not
     * @return Miniature.Builder
     */
    public static @NotNull Miniature.Builder areaToMiniature(@NotNull World world,
                                                             @NotNull Vector mostPos,
                                                             @NotNull Vector leastPos,
                                                             boolean small) {
        Miniature.Builder miniature = Miniature.Builder.create(new Vector(
                mostPos.getBlockX() - leastPos.getBlockX(),
                mostPos.getBlockY() - leastPos.getBlockY(),
                mostPos.getBlockZ() - leastPos.getBlockZ()));

        for (int x = leastPos.getBlockX(); x < mostPos.getBlockX(); x++) {
            for (int z = leastPos.getBlockZ(); z < mostPos.getBlockZ(); z++) {
                for (int y = leastPos.getBlockY(); y < mostPos.getBlockY(); y++) {
                    Location location = new Location(world, x,y,z);
                    Block block = world.getBlockAt(location);

                    miniature.addPart(block.getType(), location.toVector(), small);
                }
            }
        }
        return miniature;
    }


    /**
     * @return New Miniature builder
     */
    public static @NotNull Miniature.Builder miniatureBuilder() {
        return new Miniature.Builder();
    }
}
