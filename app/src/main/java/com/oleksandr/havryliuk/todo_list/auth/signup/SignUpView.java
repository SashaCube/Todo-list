package com.oleksandr.havryliuk.todo_list.auth.signup;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oleksandr.havryliuk.todo_list.R;

public class SignUpView implements SignUpContract.ISignUpView {

    private Button signUpBtn;
    private TextView signInTv;
    private EditText emailEt, passwordEt, confirmPasswordEt;
    private SignUpContract.ISignUpPresenter presenter;

    @Override
    public void init(View root) {
        signUpBtn = root.findViewById(R.id.signup_btn);
        signInTv = root.findViewById(R.id.signin_txt);
        emailEt = root.findViewById(R.id.email_et);
        passwordEt = root.findViewById(R.id.password_et);
        confirmPasswordEt = root.findViewById(R.id.confirm_password_et);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signUpClick(
                        emailEt.getText().toString(),
                        passwordEt.getText().toString(),
                        confirmPasswordEt.getText().toString());
            }
        });

        signInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showSignIn();
            }
        });
    }

    @Override
    public void setPresenter(SignUpContract.ISignUpPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void hideEmailError() {
        emailEt.setError(null);
    }

    @Override
    public void showEmailError() {
        emailEt.setError("Please enter Email!");
    }

    @Override
    public void showPasswordError() {
        passwordEt.setError("Please enter password!");
    }

    @Override
    public void hidePasswordError() {
        passwordEt.setError(null);
    }

    @Override
    public void showConfirmError() {
        confirmPasswordEt.setError("Passwords aren't equal!");
    }

    @Override
    public void hideConfirmError() {
        confirmPasswordEt.setError(null);
    }
}
