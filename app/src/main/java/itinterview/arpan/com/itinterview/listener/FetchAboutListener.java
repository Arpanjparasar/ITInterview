package itinterview.arpan.com.itinterview.listener;

import itinterview.arpan.com.itinterview.tables.About;

public interface FetchAboutListener {

    void onSuccess(About about);

    void onFailure(Exception exception);
}
