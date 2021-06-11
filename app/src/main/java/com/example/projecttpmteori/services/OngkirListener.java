package com.example.projecttpmteori.services;

public interface OngkirListener<T> {
    void onResponse(T items);
    void onFailure(String message);
}
