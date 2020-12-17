package sg.edu.iss.ims.user;

public enum Role {
	ADMIN_CLERK("Admin Clerk"),
	MECHANIC("Mechanic");
	
    private final String displayValue;

    private Role(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
