package Equipment;

public class Code {

    private static int roomCode = 1, shelfCode = 1, placeCode = 1;

    /**
     * Generate a unique reg number, if generated reg number exist a new will be generated.
     * @return
     */
    public static String generateRegNr(int code){
        String reg = getRegCode(code);
        if(isRegNrInUse(reg)){
            reg = getRegCode(code);
        }
        return reg;
    }

    /**
     * Generate a code for object created
     * @return
     */
    private static String getRegCode(int code){
        StringBuilder sb = new StringBuilder();
        return sb.append(String.format("%04d", code)).toString();
    }

    /**
     * Generate code for shelf placement, each meter will be assigned to a specific room
     * E.g. All clocks will be assigned to the same room
     * Format example: R01S05P15 translates to Room 1 Shelf 5 and Place 15
     * Max number of rooms/shelfs/places is 99 (00 to 99)
     * @return
     */
    public static String generateShelfCode() {
        StringBuilder sb = new StringBuilder();
        sb.append("R").append(String.format("%02d" , roomCode))
                .append("S").append(String.format("%02d", shelfCode))
                .append("P").append(String.format("%02d", placeCode++));
        if(getPlaceCode() > 99){
            setPlaceCode(0);
            setShelfCode(getShelfCode() + 1);
        }
        if(getShelfCode() > 99){
            setShelfCode(0);
            roomCode++;
        }
        return sb.toString();
    }

    public static boolean isShelfCodeInUse(String shelfCode){
        long count = MeterArchive.getMeterList().stream().filter(e -> e.getShelfCode().equals(shelfCode)).count();
        if(count > 0){
            return true;
        }
        return false;
    }

    /**
     * Check if the reg number generated exist
     * @param regNr
     * @return
     */
    private static boolean isRegNrInUse(String regNr) {
            long count = MeterArchive.getMeterList().stream().filter(e -> e.getRegNr().equals(regNr)).count();
        if(count > 0){
            return true;
        }
        return false;
    }

    /**
     * Get current shelf number
     * @return
     */
    private static int getShelfCode() {
        return shelfCode;
    }

    /**
     * Set shelf number
     * @param shelfCode
     */
    private static void setShelfCode(int shelfCode) {
        Code.shelfCode = shelfCode;
    }

    /**
     * Get current placement number
     * @return
     */
    private static int getPlaceCode() {
        return placeCode;
    }

    /**
     * Set placement number
     * @param placeCode
     */
    private static void setPlaceCode(int placeCode) {
        Code.placeCode = placeCode;
    }
}
