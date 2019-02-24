import java.util.*;
import java.util.stream.Collectors;

public class Pizza {
    long minEach;
    long maxSliceSize;
    int rows;
    int columns;
    boolean[][] isMushroom;

    Position topLeft;
    Position bottomRight;

    Set<Area> slices;// TODO should be effective structure for fast search of slice

    public Pizza(long minEach, long maxSliceSize, int rows, int columns) {
        this.minEach = minEach;
        this.maxSliceSize = maxSliceSize;
        this.rows = rows;
        this.columns = columns;
        this.isMushroom = new boolean[rows][columns];
        this.slices = new HashSet<>();
        this.topLeft = new Position(0, 0);
        this.bottomRight = new Position(rows-1, columns-1);
    }

    public void generateSlices() {
        generateSlicesInArea(new Area(new Position(0, 0), new Position(isMushroom.length - 1, isMushroom[0].length - 1)));

        // TODO iterate several times
//        Area mostDeficientArea = mostDeficientArea();
//        generateSlicesInArea(mostDeficientArea);
    }

    private Area mostDeficientArea() {
        return null; // TODO
    }

    private boolean possibleSlice(Area area) {
        return fitsConditions(area) && fitsSlices(area);
    }

    private boolean fitsConditions(Area area){

        if(area.countArea() > maxSliceSize){
            return false;
        }

        if(area.getStart().getRow() < topLeft.getRow()
                || area.getStart().getColumn() < topLeft.getColumn()
                || area.getFinish().getRow() > bottomRight.getRow()
                || area.getFinish().getColumn() > bottomRight.getColumn()
                ){
            return false;
        }

        long mushRooms = 0;
        long tomatoes = 0;

        for (Position position : area.positions()){
            if(isMushroom[(int)position.getRow()][(int)position.getColumn()]){
                mushRooms++;
            } else {
                tomatoes++;
            }
            if(mushRooms >= minEach && tomatoes >= minEach){
                return true;
            }
        }

        return false;
    }

    private boolean fitsSlices(Area area){
        for(Area slice : slices){
            if (area.intersects(slice)){
                return false;
            }
        }

        return true;
    }

    private void generateSlicesInArea(Area area) {
        clearSlicesInArea(area);

        while (true){
            Position input = getRandomPosition(area);
            Optional<Area> slice = findSliceIteration(input, area);
            if(!slice.isPresent()){
                return;
            }
            slices.add(slice.get());
        }
    }

    /**
     * Attempts to find slice from specified position within specified area.
     * @param input position to start search from (finding next empty position)
     * @param area area to search within
     * @return Found slice. Empty response means there are no available slices anymore.
     */
    private Optional<Area> findSliceIteration(Position input, Area area) {
        Optional<Position> start = nextToSlice(input, area);
        if (!start.isPresent()) {
            return Optional.empty();
        }

        Position current = start.get();
        Optional<Area> slice = findSlice(new Area(current, current), area);
        while (true) {
            if (slice.isPresent()) {
                return Optional.of(slice.get());
            } else {
                Optional<Position> next = nextToSlice(current, area);
                if (next.isPresent()) {
                    if (next.equals(start)) {
                        return Optional.empty();// There are one or several empty spots, each no able to accommodate a slice
                    }

                    current = next.get();
                } else { // Area is fully packed
                    return Optional.empty();
                }
            }
        }
    }

    private boolean belongsToSlice(Position position) {
        for(Area slice : slices){
            if(slice.positions().contains(position)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns next available position next to slice or enclosing area border.
     * @param position position to start searching from
     * @param area enclosing area
     * @return Available (not belonging to slice) position if found
     */
    private Optional<Position> nextToSlice(Position position, Area area) {
        Position start = position;
        while(!(!belongsToSlice(position) && (touchingSlice(position) || area.touchingBorder(position)))){
            position = area.getNextTo(position);
            if(position == start){
                return Optional.empty(); // We've traversed whole area
            }
        }

        return Optional.of(position);
    }

    private boolean touchingSlice(Position position) {
        return belongsToSlice(position.move(Direction.UP))
                || belongsToSlice(position.move(Direction.DOWN))
                || belongsToSlice(position.move(Direction.LEFT))
                || belongsToSlice(position.move(Direction.RIGHT));
    }

    private Optional<Area> findSlice(Area current, Area within){
        if(possibleSlice(current)){
            return Optional.of(current);
        }

        for(Direction direction : getRandomDirections()){
            if(fits(current.increase(direction), within)){
                Optional<Area> found = findSlice(current.increase(direction), within);
                if(found.isPresent()){
                    return found;
                }
            }
        }

        return Optional.empty();
    }

    private boolean fits(Area area, Area within){
        if(area.getStart().getColumn() < 0 || area.getStart().getRow() < 0 || area.getFinish().getRow() < 0 || area.getFinish().getColumn() < 0){
            return false;
        }
        boolean fitsWithin = area.intersection(within).containsAll(area.positions()); // TODO can optimize this method just check top-left & down right points
        return fitsWithin && fitsSlices(area);
    }

    private Collection<Direction> getRandomDirections(){
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        return directions;
    }

    private Position getRandomPosition(Area area) {
        return area.indexToPosition(new Random().nextInt());
    }

    private void clearSlicesInArea(Area area) {
        slices = slices.stream().filter(s -> !s.intersects(area)).collect(Collectors.toSet());
    }

    public long countPoints() {// TODo can optimize by saving current points counter
        return slices.stream().mapToLong(Area::countArea).sum();
    }

}
