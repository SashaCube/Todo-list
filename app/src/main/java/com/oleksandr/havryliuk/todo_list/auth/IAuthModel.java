package com.oleksandr.havryliuk.todo_list.auth;

public interface IAuthModel {

    void showSignIn();

    void googleSignIn();

    void showSignUp();

    void showForgotPassword();

    void sendRecoverCode(String email);

    void signIn(String email, String password);

    void signUp(String email, String password);

    void signOut();
}
