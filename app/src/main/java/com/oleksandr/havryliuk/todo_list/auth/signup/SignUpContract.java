package com.oleksandr.havryliuk.todo_list.auth.signup;

import android.view.View;

public interface SignUpContract {

    interface ISignUpView {
        void init(View root);

        void setPresenter(ISignUpPresenter presenter);

        void hideEmailError();

        void showEmailError();

        void showPasswordError();

        void hidePasswordError();

        void showConfirmError();

        void hideConfirmError();
    }

    interface ISignUpPresenter {
        void signUpClick(String email, String password, String confirm);

        void showSignIn();
    }
}
