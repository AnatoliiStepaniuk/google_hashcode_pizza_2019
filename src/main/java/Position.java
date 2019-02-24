import java.util.Objects;

public class Position{
    private long row;
    private long column;

    public long getRow() {
        return row;
    }

    public void setRow(long row) {
        this.row = row;
    }

    public long getColumn() {
        return column;
    }

    public void setColumn(long column) {
        this.column = column;
    }

    public Position(long row, long column) {
        if(row < 0 || column < 0){
            throw new RuntimeException();
        }
        this.row = row;
        this.column = column;
    }

    public Position(Position position) {
        this(position.row, position.column);
    }

    public Position move(Direction direction) {
        Position position = new Position(row, column);
        switch (direction){
            case UP:
                position.row--;
                break;
            case DOWN:
                position.row++;
                break;
            case LEFT:
                position.column--;
                break;
            case RIGHT:
                position.column++;
                break;
        }
        return position;
    }

    public String formatCoordinates() {
        return row + " " + column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row &&
                column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
