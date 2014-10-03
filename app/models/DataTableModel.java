package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rashok on 9/7/14.
 */
public class DataTableModel implements Serializable {
    private String sEcho;
    private int iTotalRecords;
    private int iTotalDisplayRecords;
    private ArrayList<ArrayList<String>> aaData = new ArrayList<ArrayList<String>>();

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public ArrayList<ArrayList<String>> getAaData() {
        return aaData;
    }

    public void setAaData(ArrayList<ArrayList<String>> aaData) {
        this.aaData = aaData;
    }
}