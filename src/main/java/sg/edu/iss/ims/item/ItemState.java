package sg.edu.iss.ims.item;

public enum ItemState {
    IN_STOCK("In Stock"),
    REORDER_PLACED("Reorder Placed"),
    BELOW_REORDER_LEVEL("Below Reorder Level");

    private final String displayValue;

    private ItemState(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
