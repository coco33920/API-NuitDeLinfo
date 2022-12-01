package gay.bacoin.api.objects;

public class Answer {

    private long code;
    private String answer;

    public Answer(String code, String answer){
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public long getCode() {
        return code;
    }
}
