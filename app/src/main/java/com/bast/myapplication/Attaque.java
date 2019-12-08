package com.bast.myapplication;

import android.widget.ProgressBar;
import android.widget.TextView;

public interface Attaque {

    void attaqueDeBase(Personnage defenseur, TextView textView, ProgressBar progressBarAtt, ProgressBar progressBarDef);
    void attaqueSpeciale(Personnage defenseur, TextView textView, ProgressBar progressBarAtt, ProgressBar progressBarDef);


}
