package com.kuro.musicplayer.utils;

import com.kuro.musicplayer.model.OnlineMusicRequest;

public class RequestFormatUtil {
    public String requestASong(OnlineMusicRequest request) {
        if (request.getId() == null) {

            System.out.println("id null");
        }
        if (request.getType() == null) {
            request.setType("song");
        }
        return "?type="+request.getType()+"&id="+request.getId();
    }
}
