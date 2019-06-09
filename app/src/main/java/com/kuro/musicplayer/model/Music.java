package com.kuro.musicplayer.model;

import java.io.Serializable;

public class Music implements Serializable {
    private Integer id;
    private String musicName;
    private String musician;
    private String path;
    private Integer imagePath;
    private String netImagePath;
    //0 local， 1 online
    public Integer type;
    //网易云系统里的id
    public Integer nId;

    public Integer getnId() {
        return nId;
    }

    public void setnId(Integer nId) {
        this.nId = nId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNetImagePath() {
        return netImagePath;
    }

    public void setNetImagePath(String netImagePath) {
        this.netImagePath = netImagePath;
    }

    public Integer getImagePath() {
        return imagePath;
    }

    public void setImagePath(Integer imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusician() {
        return musician;
    }

    public void setMusician(String musician) {
        this.musician = musician;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        } else if(obj instanceof Music) {
            Music m = (Music) obj;
            if (getId() != null && m.getId()!=null) {
                return getId().equals(m.getId());
            }else if (getMusicName() != null && getMusician() != null && m.getMusicName()!=null && m.getMusician() != null) {
                return getMusicName().equals(m.getMusicName()) && getMusician().equals(m.getMusician());
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Music [ id: " + String.valueOf(getId())
                + ", musicName: " + getMusicName()
                + ", musician: " + getMusician()
                + ", path: " + getPath()
                + ", imagePath: " + getImagePath()
                + "netImagePath: " + getNetImagePath()
                + " ]";
    }
}
