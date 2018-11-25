package com.arpan.itinterview.listener;

import com.arpan.itinterview.tables.About;

public interface FetchAboutListener {

    void onSuccess(About about);

    void onFailure(Exception exception);
}
