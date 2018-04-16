package Equipment;

public abstract class Meter implements Save {

    private String regNr, prefixRegNr, shelfCode;
    private Boolean isFunctioning;
    private static int code = 0;

    /**
     * When subclass is instantiated shelf placement and state of meter is set
     * A newly created meter always functions
     */
    public Meter(){
        setShelfCode(Code.generateShelfCode());
        setIsFunctioning(true);
    }

    /**
     * Get reg number
     * @return
     */
    public String getRegNr(){
        return this.regNr;
    }

    /**
     * Set reg number
     * @param code sequence number, iterates after each added meter
     */
    public void setRegNr(int code){
        this.regNr = Code.generateRegNr(code);
    }

    /**
     * Get the location of the meter, R = room, S = shelf, P = place
     * E.g R01S02P12
     * @return
     */
    public String getShelfCode() {
        return shelfCode;
    }

    /**
     * Set the location of a meter
     * @param shelfCode
     */
    public void setShelfCode(String shelfCode) {
        this.shelfCode = shelfCode;
    }

    /**
     * Get the state of the meter, true means the meter is working
     * @return
     */
    public boolean getIsFunctioning(){
        return this.isFunctioning;
    }

    /**
     * Set the state of the meter, true means the meter is working
     * @param isFunctioning
     */
    public void setIsFunctioning(boolean isFunctioning){
        this.isFunctioning = isFunctioning;
    }

    /**
     * Get the sequence number, every object have a unique number
     * @return
     */
    public static int getCode() {
        return code;
    }

    /**
     * Set the sequence number
     * @param code
     */
    public static void setCode(int code) {
        Meter.code = code;
    }

    public String getPrefixRegNr() {
        return prefixRegNr;
    }

    public void setPrefixRegNr(String prefixRegNr) {
        this.prefixRegNr = prefixRegNr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("Shelf Equipment.Code: ").append(getShelfCode()).append("\n")
                .append("Working condition: ").append(getIsFunctioning() ? "Working" : "Not working")
                .append("\n").toString();
    }
}
