package Equipment;

public class Weight extends Meter {

    private static final String REGNR_PREFIX = "WE";
    private static final String GRAM = " gram";
    private double minWeight, maxWeight;

    /**
     * Default constructor for Equipment.Weight
     * Default minimum weight is 0.1 and maximum is 10
     * RegNr is generated automatically
     */
    public Weight(){
        setMinValue(0.1);
        setMaxValue(10);
        setRegNr(getCode());
        setCode(getCode() + 1);
        setPrefixRegNr(getRegnrPrefix() + getRegNr());
    }

    /**
     *
     * @param minWeight minimum value this scale can measure in grams
     * @param maxWeight maximum value this scale can measure in grams
     * @param isFunctioning set if the weight is working
     */
    public Weight(double minWeight, double maxWeight, boolean isFunctioning){
        setMinValue(minWeight);
        setMaxValue(maxWeight);
        setIsFunctioning(isFunctioning);
        setRegNr(getCode());
        setCode(getCode() + 1);
        setPrefixRegNr(getRegnrPrefix() + getRegNr());
    }

    /**
     * Get RegisterNumber prefix for this object
     * @return
     */
    public static String getRegnrPrefix() {
        return REGNR_PREFIX;
    }

    public double getMinValue() {
        return minWeight;
    }

    public void setMinValue(double maxWeight) {
        this.minWeight = maxWeight;
    }

    public double getMaxValue() {
        return maxWeight;
    }

    @Override
    public String getValueType() {
        return "weight";
    }

    public void setMaxValue(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public static String getGRAM() {
        return GRAM;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight weight = (Weight) o;
        return getRegNr().equals(weight.getRegNr());
    }

    /**
     * Information about this weight
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append(getClass().getSimpleName()).append("\n")
                .append("Weighing interval: ")
                .append(getMinValue()).append(getGRAM()).append(" to ")
                .append(getMaxValue()).append(getGRAM())
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
