package gay.bacoin.api.gsonpayloads;

public class Answer {

    private final int code;
    private final String answer;

    public Answer(int code, String answer) {
        this.code = code;
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public int getCode() {
        return code;
    }
}
