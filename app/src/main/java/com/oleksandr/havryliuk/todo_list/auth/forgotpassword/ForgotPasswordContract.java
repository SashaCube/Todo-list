package com.oleksandr.havryliuk.todo_list.auth.forgotpassword;

import android.view.View;

public interface ForgotPasswordContract {

    interface IForgotPasswordView {
        void init(View root);

        void hideEmailError();

        void showEmailError();

        void setPresenter(IForgotPasswordPresenter presenter);
    }

    interface IForgotPasswordPresenter {
        void showSignIn();

        void sendRecoverCodeClick(String email);
    }
}
