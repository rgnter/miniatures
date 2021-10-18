package xyz.rgnt.miniatures.model;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Miniature {

    @Getter @NotNull
    private final Map<Integer, Part> parts = new HashMap<>();

    @Getter @NotNull
    private Vector dimensions = new Vector(0d, 0d, 0d);


    private Miniature() {
    }

    /**
     * Represents single Part of Miniature.
     */
    public static class Part {

        @Getter @Setter
        private @NotNull Material material;

        @Getter @Setter
        private @NotNull Vector relPos;
        @Getter @Setter
        private @NotNull Vector offset
                = new Vector();

        @Getter @Setter
        private float yaw = 0f;
        @Getter @Setter
        private float pitch = 0f;

        @Getter @Setter
        private @NotNull boolean small;

        public Part(@NotNull Material material,
                    @NotNull Vector relPos,
                    float yaw, float pitch,
                    boolean small) {
            this.material = material;
            this.relPos = relPos;
            this.yaw = yaw;
            this.pitch = pitch;
            this.small = small;
        }

        public Part(@NotNull Material material,
                    @NotNull Vector relPos,
                    boolean small) {
            this.material = material;
            this.relPos = relPos;
            this.small = small;
        }

        /**
         * @return Real relative position in world according to size
         */
        public @NotNull Vector getRelativePos() {
            return this.relPos.clone().multiply(small ? 0.439d : 0.627d).add(offset);
        }
    }

    /**
     *
     */
    public static class Builder {

        @Getter
        private int lastIndex = 0;
        private @NotNull Miniature miniature = new Miniature();

        /**
         * Creates a builder with already existing Miniature
         * @param miniature Miniature
         * @return Builder
         */
        public static @NotNull Builder create(@NotNull Miniature miniature, @NotNull Vector dimensions) {
            Builder builder = new Builder();
            builder.miniature = miniature;
            builder.miniature.dimensions = dimensions;
            return builder;
        }

        /**
         * Creates a builder
         * @return Builder
         */
        public static @NotNull Builder create(@NotNull Vector dimensions) {
            return create(new Miniature(), dimensions);
        }

        /**
         * Adds part
         * @param material Material
         * @param pos      Relative position (Model position)
         * @param yaw      Yaw rotation
         * @param pitch    Pitch rotation
         * @return Builder
         */
        public @NotNull Builder addPart(@NotNull Material material, @NotNull Vector pos, float yaw, float pitch, boolean small) {
            this.miniature.parts.put(lastIndex++, new Part(
                    material,
                    pos,
                    yaw,
                    pitch,
                    small));
            return this;
        }

        /**
         * Adds part
         * @param material Material
         * @param pos      Relative position (Model position)
         * @return Builder
         */
        public @NotNull Builder addPart(@NotNull Material material, @NotNull Vector pos, boolean small) {
            return addPart(material, pos, 0f, 0f, small);
        }

        /**
         * @return Miniature
         */
        public @NotNull Miniature get() {
            return miniature;
        }
    }
}
