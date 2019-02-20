package com.oleksandr.havryliuk.todo_list.auth.forgotpassword;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oleksandr.havryliuk.todo_list.R;

public class ForgotPasswordView implements ForgotPasswordContract.IForgotPasswordView {

    private ForgotPasswordContract.IForgotPasswordPresenter presenter;
    private EditText emailEt;

    @Override
    public void init(View root) {
        emailEt = root.findViewById(R.id.email_et);

        Button sendRecoverCodeBtn = root.findViewById(R.id.send_recover_code_btn);
        TextView backToSignInTv = root.findViewById(R.id.back_signin_txt);

        sendRecoverCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendRecoverCodeClick(emailEt.getText().toString());
            }
        });

        backToSignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showSignIn();
            }
        });
    }

    @Override
    public void hideEmailError() {
        emailEt.setError(null);
    }

    @Override
    public void showEmailError() {
        emailEt.setError("Please enter login!");
    }

    @Override
    public void setPresenter(ForgotPasswordContract.IForgotPasswordPresenter presenter) {
        this.presenter = presenter;
    }
}
