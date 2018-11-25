package com.arpan.itinterview.listener;


import com.arpan.itinterview.tables.ContactUs;

public interface FetchContactUs {
    void onContactSuccess(ContactUs contactUs);

    void onContactFailure(Exception exception);
}