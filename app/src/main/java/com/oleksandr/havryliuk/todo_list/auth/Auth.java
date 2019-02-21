package com.oleksandr.havryliuk.todo_list.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.oleksandr.havryliuk.todo_list.R;
import com.oleksandr.havryliuk.todo_list.utils.PreferenceManager;


public class Auth {
    //Google Auth
    private final static int RC_SIGN_IN = 1;
    public final static String EMAIL = "email";

    private static GoogleSignInClient googleSignInClient;

    public static void saveEmail(String email){
        PreferenceManager.setString(EMAIL, email);
    }

    public static void signIn(final String email, String password, final Activity activity) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //if login sucess
                    Toast.makeText(activity, "Success Sign in", Toast.LENGTH_SHORT).show();
                    auth.getCurrentUser();
                    saveEmail(email);
                    startMainActivity(activity);
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(activity, "Failed Sign in", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void signUp(final String email, String password, final Activity activity) {
        //register user with Firebase
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity, "You are signed up.", Toast.LENGTH_SHORT).show();
                    sendSignUpConfirm(auth.getCurrentUser(), activity);
                    saveEmail(email);
                    startMainActivity(activity);
                } else {
                    Toast.makeText(activity, "Failed sign up", Toast.LENGTH_SHORT).show();
                    //currentUser = null;
                }
            }
        });
    }

    private static void sendSignUpConfirm(FirebaseUser user, final Context context) {
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Email sent: success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Email isn't sent: error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void signOut() {
        if (googleSignInClient != null) {
            googleSignInClient.revokeAccess();
        }

        FirebaseAuth.getInstance().signOut();
    }

    public static void googleSignIn(Activity activity) {
        GoogleSignInClient googleSignInClient = getGoogleClient(activity);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public static void sendRecoverCode(String email, final Context context) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Email sent: success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Email not sent: error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private static void firebaseAuthWithGoogle(final GoogleSignInAccount acct,
                                               final Activity activity) {
        //connecting to firebase
        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(activity, "Google authinication success", Toast.LENGTH_SHORT).show();
                            auth.getCurrentUser();
                            saveEmail(acct.getEmail());
                            startMainActivity(activity);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(activity, "Google authinication failed(Firebase part)", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data,
                                        Activity activity) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Auth.firebaseAuthWithGoogle(account, activity);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(activity, "Google authinication failed(Google part)", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean isUserAuth() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            return false;
        }
        return true;
    }

    public static GoogleSignInClient getGoogleClient(Context context) {
        if (googleSignInClient == null) {
            //Google
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            googleSignInClient = GoogleSignIn.getClient(context, gso);
        }
        return googleSignInClient;
    }

    public static void startMainActivity(Activity activity) {
        ((AuthenticationActivity) activity).startTaskActivity();
    }
}
