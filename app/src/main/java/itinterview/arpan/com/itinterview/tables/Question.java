package itinterview.arpan.com.itinterview.tables;

import itinterview.arpan.com.itinterview.utility.IViewConstants;

public class Question {

    String Domains;
    String Companies;

    String question;

    String answer = IViewConstants.TAP_TO_ANSWER;

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
        return Domains;
    }

    public void setDomain(String domain) {
        this.Domains = domain;
    }

    public String getCompany() {
        return Companies;
    }

    public void setCompany(String company) {
        this.Companies = company;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Question() {

    }

    public Question(String domains, String company, String question) {

        this.Domains = domains;
        this.Companies = company;
        this.question = question;
    }
}
