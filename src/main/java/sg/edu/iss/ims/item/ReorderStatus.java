package sg.edu.iss.ims.item;

public enum ReorderStatus {
    PENDING_DELIVERY("Pending Delivery"),
    DELIVERED("Delivered");
    
    private final String displayValue;

    private ReorderStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }    
}
