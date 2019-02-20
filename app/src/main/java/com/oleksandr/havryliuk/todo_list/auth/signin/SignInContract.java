package com.oleksandr.havryliuk.todo_list.auth.signin;

import android.view.View;

public interface SignInContract {

    interface ISignInView {
        void init(View root);

        void setPresenter(ISignInPresenter presenter);

        void hideLoginError();

        void hidePasswordError();

        void showLoginError();

        void showPasswordError();
    }

    interface ISignInPresenter {
        void signInClick(String email, String password);

        void googleSignInClick();

        void showSignUp();

        void showForgotPassword();
    }
}
