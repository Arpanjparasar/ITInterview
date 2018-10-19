package itinterview.arpan.com.itinterview.listener;


import itinterview.arpan.com.itinterview.tables.ContactUs;

public interface FetchContactUs {
    void onContactSuccess(ContactUs contactUs);

    void onContactFailure(Exception exception);
}