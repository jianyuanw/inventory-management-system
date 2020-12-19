package sg.edu.iss.ims.model;

import java.util.Arrays;

public class Alert {
	private String type;
	private String message;
	
	public Alert(String type, String message) {
		String[] validTypes = {"primary", "secondary", "success", "danger", "warning", "info", "light", "dark"};
		if (! Arrays.stream(validTypes).anyMatch(type::equals)) {
			throw new Error("Alert type does not exist");
		} else {
			this.type = type;
			this.message = message;
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
