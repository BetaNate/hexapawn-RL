import javafx.scene.paint.Color;

public class User {
    private final String name;
    private final String side;
	private Color color;

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

	public final Color getColor() {
		switch(side.toLowerCase()) {
			case "black":
				color = Color.BLACK;
				break;
			case "white":
				color = Color.WHITE;
				break;
			default:
				System.out.println("INVALID COLOR");
				color = null;
				break;
		}
		return color;
	}

	@Override
	public String toString() {
		return name;
	}
}

