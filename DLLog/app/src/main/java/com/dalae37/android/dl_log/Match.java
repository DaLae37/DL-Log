package com.dalae37.android.dl_log;

public class Match {
    public String lane;
    public long gameId;
    public int champion;
    public String platformId;
    public long timestamp;
    public int queue;
    public String role;
    public int season;
    Match(String lane, long gameId, int champion, long timestamp, int queue, String role, int season){
        this.lane = lane;
        this.gameId = gameId;
        this.champion = champion;
        this.timestamp = timestamp;
        this.queue = queue;
        this.role = role;
        this.season = season;
    }
}
