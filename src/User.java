
public class User {
    private final String name;
    private final String side;
	public User(String name, String side) {
		this.name = name;
        this.side = side;
	}
	
	public final String getName() {
		return name ;
	}
	
    public final String getSide() {
        return side;
    }

	@Override
	public String toString() {
		return name;
	}
}
