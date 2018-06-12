package com.dalae37.android.dl_log;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class MatchAdapter extends ArrayAdapter<Match> {
    Context context;
    int resId;
    ArrayList<MatchDetail> matchDetails;
    public MatchAdapter(Context context, int resource, ArrayList<MatchDetail> matchDetails) {
        super(context, resource);
        this.context = context;
        this.resId = resource;
        this.matchDetails = matchDetails;
    }

    @Override
    public int getCount() {
        return matchDetails.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resId, null);
            MatchHolder holder = new MatchHolder(convertView);
            convertView.setTag(holder);
        }
        MatchHolder holder = (MatchHolder)convertView.getTag();
        TextView gameType = holder.gameType, whenPlay = holder.whenPlay, playTime = holder.playTime, WinOrLose = holder.WinOrLose, CS = holder.CS, KDA = holder.KDA, Level = holder.Level;
        ImageView champion = holder.champion;

        final MatchDetail matchDetail = matchDetails.get(position);
        final MatchDetail_Summoner mySummoner = matchDetail.summoners[matchDetail.myParticipantId-1];
        gameType.setText(matchDetail.gameMode);
        whenPlay.setText(matchDetail.gameCreation_RT);
        playTime.setText(matchDetail.gameDuration_RT);
        WinOrLose.setText(String.valueOf(mySummoner.isWin));
        CS.setText(mySummoner.creepScore + "");
        KDA.setText(mySummoner.kill + "킬 " + mySummoner.assist + "어시스트 " + mySummoner.death + "데스");
        Level.setText(mySummoner.champLevel + "");
        return convertView;
    }
}

class MatchHolder{
    public TextView gameType, whenPlay, playTime, WinOrLose, CS, KDA, Level;
    public ImageView champion;

    public MatchHolder(View root){
        gameType = root.findViewById(R.id.gameType);
        whenPlay = root.findViewById(R.id.whenPlay);
        playTime = root.findViewById(R.id.playTime);
        WinOrLose = root.findViewById(R.id.WinOrLose);
        CS = root.findViewById(R.id.CS);
        KDA = root.findViewById(R.id.KDA);
        Level = root.findViewById(R.id.Level);
        champion = root.findViewById(R.id.championImage);
    }
}
