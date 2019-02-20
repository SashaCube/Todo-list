package com.oleksandr.havryliuk.todo_list.auth.signin;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.oleksandr.havryliuk.todo_list.R;

public class SignInView implements SignInContract.ISignInView {
    private Button signInBtn;
    private TextView signUpTv, forgotPasswordTv;
    private EditText emailEt, passwordEt;
    private ImageView googleBtn;

    private SignInContract.ISignInPresenter presenter;

    public SignInView() {

    }

    public void setPresenter(SignInContract.ISignInPresenter presenter) {
        this.presenter = presenter;
    }

    public void init(View root) {
        googleBtn = root.findViewById(R.id.google_signIn_button);
        signInBtn = root.findViewById(R.id.signin_btn);
        signUpTv = root.findViewById(R.id.signup_txt);
        forgotPasswordTv = root.findViewById(R.id.forgot_password_txt);
        emailEt = root.findViewById(R.id.email_et);
        passwordEt = root.findViewById(R.id.password_et);
        initListeners();
    }

    private void initListeners() {
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signInClick(emailEt.getText().toString(),
                        passwordEt.getText().toString());
            }
        });

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.googleSignInClick();
            }
        });

        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showSignUp();
            }
        });

        forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showForgotPassword();
            }
        });
    }

    @Override
    public void hideLoginError() {
        emailEt.setError(null);
    }

    @Override
    public void hidePasswordError() {
        passwordEt.setError(null);
    }

    @Override
    public void showLoginError() {
        emailEt.setError("Please enter login!");
    }

    @Override
    public void showPasswordError() {
        passwordEt.setError("Please enter password!");
    }
}