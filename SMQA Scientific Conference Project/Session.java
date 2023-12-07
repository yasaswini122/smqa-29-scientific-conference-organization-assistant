import java.util.ArrayList;
import java.util.List;

class Session {
    private String title;
    private String description;

    public Session(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}