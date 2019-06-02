package com.mandarin.bcu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.mandarin.bcu.util.Data.AB_BASE;
import static com.mandarin.bcu.util.Data.AB_EARN;
import static com.mandarin.bcu.util.Data.AB_GOOD;
import static com.mandarin.bcu.util.Data.AB_MASSIVE;
import static com.mandarin.bcu.util.Data.AB_MASSIVES;
import static com.mandarin.bcu.util.Data.AB_METALIC;
import static com.mandarin.bcu.util.Data.AB_ONLY;
import static com.mandarin.bcu.util.Data.AB_RESIST;
import static com.mandarin.bcu.util.Data.AB_RESISTS;
import static com.mandarin.bcu.util.Data.AB_WAVES;
import static com.mandarin.bcu.util.Data.AB_ZKILL;
import static com.mandarin.bcu.util.Data.ATK_LD;
import static com.mandarin.bcu.util.Data.ATK_OMNI;
import static com.mandarin.bcu.util.Data.P_BREAK;
import static com.mandarin.bcu.util.Data.P_CRIT;
import static com.mandarin.bcu.util.Data.P_IMUCURSE;
import static com.mandarin.bcu.util.Data.P_IMUKB;
import static com.mandarin.bcu.util.Data.P_IMUSLOW;
import static com.mandarin.bcu.util.Data.P_IMUSTOP;
import static com.mandarin.bcu.util.Data.P_IMUWARP;
import static com.mandarin.bcu.util.Data.P_IMUWAVE;
import static com.mandarin.bcu.util.Data.P_IMUWEAK;
import static com.mandarin.bcu.util.Data.P_KB;
import static com.mandarin.bcu.util.Data.P_LETHAL;
import static com.mandarin.bcu.util.Data.P_SLOW;
import static com.mandarin.bcu.util.Data.P_STOP;
import static com.mandarin.bcu.util.Data.P_STRONG;
import static com.mandarin.bcu.util.Data.P_WARP;
import static com.mandarin.bcu.util.Data.P_WAVE;
import static com.mandarin.bcu.util.Data.P_WEAK;
import static com.mandarin.bcu.util.Data.TB_ALIEN;
import static com.mandarin.bcu.util.Data.TB_ANGEL;
import static com.mandarin.bcu.util.Data.TB_BLACK;
import static com.mandarin.bcu.util.Data.TB_FLOAT;
import static com.mandarin.bcu.util.Data.TB_METAL;
import static com.mandarin.bcu.util.Data.TB_RED;
import static com.mandarin.bcu.util.Data.TB_RELIC;
import static com.mandarin.bcu.util.Data.TB_WHITE;
import static com.mandarin.bcu.util.Data.TB_ZOMBIE;

public class SearchFilter extends AppCompatActivity {

