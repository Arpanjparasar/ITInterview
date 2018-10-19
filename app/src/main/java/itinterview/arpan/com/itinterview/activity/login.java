package itinterview.arpan.com.itinterview.activity;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import itinterview.arpan.com.itinterview.R;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN=0;
    private Button btn_signout;
    private com.google.android.gms.common.SignInButton btn_signin;
    private TextView nameText;
    private TextView emailText;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CallbackManager callbackManager;
        final String TAG = "MainActivity";
        // Initialize your instance of callbackManager//
        callbackManager = CallbackManager.Factory.create();
        // Register your callback//
        LoginManager.getInstance().registerCallback(callbackManager,

                // If the login attempt is successful, then call onSuccess and pass the LoginResult//
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // Print the user’s ID and the Auth Token to Android Studio’s Logcat Monitor//
                        Log.d(TAG, "User ID: " +
                                loginResult.getAccessToken().getUserId() + "\n" +
                                "Auth Token: " + loginResult.getAccessToken().getToken());
                    }

                    // If the user cancels the login, then call onCancel//
                    @Override
                    public void onCancel() {
                    }

                    // If an error occurs, then call onError//
                    @Override
                    public void onError(FacebookException exception) {
                    }
                });






        btn_signin=(com.google.android.gms.common.SignInButton)findViewById(R.id.signin_btn);
        btn_signout=(Button)findViewById(R.id.signout_btn);
        nameText =(TextView)findViewById(R.id.name);
        emailText=(TextView)findViewById(R.id.email);

        //init google sign in option
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        //connection failed Listener
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    public void onConnectionFailed(ConnectionResult connectionResult){

                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();


        //get instance for firebase authentication
        mAuth = FirebaseAuth.getInstance();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                     Intent intent = new Intent(login.this,HomeScreen.class);
                     //intent.putExtra("name",);
                     startActivity(intent);
                     finish();
                }else{
                    //User is Signed out
                    Toast.makeText(login.this,"No user signed in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        //add Listenners to button
        btn_signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signIn();
            }
        });

      /*  btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>(){
                            public  void onResult(Status status){
                                btn_signin.setVisibility(View.VISIBLE);
                                btn_signout.setVisibility(View.GONE);
                                emailText.setText(" ".toString());
                                nameText.setText(" ".toString());
                            }
                        }
                );

            }
        });*/
    }
    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data ){
        super.onActivityResult(requestCode,resultCode,data);

        //Result returned from launching the Intent from GoogleSignInApi .getSinInIntent();
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                //Google Sign In was successful,authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            }else{
                //In case Google Sign In is failed place your code here
            }
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }
    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListner!=null){
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct){
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if sign in fails, display a message to user.
                        //if sign in succeds the auth state listener will be notified and
                        //logic to handle the signed in user can be handled in the listener
                        if(!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Authentication failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}

