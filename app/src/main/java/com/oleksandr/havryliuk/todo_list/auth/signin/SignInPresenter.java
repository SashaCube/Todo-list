package com.oleksandr.havryliuk.todo_list.auth.signin;

import android.text.TextUtils;
import com.oleksandr.havryliuk.todo_list.auth.IAuthModel;

public class SignInPresenter implements SignInContract.ISignInPresenter {

    private SignInContract.ISignInView view;
    private IAuthModel model;

    public SignInPresenter(IAuthModel model, SignInContract.ISignInView view) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void signInClick(String email, String password) {
        view.hideLoginError();
        view.hidePasswordError();

        if (TextUtils.isEmpty(email)) {
            view.showLoginError();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            view.showPasswordError();
            return;
        }

        model.signIn(email, password);
    }


    @Override
    public void googleSignInClick() {
        model.googleSignIn();
    }

    @Override
    public void showSignUp() {
        model.showSignUp();
    }

    @Override
    public void showForgotPassword() {
        model.showForgotPassword();
    }
}