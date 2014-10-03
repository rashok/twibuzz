package models;

/**
 * Created by rashok on 9/9/14.
 */
public class Pagination {

    public final int start;
    public final int fetchSize;
    public final int sortBy;
    public final String sortDir;

    public Pagination(int start, int fetchSize, int sortBy, String sortDir) {
        this.start = start;
        this.fetchSize = fetchSize;
        this.sortBy = sortBy;
        this.sortDir = sortDir;
    }
}
