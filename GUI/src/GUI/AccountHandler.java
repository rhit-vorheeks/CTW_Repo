package GUI;

public class AccountHandler {
	
	private String username;

	public AccountHandler() {
		username = null;
	}
	
	public void setName(String name) {
		this.username = name;
	}

	public String getName() {
		return this.username;
	}
}
