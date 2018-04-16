package Equipment;

public class Thermometer extends Meter {

    private double minTemp, maxTemp;
    private static final String CELSIUS = "°C";
    private static final String REGNR_PREFIX = "TH";


    /**
     * Default constructor for Equipment.Thermometer
     * Default min temp is -30 degrees celcius
     * Default max temp is 50 degrees celcius
     */
    public Thermometer(){
        setMinValue(-30);
        setMaxValue(50);
        setRegNr(getCode());
        setCode(getCode() + 1);
        setPrefixRegNr(getRegnrPrefix() + getRegNr());
    }

    /**
     * New thermometer set with specific temperatures
     * @param minTemp minimum temperature this thermometer can measure
     * @param maxTemp maximum temperature this thermometer can measure
     * @param isFunctioning set if the thermometer is working
     */
    public Thermometer(double minTemp, double maxTemp, boolean isFunctioning){
        setMinValue(minTemp);
        setMaxValue(maxTemp);
        setRegNr(getCode());
        setCode(getCode() + 1);
        setIsFunctioning(isFunctioning);
        setPrefixRegNr(getRegnrPrefix() + getRegNr());
    }

    /**
     * Get RegisterNumber prefix for this object
     * @return
     */
    public String getRegnrPrefix() {
        return REGNR_PREFIX;
    }

    /**
     * Get minimum temperature for this thermometer
     */
    public double getMinValue() {
        return minTemp;
    }

    /**
     * Set minimum temperature for this thermometer
     * @param minTemp minimum temperature
     */
    public void setMinValue(double minTemp) {
        this.minTemp = minTemp;
    }

    /**
     * Get maximum temperature for this thermometer
     */
    public double getMaxValue() {
        return maxTemp;
    }

    @Override
    public String getValueType() {
        return "temperature";
    }

    /**
     * Set maximum temperature for this thermometer
     * @param maxTemp maximum temperature
     */
    public void setMaxValue(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    /**
     * Get string celsius
     * @return
     */
    public static String getCELSIUS() {
        return CELSIUS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thermometer t = (Thermometer) o;
        return getRegNr().equals(t.getRegNr());
    }

    /**
     * Information about this thermometer
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(getClass().getSimpleName()).append("\n")
                .append("Temperature range: ")
                .append(getMinValue()).append(getCELSIUS())
                .append(" to ").append(getMaxValue()).append(getCELSIUS())
                .append("\n")
                .append("Reg number: ").append(getRegnrPrefix()).append(getRegNr()).append("\n")
                .append(super.toString())
                .toString();
    }

    @Override
    public void saveData(double minVal, double maxVal, boolean isFunctioning, String shelfCode) {
        setMinValue(minVal);
        setMaxValue(maxVal);
        setIsFunctioning(isFunctioning);
        setShelfCode(shelfCode);
    }
}
