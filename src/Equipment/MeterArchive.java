package Equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class MeterArchive {

    private static List<Meter> meterList = new ArrayList<>(); // There is only one instance of this list

    /**
     * Controller.AddController meter to list
     *
     * @param meter Must be a subclass of Equipment.Meter
     */
    public static void addMeter(Meter meter) {
        meterList.add(meter);
    }

    /**
     * Remove a meter from the array of meters.
     * @param regNr find a meter to delete by its reg number
     * @return returns true if a meter was deleted
     */
    public static boolean removeMeter(String regNr) {
        try {
            Meter m = meterList.stream().filter(e -> e.getPrefixRegNr().equals(regNr)).findFirst().get();
            meterList.remove(m);
            System.out.println("Equipment.Meter deleted");
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Could not find meter by regNr: " + regNr);
            return false;
        }
    }

    /**
     * Get a specific meter from list
     * @param regNr the reg number of the meter to retrieve
     * @return
     */
    public static Meter getMeter(String regNr) {
        if(meterList.stream().filter(e -> e.getPrefixRegNr().equals(regNr)).count() >= 1){
            return meterList.stream().filter(e -> e.getPrefixRegNr().equals(regNr)).findFirst().get();
        }
        return null;
    }

    /**
     * Get the list of meters
     *
     * @return
     */
    public static List<Meter> getMeterList() {
        return meterList;
    }

    public static void clearMeterList(){
        meterList.clear();
    }

    public static void setMeterList(List<Meter> list){
        meterList = list;
    }

    /**
     * Print the list of meters
     */
    public static String printMeterList() {
        StringBuilder sb = new StringBuilder();
        for (Meter m : meterList) {
            sb.append(m);
            sb.append("\n");
        }
        return sb.toString();
    }
}
