package Equipment;

public interface Save {
    void saveData(double minVal, double maxVal, boolean isFunctioning, String shelfCode);

    void setMinValue(double minValue);

    void setMaxValue(double maxValue);

    double getMinValue();

    double getMaxValue();

    String getValueType();
}
