package MobileComputing.SensorEnvironment;

public enum resourceClass {
    UD(-1), temperature(0), irSpectrum(1), o2Sat(2), humidity(3);

    private int colVal;

    resourceClass(int colVal) {
        this.setColVal(colVal);
    }

    public int getColVal() {
        return colVal;
    }

    public void setColVal(int colVal) {
        this.colVal = colVal;
    }

}