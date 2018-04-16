package Equipment;

public class Clock extends Meter {

    private static final String REGNR_PREFIX = "CL";
    private static final String SECOND = " s";

    private double minInterval, maxInterval;

    /**
     * Default constructor for clock
     */
    public Clock(){
        setMinValue(0.01);
        setMaxValue(60.0);
        setIsFunctioning(true);
        setRegNr(getCode());
        setCode(getCode() + 1);
        setPrefixRegNr(getRegnrPrefix() + getRegNr());
    }

    /**
     * New clock set with specific intervals
     * @param minInterval
     * @param maxInterval
     * @param isFunctioning set if the clock is working
     */
    public Clock(double minInterval, double maxInterval, boolean isFunctioning){
        setMinValue(minInterval);
        setMaxValue(maxInterval);
        setIsFunctioning(isFunctioning);
        setRegNr(getCode());
        setCode(getCode() + 1);
        setPrefixRegNr(getRegnrPrefix() + getRegNr());
    }

    /**
     * Get the minimum interval of this clock
     * @return
     */
    public double getMinValue() {
        return minInterval;
    }

    /**
     * Set minimum interval for this clock
     * @param minInterval
     */
    public void setMinValue(double minInterval) {
        this.minInterval = minInterval;
    }

    /**
     * Get the maximum interval of this clock
     * @return
     */
    public double getMaxValue() {
        return maxInterval;
    }

    @Override
    public String getValueType() {
        return "interval";
    }

    /**
     * Set maximum interval for this clock
     * @param maxInterval
     */
    public void setMaxValue(double maxInterval) {
        this.maxInterval = maxInterval;
    }

    /**
     * Get RegisterNumber prefix for this clock
     * @return
     */
    public String getRegnrPrefix() {
        return REGNR_PREFIX;
    }

    /**
     * Get second suffix for this clock
     * @return
     */
    public static String getSECOND() {
        return SECOND;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clock clock = (Clock) o;
        return getRegNr().equals(clock.getRegNr());
    }

    /**
     * Information about this clock
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(getClass().getSimpleName()).append("\n")
                .append("Minimum time interval: ")
                .append(getMinValue()).append(getSECOND())
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
