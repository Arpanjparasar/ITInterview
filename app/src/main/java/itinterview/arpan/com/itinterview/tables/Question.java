package itinterview.arpan.com.itinterview.tables;

public class Question {

    String domain;
    String company;

    String question;

    String answer = "Not yet Answered";

    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Question() {

    }

    public Question(String domain, String company, String question) {

        this.domain = domain;
        this.company = company;
        this.question = question;
    }
}
