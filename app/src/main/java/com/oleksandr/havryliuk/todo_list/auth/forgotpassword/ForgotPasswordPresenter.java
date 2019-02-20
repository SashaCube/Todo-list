package com.oleksandr.havryliuk.todo_list.auth.forgotpassword;

import android.text.TextUtils;

import com.oleksandr.havryliuk.todo_list.auth.IAuthModel;

public class ForgotPasswordPresenter implements ForgotPasswordContract.IForgotPasswordPresenter {

    private IAuthModel model;
    private ForgotPasswordContract.IForgotPasswordView view;

    public ForgotPasswordPresenter(IAuthModel model,
                                   ForgotPasswordContract.IForgotPasswordView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void sendRecoverCodeClick(String email) {
        if (TextUtils.isEmpty(email)) {
            view.showEmailError();
            return;
        }

        model.sendRecoverCode(email);
    }

    @Override
    public void showSignIn() {
        model.showSignIn();
    }
}
