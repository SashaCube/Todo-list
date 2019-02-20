package com.oleksandr.havryliuk.todo_list.auth.signup;

import android.text.TextUtils;

import com.oleksandr.havryliuk.todo_list.auth.IAuthModel;

public class SignUpPresenter implements SignUpContract.ISignUpPresenter {

    private IAuthModel model;
    private SignUpContract.ISignUpView view;

    public SignUpPresenter(IAuthModel model, SignUpContract.ISignUpView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void signUpClick(String email, String password, String confirm) {
        view.hideEmailError();
        view.hidePasswordError();
        view.hideConfirmError();

        if (TextUtils.isEmpty(email)) {
            view.showEmailError();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            view.showPasswordError();
            return;
        }
        if (TextUtils.isEmpty(confirm)) {
            view.showConfirmError();
            return;
        }
        if (!password.equals(confirm)) {
            view.showConfirmError();
            return;
        }

        model.signUp(email, password);
    }

    @Override
    public void showSignIn() {
        model.showSignIn();
    }
}
