package com.example.coursework.interfaces;


public interface SceneOpener {
    void openNewScene(String title, String fxmlPath);
    void openNewSceneAndCloseCurrent(String title, String fxmlPath);
}
