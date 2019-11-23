package com.bast.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bast.myapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends Activity {
    public final static String VAL_RETOUR = "com.bast.magiworld.VALRETOUR";
    ArrayList<Personnage> arrayPerso = new ArrayList<>();
    private ActivityMainBinding binding;

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
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createView();

        binding.buttonCombat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(arrayPerso.isEmpty()){
                    Toast.makeText(MainActivity.this, R.string.toastCreate, Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, CombatActivity.class);
                    intent.putParcelableArrayListExtra("listPerso", (ArrayList)arrayPerso);
                    startActivityForResult(intent, 0);
                }
            }
        });

        binding.buttonRAZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetJeu();
            }
        });

    }

    //CREATION DE LA VUE

    public void createView(){
        binding.textBienvenue.setText(R.string.app_name);

        binding.textNomPerso.setText(R.string.nameChar);
        binding.editNomPerso.getHint();
        binding.editNomPerso.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        binding.editNomPerso.setMaxLines(1);

        binding.textNivJ1.setText(R.string.levelChar);
        binding.editNivJ1.getHint();
        binding.editNivJ1.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.editNivJ1.setMaxLines(1);

        binding.textForceJ1.setText(R.string.strengthChar);
        binding.editForceJ1.getHint();
        binding.editForceJ1.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.editForceJ1.setMaxLines(1);

        binding.textAgiJ1.setText(R.string.agilityChar);
        binding.editAgiJ1.getHint();
        binding.editAgiJ1.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.editAgiJ1.setMaxLines(1);

        binding.textIntJ1.setText(R.string.intelChar);
        binding.editIntJ1.getHint();
        binding.editIntJ1.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.editIntJ1.setMaxLines(1);

        binding.buttonCreateJ1.setText(R.string.creaChar);

        binding.textNomPerso2.setText(R.string.nameChar);
        binding.editNomPerso2.getHint();
        binding.editNomPerso2.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        binding.editNomPerso2.setMaxLines(1);

        binding.textNivJ2.setText(R.string.levelChar);
        binding.editNivJ2.getHint();
        binding.editNivJ2.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.editNivJ2.setMaxLines(1);

        binding.textForceJ2.setText(R.string.strengthChar);
        binding.editForceJ2.getHint();
        binding.editForceJ2.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.editForceJ2.setMaxLines(1);

        binding.textAgiJ2.setText(R.string.agilityChar);
        binding.editAgiJ2.getHint();
        binding.editAgiJ2.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.editAgiJ2.setMaxLines(1);

        binding.textIntJ2.setText(R.string.intelChar);
        binding.editIntJ2.getHint();
        binding.editIntJ2.setInputType(InputType.TYPE_CLASS_NUMBER);
        binding.editIntJ2.setMaxLines(1);

        binding.buttonCreateJ2.setText(R.string.creaChar);

        binding.buttonCombat.setText(R.string.fight);
        binding.buttonRAZ.setText(R.string.razGame);

        binding.buttonCreateJ1.setOnClickListener(createListener);
        binding.buttonCreateJ2.setOnClickListener(createListener);

        binding.rButtonMageJ1.setOnClickListener(backChanged);
        binding.rButtonWarJ1.setOnClickListener(backChanged);
        binding.rButtonRogueJ1.setOnClickListener(backChanged);
        binding.rButtonMageJ2.setOnClickListener(backChanged);
        binding.rButtonRogueJ2.setOnClickListener(backChanged);
        binding.rButtonWarJ2.setOnClickListener(backChanged);
    }


    // CREATION DES PERSONNAGES

    public View.OnClickListener createListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.buttonCreateJ1 :
                    String nomJ1 = "";
                    if (binding.rGroupJ1.getCheckedRadioButtonId() == R.id.rButtonWarJ1){
                        nomJ1 = getResources().getString(R.string.nameClassWar);
                    }else if (binding.rGroupJ1.getCheckedRadioButtonId() == R.id.rButtonRogueJ1){
                        nomJ1 = getResources().getString(R.string.nameClassRogue);
                    }else if (binding.rGroupJ1.getCheckedRadioButtonId() == R.id.rButtonMageJ1){
                        nomJ1 = getResources().getString(R.string.nameClassMage);
                    }

                    createCharac(binding.editNomPerso, binding.editNivJ1, binding.editForceJ1, binding.editIntJ1, binding.editAgiJ1, binding.textResCreaJ1, nomJ1);
                    break;

                case R.id.buttonCreateJ2 :
                    String nomJ2 = "";
                    if (binding.rGroupJ2.getCheckedRadioButtonId() == R.id.rButtonWarJ2){
                        nomJ2 = getResources().getString(R.string.nameClassWar);
                    }else if (binding.rGroupJ2.getCheckedRadioButtonId() == R.id.rButtonRogueJ2){
                        nomJ2 = getResources().getString(R.string.nameClassRogue);
                    }else if (binding.rGroupJ2.getCheckedRadioButtonId() == R.id.rButtonMageJ2){
                        nomJ2 = getResources().getString(R.string.nameClassMage);
                    }
                    createCharac(binding.editNomPerso2, binding.editNivJ2, binding.editForceJ2, binding.editIntJ2, binding.editAgiJ2, binding.textResCreaJ2, nomJ2);
                    break;
            }
        }
    };

    public void createCharac(EditText editNom, EditText editNiv, EditText editForce, EditText editInt, EditText editAgi, TextView textView, String nomClasse){
        if(editNom.getText().toString().equals("") || editNiv.getText().toString().equals("") || editForce.getText().toString().equals("") || editInt.getText().toString().equals("") || editAgi.getText().toString().equals("")){
            Toast.makeText(MainActivity.this, R.string.toastStatsFill, Toast.LENGTH_LONG).show();
        }else{

            createStats(editNom, editNiv, editForce, editAgi, editInt, textView, nomClasse);
        }

    }

    public void createStats(EditText editNom, EditText editNiv, EditText editForce, EditText editAgi, EditText editIntel, TextView textView, String nomClasse){

        String nomPerso = editNom.getText().toString();

        String niv = editNiv.getText().toString();
        int nivInt = Integer.parseInt(niv);

        int vie = 5*nivInt;

        String force = editForce.getText().toString();
        int forceInt = Integer.parseInt(force);

        String agi = editAgi.getText().toString();
        int agiInt = Integer.parseInt(agi);

        String intel = editIntel.getText().toString();
        int intelInt = Integer.parseInt(intel);

        testCarac(nomPerso, nivInt, vie, forceInt, agiInt, intelInt, textView, nomClasse);

    }

    public void testCarac(String nomPerso, int niv, int vie, int force, int agi, int intel, TextView textview, String nomClasse){
        if ((force+agi+intel) > niv){
            Toast.makeText(this, R.string.toastStats, Toast.LENGTH_LONG).show();
        }else{
            chooseClass(nomClasse, nomPerso, niv, vie, force, agi, intel, textview);

        }


    }

    public void chooseClass(String nameClass, String nomPerso, int niv, int vie, int force, int agi, int intel, TextView textview){
        if(nameClass.equals(getResources().getString(R.string.nameClassMage))){
            Mage mage = new Mage(getResources().getString(R.string.nameClassMage), nomPerso, niv, vie, force, agi, intel, getResources().getString(R.string.attBaseMage), getResources().getString(R.string.attSpeMage));
            textview.setText(getResources().getString(R.string.textcreaPersoMage, mage.nomPerso, mage.name, mage.niveau, mage.vie, mage.intelligence, mage.agilite, mage.force));
            arrayPerso.add(mage);

        }else if(nameClass.equals(getResources().getString(R.string.nameClassWar))){
            Guerrier guerrier = new Guerrier(getResources().getString(R.string.nameClassWar), nomPerso, niv, vie, force, agi, intel, getResources().getString(R.string.attBaseWar), getResources().getString(R.string.attSpeWar));
            textview.setText(getResources().getString(R.string.textcreaPersoWar, guerrier.nomPerso, guerrier.name, guerrier.niveau, guerrier.vie, guerrier.force, guerrier.agilite, guerrier.intelligence));
            arrayPerso.add(guerrier);

        }else{
            Rodeur rodeur = new Rodeur(getResources().getString(R.string.nameClassRogue), nomPerso, niv, vie, force, agi, intel, getResources().getString(R.string.attBaseWar), getResources().getString(R.string.attSpeRogue));
            textview.setText(getResources().getString(R.string.textcreaPersoRogue, rodeur.nomPerso, rodeur.name, rodeur.niveau, rodeur.vie, rodeur.agilite, rodeur.intelligence, rodeur.force));
            arrayPerso.add(rodeur);

        }
    }


    //RESET DU JEU

    public void resetJeu(){
        binding.editNomPerso.setText("");
        binding.editNomPerso2.setText("");
        binding.editNivJ1.setText("");
        binding.editNivJ2.setText("");
        binding.editForceJ1.setText("");
        binding.editForceJ2.setText("");
        binding.editAgiJ1.setText("");
        binding.editAgiJ2.setText("");
        binding.editIntJ1.setText("");
        binding.editIntJ2.setText("");
        arrayPerso.clear();
        binding.textResCreaJ1.setText("");
        binding.textResCreaJ2.setText("");
        binding.rButtonWarJ1.setBackground(getDrawable(R.mipmap.warwait));
        binding.rButtonRogueJ1.setBackground(getDrawable(R.mipmap.roguewait));
        binding.rButtonMageJ1.setBackground(getDrawable(R.mipmap.magewait));
        binding.rButtonWarJ2.setBackground(getDrawable(R.mipmap.warwait));
        binding.rButtonRogueJ2.setBackground(getDrawable(R.mipmap.roguewait));
        binding.rButtonMageJ2.setBackground(getDrawable(R.mipmap.magewait));
    }



    //GESTION DES RADIOGROUP / RADIOBUTTON

    public View.OnClickListener backChanged = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rButtonWarJ1 :
                    changedBackRButt(binding.rButtonWarJ1, binding.rButtonRogueJ1, binding.rButtonMageJ1, 0);
                    break;
                case R.id.rButtonRogueJ1 :
                    changedBackRButt(binding.rButtonWarJ1, binding.rButtonRogueJ1, binding.rButtonMageJ1, 1);
                    break;
                case R.id.rButtonMageJ1 :
                    changedBackRButt(binding.rButtonWarJ1, binding.rButtonRogueJ1, binding.rButtonMageJ1, 2);
                    break;
                case R.id.rButtonWarJ2 :
                    changedBackRButt(binding.rButtonWarJ2, binding.rButtonRogueJ2, binding.rButtonMageJ2, 0);
                    break;
                case R.id.rButtonRogueJ2 :
                    changedBackRButt(binding.rButtonWarJ2, binding.rButtonRogueJ2, binding.rButtonMageJ2, 1);
                    break;
                case R.id.rButtonMageJ2 :
                    changedBackRButt(binding.rButtonWarJ2, binding.rButtonRogueJ2, binding.rButtonMageJ2, 2);
                    break;
            }
        }
    };



    public void changedBackRButt(RadioButton rButtonWar, RadioButton rButtonRogue, RadioButton rButtonMage, int i){
        if( i == 0) {
            rButtonWar.setBackground(getDrawable(R.mipmap.warchoose));
            rButtonRogue.setBackground(getDrawable(R.mipmap.roguewait));
            rButtonMage.setBackground(getDrawable(R.mipmap.magewait));
        }else if ( i == 1 ){
            rButtonWar.setBackground(getDrawable(R.mipmap.warwait));
            rButtonRogue.setBackground(getDrawable(R.mipmap.roguechoose));
            rButtonMage.setBackground(getDrawable(R.mipmap.magewait));
        }else if ( i == 2 ){
            rButtonWar.setBackground(getDrawable(R.mipmap.warwait));
            rButtonRogue.setBackground(getDrawable(R.mipmap.roguewait));
            rButtonMage.setBackground(getDrawable(R.mipmap.magechoose));
        }

    }





    // RETOUR DE L'INTENT

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            String s = data.getStringExtra(VAL_RETOUR);
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
        }
    }






    // CALLIGRAPHY

    @Override
    protected void attachBaseContext(Context newBase){
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
