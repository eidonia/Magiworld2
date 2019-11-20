package com.bast.myapplication;

import android.widget.ImageSwitcher;

public interface Animation {
    void running(final ImageSwitcher imageSwitcher);
    void runningReverse(final ImageSwitcher imageSwitcher);
    void death(final ImageSwitcher imageSwitcher);
    void win(final ImageSwitcher imageSwitcher);
    void attBase(final ImageSwitcher imageSwitcher);
    void attBaseReverse(final ImageSwitcher imageSwitcher);
    void attSpe(final ImageSwitcher imageSwitcher);
    void attSpeReverse(final ImageSwitcher imageSwitcher);

}
