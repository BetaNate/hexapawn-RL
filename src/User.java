import java.util.ArrayList;

public class User {
    private final String name;

    public User(String name) {
        this.name = name;

        if(this.name == "Human"){
            humanPlayer();
        }
        else {
            roboPlayer();
        }
    }

    public void humanPlayer() {
        
    }

    public final String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
