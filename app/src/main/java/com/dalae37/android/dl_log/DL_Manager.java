package com.dalae37.android.dl_log;

public class DL_Manager {
    private static DL_Manager instance;
    private DL_Manager(){
        this.nickname = "dalae37";
    }
    private String nickname;
    private static class Singleton{
        private static final DL_Manager instance = new DL_Manager();
    }

    public static DL_Manager getInstance (){
        return Singleton.instance;
    } //Singleton Area
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
    public String getNickname(){
        return this.nickname;
    }
    public int getChampionID(int champion){
        int resouceID;
        switch (champion) {
            case 62 :
                resouceID = R.drawable.monkeyking;
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
                resouceID = R.drawable.missfortune;
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
            case 516 :
                resouceID = R.drawable.ornn;
                break;
            case 412 :
                resouceID = R.drawable.thresh;
                break;
            case 10 :
                resouceID = R.drawable.kayle;
                break;
            case 120 :
                resouceID = R.drawable.hecarim;
                break;
            case 121 :
                resouceID = R.drawable.khazix;
                break;
            case 2 :
                resouceID = R.drawable.olaf;
                break;
            case 115 :
                resouceID = R.drawable.ziggs;
                break;
            case 134 :
                resouceID = R.drawable.syndra;
                break;
            case 36 :
                resouceID = R.drawable.drmundo;
                break;
            case 43 :
                resouceID = R.drawable.karma;
                break;
            case 1 :
                resouceID = R.drawable.annie;
                break;
            case 84 :
                resouceID = R.drawable.akali;
                break;
            case 106 :
                resouceID = R.drawable.volibear;
                break;
            case 157 :
                resouceID = R.drawable.yasuo;
                break;
            case 85 :
                resouceID = R.drawable.kennen;
                break;
            case 107 :
                resouceID = R.drawable.rengar;
                break;
            case 13 :
                resouceID = R.drawable.ryze;
                break;
            case 98 :
                resouceID = R.drawable.shen;
                break;
            case 154 :
                resouceID = R.drawable.zac;
                break;
            case 91 :
                resouceID = R.drawable.talon;
                break;
            case 50 :
                resouceID = R.drawable.swain;
                break;
            case 14 :
                resouceID = R.drawable.sion;
                break;
            case 67 :
                resouceID = R.drawable.vayne;
                break;
            case 75 :
                resouceID = R.drawable.nasus;
                break;
            case 141 :
                resouceID = R.drawable.kayn;
                break;
            case 4 :
                resouceID = R.drawable.twistedfate;
                break;
            case 31 :
                resouceID = R.drawable.chogath;
                break;
            case 77 :
                resouceID = R.drawable.udyr;
                break;
            case 236 :
                resouceID = R.drawable.lucian;
                break;
            case 427 :
                resouceID = R.drawable.ivern;
                break;
            case 89 :
                resouceID = R.drawable.leona;
                break;
            case 51 :
                resouceID = R.drawable.caitlyn;
                break;
            case 113 :
                resouceID = R.drawable.sejuani;
                break;
            case 56 :
                resouceID = R.drawable.nocturne;
                break;
            case 26 :
                resouceID = R.drawable.zilean;
                break;
            case 268 :
                resouceID = R.drawable.azir;
                break;
            case 68 :
                resouceID = R.drawable.rumble;
                break;
            case 25 :
                resouceID = R.drawable.morgana;
                break;
            case 163 :
                resouceID = R.drawable.taliyah;
                break;
            case 17 :
                resouceID = R.drawable.teemo;
                break;
            case 6 :
                resouceID = R.drawable.urgot;
                break;
            case 32 :
                resouceID = R.drawable.amumu;
                break;
            case 3 :
                resouceID = R.drawable.galio;
                break;
            case 74 :
                resouceID = R.drawable.heimerdinger;
                break;
            case 34 :
                resouceID = R.drawable.anivia;
                break;
            case 22 :
                resouceID = R.drawable.ashe;
                break;
            case 161 :
                resouceID = R.drawable.velkoz;
                break;
            case 27 :
                resouceID = R.drawable.singed;
                break;
            case 110 :
                resouceID = R.drawable.varus;
                break;
            case 29 :
                resouceID = R.drawable.twitch;
                break;
            case 86 :
                resouceID = R.drawable.garen;
                break;
            case 53 :
                resouceID = R.drawable.blitzcrank;
                break;
            case 11 :
                resouceID = R.drawable.masteryi;
                break;
            case 555 :
                resouceID = R.drawable.pyke;
                break;
            case 60 :
                resouceID = R.drawable.elise;
                break;
            case 12 :
                resouceID = R.drawable.alistar;
                break;
            case 55 :
                resouceID = R.drawable.katarina;
                break;
            case 245 :
                resouceID = R.drawable.ekko;
                break;
            case 82 :
                resouceID = R.drawable.mordekaiser;
                break;
            case 117 :
                resouceID = R.drawable.lulu;
                break;
            case 164 :
                resouceID = R.drawable.camille;
                break;
            case 266 :
                resouceID = R.drawable.aatrox;
                break;
            case 119 :
                resouceID = R.drawable.draven;
                break;
            case 223 :
                resouceID = R.drawable.tahmkench;
                break;
            case 80 :
                resouceID = R.drawable.pantheon;
                break;
            case 5 :
                resouceID = R.drawable.xinzhao;
                break;
            case 136 :
                resouceID = R.drawable.aurelionsol;
                break;
            case 64 :
                resouceID = R.drawable.leesin;
                break;
            case 44 :
                resouceID = R.drawable.taric;
                break;
            case 90 :
                resouceID = R.drawable.malzahar;
                break;
            case 145 :
                resouceID = R.drawable.kaisa;
                break;
            case 131 :
                resouceID = R.drawable.diana;
                break;
            case 18 :
                resouceID = R.drawable.tristana;
                break;
            case 421 :
                resouceID = R.drawable.reksai;
                break;
            case 8 :
                resouceID = R.drawable.vladimir;
                break;
            case 59 :
                resouceID = R.drawable.jarvaniv;
                break;
            case 267 :
                resouceID = R.drawable.nami;
                break;
            case 202 :
                resouceID = R.drawable.jhin;
                break;
            case 16 :
                resouceID = R.drawable.soraka;
                break;
            case 45 :
                resouceID = R.drawable.veigar;
                break;
            case 40 :
                resouceID = R.drawable.janna;
                break;
            case 111 :
                resouceID = R.drawable.nautilus;
                break;
            case 28 :
                resouceID = R.drawable.evelynn;
                break;
            case 79 :
                resouceID = R.drawable.gragas;
                break;
            case 238 :
                resouceID = R.drawable.zed;
                break;
            case 254 :
                resouceID = R.drawable.vi;
                break;
            case 96 :
                resouceID = R.drawable.kogmaw;
                break;
            case 103 :
                resouceID = R.drawable.ahri;
                break;
            case 133 :
                resouceID = R.drawable.quinn;
                break;
            case 7 :
                resouceID = R.drawable.leblanc;
                break;
            case 81 :
                resouceID = R.drawable.ezreal;
                break;
            default :
                resouceID = R.drawable.sejuani;
                break;
        }
        return resouceID;
    }
    public String getWinOrLose(boolean isWin){
        return (isWin) ? "승리" : "패배";
    }
    public String getGameMode(int gameQueue){
        switch (gameQueue){
            case 420 :
                return "솔로 랭크";
            case 430 :
                return "일반 게임";
            case 440 :
                return "자유 랭크";
            case 450 :
                return "무작위 총력전";
            default :
                return "알려지지 않은 게임";
        }
    }
    public String getGameCreation(long creation){
        if(creation / 60 == 0)
            return creation + "초전";
        else if(creation / (60 * 60) == 0)
            return (creation - (creation % 60)) / 60 + "분전";
        else if(creation / (60 * 60 * 24) == 0)
            return (creation - (creation % (60 * 60))) / (60 * 60) + "시간전";
        else if(creation / (60 * 60 * 24 * 7) == 0)
            return (creation - (creation % (60 * 60 * 24))) / (60 * 60 *24) + "일전";
        else
            return "오래전";
    }
}