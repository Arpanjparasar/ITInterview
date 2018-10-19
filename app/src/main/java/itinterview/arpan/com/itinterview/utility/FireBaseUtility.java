package itinterview.arpan.com.itinterview.utility;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import itinterview.arpan.com.itinterview.ITInterviewApplication;
import itinterview.arpan.com.itinterview.listener.FetchAboutListener;
import itinterview.arpan.com.itinterview.tables.About;
import itinterview.arpan.com.itinterview.tables.ContactUs;

public class FireBaseUtility {


    public static void saveData(About about) {

//Write code to save data to firebase
        String userId = ITInterviewApplication.getFireBaseDatabase().push().getKey();
        ITInterviewApplication.getFireBaseDatabase().child("about").setValue(about);


    }

    public static void saveContact(ContactUs fdsf) {
        ITInterviewApplication.getFireBaseDatabase().child("contact").setValue(fdsf);
    }

    public static void getAbout(final FetchAboutListener fetchAboutListener) {

        ITInterviewApplication.getFireBaseDatabase().child("about").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                About value = dataSnapshot.getValue(About.class);
                System.out.println("print"+value.toString());
                fetchAboutListener.onSuccess(value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fetchAboutListener.onFailure(databaseError.toException());
                // ...
            }
        });
    }
}
