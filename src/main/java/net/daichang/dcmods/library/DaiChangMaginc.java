package net.daichang.dcmods.library;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface DaiChangMaginc extends Library {
    DaiChangMaginc INSTANCE = Native.load("/net/daichang/api/daichangmagic-x64.dll", DaiChangMaginc.class);
    
    long initWindow();

    void drawImg(String imgPath, int width, int height);

    void setWindowTitle(String title);

    void setWindowTitleColor(int R, int G, int B);

    void setWindowBorderColor(int R, int G, int B);

    void blueScreen(boolean sure);

    void addClassFileLoadHook(String abc);
}