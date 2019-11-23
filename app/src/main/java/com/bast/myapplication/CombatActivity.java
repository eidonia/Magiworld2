package com.bast.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bast.myapplication.databinding.ActivityCombatBinding;

import java.util.ArrayList;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class CombatActivity extends Activity {

    int i = 1;
    int countAtt = 0;
    int animationCounterJ1 = 1;
    Handler imgSwitchHandler;
    private ActivityCombatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/MedievalSharp-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        binding = ActivityCombatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createView();


        ArrayList<Personnage> listPerso = (ArrayList) this.getIntent().getParcelableArrayListExtra("listPerso");

        binding.textCombat.setText(getResources().getString(R.string.nowFight));
        touJeu(listPerso);


        binding.buttonReturn.setText(getResources().getString(R.string.restart));
        binding.buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.VAL_RETOUR, R.string.restartIntent);
                CombatActivity.this.setResult(1, intent);
                CombatActivity.this.finish();
            }
        });

    }


    //CREATION DE LA VUE

    public void createView() {
        binding.separateur.setText(getResources().getString(R.string.separateur));
        binding.separateur.setText(getResources().getString(R.string.separateur));
        binding.separateur3.setText(getResources().getString(R.string.separateur));
        binding.buttonPVJ1.setText(getResources().getString(R.string.healthPoint));
        binding.buttonPVJ2.setText(getResources().getString(R.string.healthPoint));
        binding.buttonFinRound.setText(getResources().getString(R.string.endRound));
        binding.determinatebarJ1.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        binding.determinatebarJ2.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

        binding.imageJoueur1.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                return myView;
            }
        });

        binding.imageJoueur2.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                return myView;

            }
        });

        binding.imgSwitchFirework.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                return myView;

            }
        });


    }

    //CREATION  DU JEU

    public void touJeu(ArrayList<Personnage> list) {
        binding.textRound.setText(getResources().getString(R.string.round, 1));

        Personnage joueur1 = create(list, 0);
        Personnage joueur2 = create(list, 1);
        joueur1.running(binding.imageJoueur1);
        joueur2.runningReverse(binding.imageJoueur2);
        binding.textJoueur1CA.setText(joueur1.nomPerso);
        binding.textJoueur2CA.setText(joueur2.nomPerso);
        binding.textVieJ1.setText(getResources().getString(R.string.life, joueur1.vie));
        binding.textVieJ2.setText(getResources().getString(R.string.life, joueur2.vie));
        binding.determinatebarJ1.setMax(joueur1.vie);
        binding.determinatebarJ2.setMax(joueur2.vie);
        nomButton(binding.buttonAttBase, binding.buttonAttSpe, binding.buttonAttBaseJ2, binding.buttonAttSpeJ2, joueur1, joueur2);
        binding.textPres.setText(getResources().getString(R.string.describeFighters, joueur1.nomPerso, joueur1.name, joueur1.niveau, joueur2.nomPerso, joueur2.name, joueur2.niveau));
        tourAttaque(joueur1, joueur2);

    }

    public Personnage create(ArrayList<Personnage> array, int i) {
        if (array.get(i).name.equals(getResources().getString(R.string.nameClassMage))) {
            return new Mage(array.get(i).name, array.get(i).nomPerso, array.get(i).niveau, array.get(i).vie, array.get(i).force, array.get(i).agilite, array.get(i).intelligence, getResources().getString(R.string.attBaseMage), getResources().getString(R.string.attSpeMage));
        } else if (array.get(i).name.equals(getResources().getString(R.string.nameClassWar))) {
            return new Guerrier(array.get(i).name, array.get(i).nomPerso, array.get(i).niveau, array.get(i).vie, array.get(i).force, array.get(i).agilite, array.get(i).intelligence, getResources().getString(R.string.attBaseWar), getResources().getString(R.string.attSpeWar));
        }
        return new Rodeur(array.get(i).name, array.get(i).nomPerso, array.get(i).niveau, array.get(i).vie, array.get(i).force, array.get(i).agilite, array.get(i).intelligence, getResources().getString(R.string.attBaseRogue), getResources().getString(R.string.attSpeRogue));
    }

    public void tourAttaque(final Personnage persoJ1, final Personnage persoJ2) {
        binding.buttonFinRound.setEnabled(false);
        buttonAtt(binding.buttonAttBase, binding.buttonAttSpe, binding.textInfoJ1, binding.textVieJ1, binding.textVieJ2, persoJ1, persoJ2, binding.imageJoueur1, binding.imageJoueur2, binding.determinatebarJ1, binding.determinatebarJ2, 1);
        buttonAtt(binding.buttonAttBaseJ2, binding.buttonAttSpeJ2, binding.textInfoJ2, binding.textVieJ2, binding.textVieJ1, persoJ2, persoJ1, binding.imageJoueur2, binding.imageJoueur1, binding.determinatebarJ2, binding.determinatebarJ1, 2);

        buttonPV(binding.buttonPVJ1, binding.textInfoJ1, persoJ1);
        buttonPV(binding.buttonPVJ2, binding.textInfoJ2, persoJ2);

        buttonAttSpe(binding.buttonAttSpe, binding.textInfoJ1, binding.textVieJ1, binding.textVieJ2, persoJ1, persoJ2, binding.buttonAttBase, binding.imageJoueur1, binding.imageJoueur2, binding.determinatebarJ1, binding.determinatebarJ2, 1);
        buttonAttSpe(binding.buttonAttSpeJ2, binding.textInfoJ2, binding.textVieJ2, binding.textVieJ1, persoJ2, persoJ1, binding.buttonAttBaseJ2, binding.imageJoueur2, binding.imageJoueur1, binding.determinatebarJ2, binding.determinatebarJ1, 2);

        binding.buttonFinRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonAttBaseJ2.setEnabled(true);
                binding.buttonAttBase.setEnabled(true);
                binding.buttonAttSpe.setEnabled(true);
                binding.buttonAttSpeJ2.setEnabled(true);
                binding.buttonFinRound.setEnabled(false);
                i++;
                binding.textRound.setText(getResources().getString(R.string.round, i));
                countAtt = 0;
            }
        });
    }


    //ANIMATIONS

    public void animFirework() {
        imgSwitchHandler = new Handler(Looper.getMainLooper());
        imgSwitchHandler.post(new Runnable() {
            @Override
            public void run() {
                switch (animationCounterJ1++) {
                    case 1:
                        binding.imgSwitchFirework.setImageResource(R.mipmap.firework_red0);
                        break;
                    case 2:
                        binding.imgSwitchFirework.setImageResource(R.mipmap.firework_red1);
                        break;
                    case 3:
                        binding.imgSwitchFirework.setImageResource(R.mipmap.firework_red2);
                        break;
                    case 4:
                        binding.imgSwitchFirework.setImageResource(R.mipmap.firework_red3);
                        break;
                    case 5:
                        binding.imgSwitchFirework.setImageResource(R.mipmap.firework_red4);
                        break;
                    case 6:
                        binding.imgSwitchFirework.setImageResource(R.mipmap.firework_red5);
                        break;
                    case 7:
                        binding.imgSwitchFirework.setImageResource(R.mipmap.firework_red6);
                        break;
                    case 8:
                        binding.imgSwitchFirework.setImageResource(R.mipmap.firework_red7);
                        break;
                }
                animationCounterJ1 %= 9;
                if (animationCounterJ1 == 0) animationCounterJ1 = 1;
                imgSwitchHandler.postDelayed(this, 300);

            }
        });
    }

    // GESTION DES BUTTONS

    public void buttonAtt(final Button buttonAttaque, final Button buttAttSpe, final TextView textInfo, final TextView textVieAtt, final TextView textVieDef, final Personnage persoAtt, final Personnage persoDef, final ImageSwitcher imageSwitcherAtt, final ImageSwitcher imageSwitcherDef, final ProgressBar pBarAtt, final ProgressBar pBarDef, final int i) {
        buttonAttaque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persoAtt.attaqueDeBase(persoDef, textInfo);
                buttonAttaque.setEnabled(false);
                if (persoDef.vie > 0) {
                    if (i == 1) {
                        persoAtt.attBase(imageSwitcherAtt);
                    } else {
                        persoAtt.attBaseReverse(imageSwitcherAtt);
                    }
                }


                testPointDeVie(persoAtt, persoDef, binding.textResult, imageSwitcherAtt, imageSwitcherDef);

                buttAttSpe.setEnabled(false);
                countAtt++;
                if (countAtt == 2 && persoDef.vie > 0)
                    binding.buttonFinRound.setEnabled(true);

                if(persoAtt.vie <= 0){
                    textVieAtt.setText(getResources().getString(R.string.life, 0));
                }else {
                    textVieAtt.setText(getResources().getString(R.string.life, persoAtt.vie));
                }

                if(persoDef.vie <= 0){
                    textVieDef.setText(getResources().getString(R.string.life, 0));
                }else {
                    textVieDef.setText(getResources().getString(R.string.life, persoDef.vie));
                }

                progressBarVie(persoAtt, persoDef, pBarAtt, pBarDef);

            }
        });

    }

    public void buttonPV(Button buttonPV, final TextView textInfoSoin, final Personnage persoPV) {
        buttonPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInfoSoin.setText(getResources().getString(R.string.lifeText, persoPV.vie));
            }
        });
    }

    public void buttonAttSpe(final Button buttonAttSpe, final TextView textInfo,  final TextView textVieAtt, final TextView textVieDef, final Personnage persoAttSpe, final Personnage persoDefSpe, final Button buttonAttBase, final ImageSwitcher imageSwitcherAtt, final ImageSwitcher imageSwitcherDef, final ProgressBar pBarAttSpe, final ProgressBar pBarDefSpe, final int i) {
        buttonAttSpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persoAttSpe.attaqueSpeciale(persoDefSpe, textInfo);
                if (persoAttSpe.vie > 0 && persoDefSpe.vie > 0) {
                    if (i == 1) {
                        persoAttSpe.attSpe(imageSwitcherAtt);
                    } else {
                        persoAttSpe.attSpeReverse(imageSwitcherAtt);
                    }
                }

                countAtt++;
                buttonAttSpe.setEnabled(false);
                buttonAttBase.setEnabled(false);
                testPointDeVie(persoAttSpe, persoDefSpe, binding.textResult, imageSwitcherAtt, imageSwitcherDef);

                if (countAtt == 2 && persoDefSpe.vie > 0)
                    binding.buttonFinRound.setEnabled(true);

                if(persoAttSpe.vie <= 0){
                    textVieAtt.setText(getResources().getString(R.string.life, 0));
                }else {
                    textVieAtt.setText(getResources().getString(R.string.life, persoAttSpe.vie));
                }

                if(persoDefSpe.vie <= 0){
                    textVieDef.setText(getResources().getString(R.string.life, 0));
                }else {
                    textVieDef.setText(getResources().getString(R.string.life, persoDefSpe.vie));
                }

                progressBarVie(persoAttSpe, persoDefSpe, pBarAttSpe, pBarDefSpe);
            }
        });
    }

    // PROGRESS BAR

    public void progressBarVie(Personnage persoAtt, Personnage persoDef, ProgressBar pBarAtt, ProgressBar pBarDef){
        int vieMaxAtt = 5 * persoAtt.niveau;
        int vieMaxDef = 5 * persoDef.niveau;

        pBarAtt.setProgress(persoAtt.vie);
        pBarDef.setProgress(persoDef.vie);

        if (persoAtt.vie <= vieMaxAtt / 2){
            pBarAtt.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        }else if (persoAtt.vie <= vieMaxAtt / 4){
            pBarAtt.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }

        if (persoDef.vie <= vieMaxDef / 2){
            pBarDef.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        }else if (persoDef.vie <= vieMaxDef / 4){
            pBarDef.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }



    }


    // CALCUL DES POINTS DE VIE A CHAQUE ATTAQUE

    public void testPointDeVie(Personnage persoAtt, Personnage persoVie, TextView text, ImageSwitcher imageSwitcherAtt, ImageSwitcher imageSwitcherDef) {
        if (persoVie.vie <= 0) {
            text.setText(getResources().getString(R.string.winText, persoAtt.nomPerso, persoVie.nomPerso));
            setButton();
            persoAtt.isFighting = false;
            persoVie.isFighting = false;
            persoVie.death(imageSwitcherDef);
            persoAtt.win(imageSwitcherAtt);
        } else if (persoAtt.vie <= 0) {
            text.setText(getResources().getString(R.string.winBySuicideText, persoVie.nomPerso, persoAtt.nomPerso));
            setButton();
            persoAtt.isFighting = false;
            persoVie.isFighting = false;
            persoAtt.death(imageSwitcherAtt);
            persoVie.win(imageSwitcherDef);
        }
    }


    // NOM DES BUTTONS SUIVANT LES CLASSES

    public void nomButton(Button buttonJ1Base, Button buttonJ1Spe, Button buttonJ2Base, Button buttonJ2Spe, Personnage pJ1, Personnage pJ2) {
        buttonJ1Base.setText(pJ1.nomAttBase);
        buttonJ1Spe.setText(pJ1.nomAttSpe);
        buttonJ2Base.setText(pJ2.nomAttBase);
        buttonJ2Spe.setText(pJ2.nomAttSpe);
    }



    public void setButton() {
        binding.buttonFinRound.setEnabled(false);
        binding.buttonAttSpe.setEnabled(false);
        binding.buttonAttSpeJ2.setEnabled(false);
        binding.buttonAttBase.setEnabled(false);
        binding.buttonAttBaseJ2.setEnabled(false);
        binding.buttonPVJ1.setEnabled(false);
        binding.buttonPVJ2.setEnabled(false);
        animFirework();
    }


    // CALLIGRAPHY

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