    private ImageButton back;
    private RadioButton tgor;
    private RadioButton atkmu;
    private RadioButton atkor;
    private RadioButton abor;
    private RadioGroup tggroup;
    private RadioGroup atkgroup;
    private RadioGroup atkgroupor;
    private RadioGroup abgroup;
    private CheckBox[] rarities = new CheckBox[6];
    private CheckBox[] targets = new CheckBox[9];
    private CheckBox[] attacks = new CheckBox[3];
    private CheckBox[] abilities = new CheckBox[28];
    private ScrollView sc;
    private NestedScrollView nsc;
    private int[] tgid = {R.id.schchrd,R.id.schchfl,R.id.schchbla,R.id.schchme,R.id.schchan,R.id.schchzo,R.id.schchre,R.id.schchal,R.id.schchwh};
    private String [] colors = {"1","2","3","4","5","6","7","8","9","0"};
    private int[] rareid = {R.id.schchba,R.id.schchex,R.id.schchr,R.id.schchsr,R.id.schchur,R.id.schchlr};
    private String [] rarity = {"0","1","2","3","4","5"};
    private int[] atkid = {R.id.schchld,R.id.schchom,R.id.schchmu};
    private String [] atks = {"2","4","3"};
    private int[] abid = {R.id.schchabwe,R.id.schchabfr,R.id.schchabsl,R.id.schchabta,R.id.schchabst,R.id.schchabre,R.id.schchabir,R.id.schchabmd,R.id.schchabid,R.id.schchabkb,R.id.schchabwp,R.id.schchabstr,R.id.schchabsu,R.id.schchabcd,
            R.id.schchabcr,R.id.schchabzk,R.id.schchabbb,R.id.schchabem,R.id.schchabme,R.id.schchabwv,R.id.schchabimwe,R.id.schchabimfr,R.id.schchabimsl,R.id.schchabimkb,R.id.schchabimwv,R.id.schchabimwp,R.id.schchabimcu,R.id.schchabws};
    private int[] abtool = {R.string.sch_abi_we,R.string.sch_abi_fr,R.string.sch_abi_sl,R.string.sch_abi_ao,R.string.sch_abi_st,R.string.sch_abi_re,R.string.sch_abi_it,R.string.sch_abi_md,R.string.sch_abi_id,R.string.sch_abi_kb,
            R.string.sch_abi_wa,R.string.sch_abi_str,R.string.sch_abi_su,R.string.sch_abi_bd,R.string.sch_abi_cr,R.string.sch_abi_zk,R.string.sch_abi_bb,R.string.sch_abi_em,R.string.sch_abi_me,R.string.sch_abi_wv,
            R.string.sch_abi_iw,R.string.sch_abi_if,R.string.sch_abi_is,R.string.sch_abi_ik,R.string.sch_abi_iwv,R.string.sch_abi_iwa,R.string.sch_abi_ic,R.string.sch_abi_ws};
    private int[] tgtool = {R.string.sch_red,R.string.sch_fl,R.string.sch_bla,R.string.sch_me,R.string.sch_an,R.string.sch_zo,R.string.sch_re,R.string.sch_al,R.string.sch_wh};
    private int [][] abils = {{1,P_WEAK},{1,P_STOP},{1,P_SLOW},{0,AB_ONLY},{0,AB_GOOD},{0,AB_RESIST},{0,AB_RESISTS},{0,AB_MASSIVE},{0,AB_MASSIVES},{1,P_KB},{1,P_WARP},{1,P_STRONG},{1,P_LETHAL},{0,AB_BASE},{1,P_CRIT},{0,AB_ZKILL},{1,P_BREAK},
            {0,AB_EARN},{0,AB_METALIC},{1,P_WAVE},{1,P_IMUWEAK},{1,P_IMUSTOP},{1,P_IMUSLOW},{1,P_IMUKB},{1,P_IMUWAVE},{1,P_IMUWARP},{1,P_IMUCURSE},{0,AB_WAVES}};
    private ArrayList<String> tg = new ArrayList<>();
    private ArrayList<String> rare = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> ability = new ArrayList<>();
    private ArrayList<String> attack = new ArrayList<>();
    private boolean tgorand = true;
    private boolean atksimu = true;
    private boolean aborand = true;
    private boolean atkorand = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);

        back = findViewById(R.id.schbck);
        tgor = findViewById(R.id.schrdtgor);
        atkmu = findViewById(R.id.schrdatkmu);
        atkor = findViewById(R.id.schrdatkor);
        abor = findViewById(R.id.schrdabor);
        tggroup = findViewById(R.id.schrgtg);
        atkgroup = findViewById(R.id.schrgatk);
        atkgroupor = findViewById(R.id.schrgatkor);
        abgroup = findViewById(R.id.schrgab);
        sc = findViewById(R.id.animsc);
        nsc = findViewById(R.id.animnscview);
        for(int i = 0; i < tgid.length; i++)
            targets[i] = findViewById(tgid[i]);
        for(int i=0;i<rareid.length;i++)
            rarities[i] = findViewById(rareid[i]);
        for(int i=0;i<atkid.length;i++)
            attacks[i] = findViewById(atkid[i]);
        for(int i=0;i<abid.length;i++)
            abilities[i] = findViewById(abid[i]);

        tgor.setChecked(true);
        atkor.setChecked(true);
        abor.setChecked(true);

        Listeners();
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void Listeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returner();
            }
        });

        tggroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tgorand = checkedId == tgor.getId();
            }
        });

        atkgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                atksimu = checkedId == atkmu.getId();
            }
        });

        abgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                aborand = checkedId == abor.getId();
            }
        });

        for(int i=0;i<targets.length;i++) {
            final int finalI = i;
            targets[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        tg.add(colors[finalI]);
                    else
                        tg.remove(colors[finalI]);
                }
            });

            targets[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(), tgtool[finalI], Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        for(int i=0;i<rarities.length;i++) {
            final int finall = i;
            rarities[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        rare.add(rarity[finall]);
                    else
                        rare.remove(rarity[finall]);
                }
            });
        }

        for(int i=0;i<attacks.length;i++) {
            final int finall = i;
            attacks[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        attack.add(atks[finall]);
                    else
                        attack.remove(atks[finall]);
                }
            });
        }

        for(int i=0;i<abilities.length;i++) {
            final int finall = i;
            abilities[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(),abtool[finall],Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

            abilities[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        ArrayList<Integer> abilval = new ArrayList<>();
                        for(int i : abils[finall])
                            abilval.add(i);
                        ability.add(abilval);
                    }
                    else
                        ability.remove(abils[finall]);
                }
            });

            atkgroupor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    atkorand = checkedId == atkor.getId();
                }
            });
        }

        nsc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sc.requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });
    }

    protected void returner() {
        Intent result = new Intent();
        if(atkgroup.getCheckedRadioButtonId() == -1) {
            result.putExtra("empty",true);
        } else {
            result.putExtra("empty",false);
        }
        result.putExtra("tgorand",tgorand);
        result.putExtra("atksimu",atksimu);
        result.putExtra("aborand",aborand);
        result.putExtra("atkorand",atkorand);
        result.putExtra("target",tg);
        result.putExtra("attack",attack);
        result.putExtra("rare",rare);
        result.putExtra("ability",ability);
        setResult(RESULT_OK,result);
        finish();
    }
}
