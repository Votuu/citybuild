package net.xcyan.citybuild.slums.region;

public class RegionGenerator {

    private Direction direction;

    public RegionGenerator() {
        this.direction = Direction.NORTH;
    }

    private static enum Direction {
        NORTH(1, 2),
        EAST(2, 3),
        SOUTH(3, 4),
        WEST(4, 1);

        private int current;
        private int next;

        public static Direction byId(int id) {
            for(Direction cache : Direction.values()) {
                if(cache.current == id) {
                    return cache;
                }
            }

            return Direction.NORTH;
        }

        Direction(int current, int next) {
            this.current = current;
            this.next = next;
        }

        public int current() {
            return current;
        }

        public int next() {
            return next;
        }
    }
}
