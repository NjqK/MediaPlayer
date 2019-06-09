package com.kuro.musicplayer.utils;

import com.kuro.musicplayer.R;
import com.kuro.musicplayer.model.Music;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FindLocalMusicsHelper {

    private static List<Music> mp3List = new ArrayList<Music>();

    public static List<Music> getSDcardFile(File groupPath){
        for(int i=0; i< groupPath.listFiles().length; i++){
            File childFile = groupPath.listFiles()[i];
            if(childFile.isDirectory()){
                getSDcardFile(childFile);
            }else{
                if(childFile.isFile() && childFile.toString().trim().endsWith(".mp3")){
                    Music music = new Music();
                    String[] temp = childFile.getName().trim().replace(".mp3","").split("-");
                    music.setMusicName(temp[1].trim());
                    music.setMusician(temp[0].trim());
                    music.setPath(childFile.getAbsolutePath());
                    music.setImagePath(R.raw.ic_music1);
                    music.setType(0);
                    if (!mp3List.contains(music)) {
                        mp3List.add(music);
                    }
                }
            }
        }
       return mp3List;
    }
}
