import java.util.ArrayList;
import java.util.List;

class Poll {
    private String question;
    private String options;

    public Poll(String question, String options) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptions() {
        return options;
    }
}