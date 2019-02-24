import java.util.*;

public class Area {
    private Position start;
    private Position finish;

    public Position getStart() {
        return start;
    }

    public Position getFinish() {
        return finish;
    }

    public void setStart(Position start) {
        if (start.getRow() < 0 || start.getColumn() < 0) {
            System.out.printf("");
        }
        this.start = start;
    }

    public void setFinish(Position finish) {
        if (start.getRow() < 0 || start.getColumn() < 0) {
            System.out.printf("");
        }
        this.finish = finish;
    }

    public Area(Area other) {
        this(new Position(other.start), new Position(other.finish));
    }

    public Area(Position start, Position finish) {
//        if (start.getColumn() < 0 || start.getRow() < 0 || finish.getRow() < 0 || finish.getColumn() < 0) {
//            throw new RuntimeException(); // TODO
//        }
        this.start = start;
        this.finish = finish;
    }

    public long countArea() {
        return width() * height();
    }

    public long width() {
        return finish.getColumn() - start.getColumn() + 1;
    }

    public long height() {
        return finish.getRow() - start.getRow() + 1;
    }

    public Position indexToPosition(long index) {
        index %= this.countArea();
        index = Math.abs(index);

        long row = index / this.width();
        long column = index % this.width();

        return new Position(row, column);
    }

    public List<Position> positions() {
        List<Position> positions = new ArrayList<>();

        for (int r = 0; r <= finish.getRow() - start.getRow(); r++) {
            for (int c = 0; c <= finish.getColumn() - start.getColumn(); c++) {
                positions.add(new Position(start.getRow() + r, start.getColumn() + c));
            }
        }

        return positions;
    }

    public Position getNextTo(Position position) {
        List<Position> positions = positions();
        int index = (positions.indexOf(position) + 1) % positions.size();
        return positions.get(index);
    }


    public Area increase(Direction direction) {
        Area area = new Area(this);
        switch (direction) {
            case UP:
                area.start.setRow(area.start.getRow() - 1);
                break;
            case DOWN:
                area.finish.setRow(area.finish.getRow() + 1);
                break;
            case LEFT:
                area.start.setColumn(area.start.getColumn() - 1);
                break;
            case RIGHT:
                area.finish.setColumn(area.finish.getColumn() + 1);
                break;
        }
        return area;
    }

    public boolean intersects(Area other) {
        return !intersection(other).isEmpty();
    }

    public Collection<Position> intersection(Area other) {
        Collection<Position> intersection = this.positions();
        intersection.retainAll(other.positions());
        return intersection;
    }

    public String formatCoordinates() {
        return start.formatCoordinates() + " " + finish.formatCoordinates();
    }

    public boolean touchingBorder(Position position) {
        return position.getColumn() == start.getColumn()
                || position.getRow() == start.getRow()
                || position.getColumn() == finish.getColumn()
                || position.getRow() == finish.getRow();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return Objects.equals(start, area.start) &&
                Objects.equals(finish, area.finish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, finish);
    }
}
