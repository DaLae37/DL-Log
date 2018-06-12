package com.dalae37.android.dl_log;

import android.os.Debug;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MatchDetail {
    JSONObject matchJObject;
    public String gameMode;
    public long gameDuration, gameCreation, accountId;
    public int myParticipantId, teamId[] = new int[2];
    public boolean isFirstWin;
    public String matchJson, gameDuration_RT, gameCreation_RT;
    MatchDetail_Summoner [] summoners = new MatchDetail_Summoner[10];


    MatchDetail(String matchJson, long accountId){
        this.matchJson = matchJson;
        this.accountId = accountId;
        ParseData();
        SetMatchDetail_Summoner();
    }

    public void SetMatchDetail_Summoner() {
        try {
            JSONArray participantIdentities = matchJObject.getJSONArray("participantIdentities");
            JSONArray participants = matchJObject.getJSONArray("participants");
            JSONArray teams = matchJObject.getJSONArray("teams");

            for(int i=0; i<2; i++){
                JSONObject jObject = teams.getJSONObject(i);
                teamId[i] = jObject.getInt("teamId");
                if(jObject.getString("win").equals("Win")){
                    switch (i){
                        case 0 :
                            isFirstWin = true;
                            break;
                        case 1:
                            isFirstWin = false;
                            break;
                    }
                }
            }
            for (int i = 0; i < 10; i++) {
               JSONObject jObject1 = participantIdentities.getJSONObject(i);
               JSONObject player = jObject1.getJSONObject("player");

               JSONObject jObject2  = participants.getJSONObject(i);
               JSONObject stats = jObject2.getJSONObject("stats");


                int participantId = jObject1.getInt("participantId");
                if(player.getLong("accountId") == accountId){
                    myParticipantId = participantId;
                }

                String name = player.getString("summonerName");
                Boolean isWin = stats.getBoolean("win");
                int kills = stats.getInt("kills"), assists = stats.getInt("assists"), deaths = stats.getInt("deaths"), totalMinionsKilled = stats.getInt("totalMinionsKilled"), champLevel = stats.getInt("champLevel");
                int spell[] = {jObject2.getInt("spell1Id"),jObject2.getInt("spell2Id")};
                int item[] = {stats.getInt("item0"),stats.getInt("item1"),stats.getInt("item2"),stats.getInt("item3"),stats.getInt("item4"),stats.getInt("item5"),stats.getInt("item6")};
                int team = jObject2.getInt("teamId"), championId=  jObject2.getInt("championId");
                summoners[i] = new MatchDetail_Summoner(participantId, name, team, spell, item, championId,kills,assists,deaths,totalMinionsKilled,champLevel,isWin);
            }
        } catch (Exception e) {
            Log.e("ParsingUserLog", e.toString());
        }
    }

    public void ParseData(){
        try{
            matchJObject = new JSONObject(matchJson);
            gameDuration = matchJObject.getLong("gameDuration");
            int second = (int)gameDuration % 60, minute = ((int)gameDuration-second) % 3600, hours = (int)gameDuration/3600;
            gameDuration_RT = hours + "시 " + minute + "분 " + second + "초";
            gameCreation = matchJObject.getLong("gameCreation");
            long current = System.currentTimeMillis();
            gameCreation_RT = (current - gameCreation) / 60000 + "분전";
        }
        catch (Exception e){
            Log.e("ParseData", e.toString());
        }
    }
}

class MatchDetail_Summoner{
    public String name;
    public int team;
    public int participantId;
    public int spell[];
    public int item[];
    public int championId;
    public int kill,assist,death, creepScore, champLevel;
    public boolean isWin;
    MatchDetail_Summoner(int participantId, String name, int team, int[] spell, int[]item, int championId, int kill, int assist, int death, int creepScore, int champLevel, boolean isWin) {
        this.name = name;
        this.team = team;
        this.participantId = participantId;
        this.spell = spell;
        this.item = item;
        this.championId = championId;
        this.kill = kill;
        this.assist = assist;
        this.death = death;
        this.creepScore = creepScore;
        this.champLevel = champLevel;
        this.isWin = isWin;
    }
}
