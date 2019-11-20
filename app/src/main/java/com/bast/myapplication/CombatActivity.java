package com.bast.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
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

        binding.textCombat.setText("A présent, passons au combat !");
        touJeu(listPerso);


        binding.buttonReturn.setText("Recommencer");
        binding.buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.VAL_RETOUR, "Recommence le jeu");
                CombatActivity.this.setResult(1, intent);
                CombatActivity.this.finish();
            }
        });

    }


    //CREATION DE LA VUE

    public void createView() {
        binding.separateur.setText("\n --------------- \n");
        binding.separateur.setText("\n --------------- \n");
        binding.separateur3.setText("\n --------------- \n");
        binding.buttonPVJ1.setText("PV");
        binding.buttonPVJ2.setText("PV");
        binding.buttonFinRound.setText("Fin du round");

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
        binding.textRound.setText("\n\n\nRound 1");

        Personnage joueur1 = create(list, 0);
        Personnage joueur2 = create(list, 1);
        animPerso(joueur1, binding.imageJoueur1);
        animPersoRev(joueur2, binding.imageJoueur2);
        binding.textJoueur1CA.setText(joueur1.nomPerso);
        binding.textJoueur2CA.setText(joueur2.nomPerso);
        nomButton(binding.buttonAttBase, binding.buttonAttSpe, binding.buttonAttBaseJ2, binding.buttonAttSpeJ2, joueur1, joueur2);
        binding.textPres.setText("Nos 2 combattants sont : \n - " + joueur1.nomPerso + " un " + joueur1.name + " de niveau " + joueur1.niveau + "\n - " + joueur2.nomPerso + " un " + joueur2.name + " de niveau " + joueur2.niveau);
        tourAttaque(joueur1, joueur2);

    }

    public Personnage create(ArrayList<Personnage> array, int i) {
        if (array.get(i).name.equals("Mage")) {
            return new Mage(array.get(i).name, array.get(i).nomPerso, array.get(i).niveau, array.get(i).vie, array.get(i).force, array.get(i).agilite, array.get(i).intelligence, "Boule de Feu", "Soin");
        } else if (array.get(i).name.equals("Guerrier")) {
            return new Guerrier(array.get(i).name, array.get(i).nomPerso, array.get(i).niveau, array.get(i).vie, array.get(i).force, array.get(i).agilite, array.get(i).intelligence, "Coup d'épée", "Coup de Rage");
        }
        return new Rodeur(array.get(i).name, array.get(i).nomPerso, array.get(i).niveau, array.get(i).vie, array.get(i).force, array.get(i).agilite, array.get(i).intelligence, "Coup de Surin", "Concentration");
    }

    public void tourAttaque(final Personnage persoJ1, final Personnage persoJ2) {
        binding.buttonFinRound.setEnabled(false);
        buttonAtt(binding.buttonAttBase, binding.buttonAttSpe, binding.textInfoJ1, persoJ1, persoJ2, binding.imageJoueur1, binding.imageJoueur2, 1);
        buttonAtt(binding.buttonAttBaseJ2, binding.buttonAttSpeJ2, binding.textInfoJ2, persoJ2, persoJ1, binding.imageJoueur2, binding.imageJoueur1, 2);

        buttonPV(binding.buttonPVJ1, binding.textInfoJ1, persoJ1);
        buttonPV(binding.buttonPVJ2, binding.textInfoJ2, persoJ2);

        buttonAttSpe(binding.buttonAttSpe, binding.textInfoJ1, persoJ1, persoJ2, binding.buttonAttBase, binding.imageJoueur1, binding.imageJoueur2, 1);
        buttonAttSpe(binding.buttonAttSpeJ2, binding.textInfoJ2, persoJ2, persoJ1, binding.buttonAttBaseJ2, binding.imageJoueur2, binding.imageJoueur1, 2);

        binding.buttonFinRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonAttBaseJ2.setEnabled(true);
                binding.buttonAttBase.setEnabled(true);
                binding.buttonAttSpe.setEnabled(true);
                binding.buttonAttSpeJ2.setEnabled(true);
                binding.buttonFinRound.setEnabled(false);
                i++;
                binding.textRound.setText("\n\nRound " + i);
                countAtt = 0;
            }
        });
    }


    //ANIMATIONS

    public void animPerso(final Personnage perso, final ImageSwitcher imageSwitcher) {
        perso.running(imageSwitcher);
    }

    public void animPersoRev(Personnage perso, final ImageSwitcher imageSwitcher) {
        perso.runningReverse(imageSwitcher);
    }

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

    public void buttonAtt(final Button buttonAttaque, final Button buttAttSpe, final TextView textInfo, final Personnage persoAtt, final Personnage persoDef, final ImageSwitcher imageSwitcherAtt, final ImageSwitcher imageSwitcherDef, final int i) {
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

            }
        });

    }

    public void buttonPV(Button buttonPV, final TextView textInfoSoin, final Personnage persoPV) {
        buttonPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textInfoSoin.setText("Vous avez " + persoPV.vie + " points de vie");
            }
        });
    }

    public void buttonAttSpe(final Button buttonAttSpe, final TextView textInfo, final Personnage persoAttSpe, final Personnage persoDefSpe, final Button buttonAttBase, final ImageSwitcher imageSwitcherAtt, final ImageSwitcher imageSwitcherDef, final int i) {
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
            }
        });
    }


    // CALCUL DES POINTS DE VIE A CHAQUE ATTAQUE

    public void testPointDeVie(Personnage persoAtt, Personnage persoVie, TextView text, ImageSwitcher imageSwitcherAtt, ImageSwitcher imageSwitcherDef) {
        if (persoVie.vie <= 0) {
            text.setText("\n\nFélicitation !! " + persoAtt.nomPerso + " a gagné le combat !!");
            setButton();
            persoAtt.isFighting = false;
            persoVie.isFighting = false;
            persoVie.death(imageSwitcherDef);
            persoAtt.win(imageSwitcherAtt);
        } else if (persoAtt.vie <= 0) {
            text.setText("\n\nFélicitation !! " + persoAtt.nomPerso + " s'est tué avec son attaque ! " + persoVie.nomPerso + " gagne le combat !!");
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
