package com.dalae37.android.dl_log;

public class DL_Manager {
    private static DL_Manager instance;
    private DL_Manager(){}

    private static class Singleton{
        private static final DL_Manager instance = new DL_Manager();
    }

    public static DL_Manager getInstance (){
        return Singleton.instance;
    } //Singleton Area
    public int getChampionID(int champion){
        int resouceID;
        switch (champion) {
            case 62 :
                resouceID = R.drawable.monkeyKing;
                break;
            case 24 :
                resouceID = R.drawable.jax;
                break;
            case 9 :
                resouceID = R.drawable.fiddlesticks;
                break;
            case 35 :
                resouceID = R.drawable.shaco;
                break;
            case 19 :
                resouceID = R.drawable.warwick;
                break;
            case 498 :
                resouceID = R.drawable.xayah;
                break;
            case 76 :
                resouceID = R.drawable.nidalee;
                break;
            case 143 :
                resouceID = R.drawable.zyra;
                break;
            case 240 :
                resouceID = R.drawable.kled;
                break;
            case 63 :
                resouceID = R.drawable.brand;
                break;
            case 33 :
                resouceID = R.drawable.rammus;
                break;
            case 420 :
                resouceID = R.drawable.illaoi;
                break;
            case 42 :
                resouceID = R.drawable.corki;
                break;
            case 201 :
                resouceID = R.drawable.braum;
                break;
            case 122 :
                resouceID = R.drawable.darius;
                break;
            case 23 :
                resouceID = R.drawable.tryndamere;
                break;
            case 21 :
                resouceID = R.drawable.missFortune;
                break;
            case 83 :
                resouceID = R.drawable.yorick;
                break;
            case 101 :
                resouceID = R.drawable.xerath;
                break;
            case 15 :
                resouceID = R.drawable.sivir;
                break;
            case 92 :
                resouceID = R.drawable.riven;
                break;
            case 61 :
                resouceID = R.drawable.orianna;
                break;
            case 41 :
                resouceID = R.drawable.gangplank;
                break;
            case 54 :
                resouceID = R.drawable.malphite;
                break;
            case 78 :
                resouceID = R.drawable.poppy;
                break;
            case 127 :
                resouceID = R.drawable.lissandra;
                break;
            case 126 :
                resouceID = R.drawable.jayce;
                break;
            case 20 :
                resouceID = R.drawable.nunu;
                break;
            case 48 :
                resouceID = R.drawable.trundle;
                break;
            case 30 :
                resouceID = R.drawable.karthus;
                break;
            case 104 :
                resouceID = R.drawable.graves;
                break;
            case 142 :
                resouceID = R.drawable.zoe;
                break;
            case 150 :
                resouceID = R.drawable.gnar;
                break;
            case 99 :
                resouceID = R.drawable.lux;
                break;
            case 102 :
                resouceID = R.drawable.shyvana;
                break;
            case 58 :
                resouceID = R.drawable.renekton;
                break;
            case 114 :
                resouceID = R.drawable.fiora;
                break;
            case 222 :
                resouceID = R.drawable.jinx;
                break;
            case 429 :
                resouceID = R.drawable.kalista;
                break;
            case 105 :
                resouceID = R.drawable.fizz;
                break;
            case 38 :
                resouceID = R.drawable.kassadin;
                break;
            case 37 :
                resouceID = R.drawable.sona;
                break;
            case 39 :
                resouceID = R.drawable.irelia;
                break;
            case 112 :
                resouceID = R.drawable.viktor;
                break;
            case 497 :
                resouceID = R.drawable.rakan;
                break;
            case 203 :
                resouceID = R.drawable.kindred;
                break;
            case 69 :
                resouceID = R.drawable.cassiopeia;
                break;
            case 57 :
                resouceID = R.drawable.maokai;
                break;
            default :
                resouceID = R.drawable.sejuani;
                break;
        }
        return resouceID;
    }
}