package com.kuro.musicplayer.model;

public class OnlineMusicRequest {

    //type: song, lyric, comments, detail, artist, album, playlist, mv, search
    private String type;
    //https://api.imjad.cn/cloudmusic/?type=playlist&id=2392116731歌单
    private Integer id;
    //type=search&search_type=1&s=关键字
    //歌单https://api.imjad.cn/cloudmusic/?type=search&search_type=1000&s=用户名
    //用户信息https://api.imjad.cn/cloudmusic/?type=search&search_type=1002&s=用户名
    private Integer searchType;

    public class RequestBuilder {
        private StringBuilder sb = new StringBuilder();
        private OnlineMusicRequest request = new OnlineMusicRequest();

        public RequestBuilder buildType(String type) {
            request.setType(type);
            return this;
        }
        public RequestBuilder buildId(int id) {
            request.setId(id);
            return this;
        }
        public RequestBuilder buildSearchType(int searchType) {
            request.setSearchType(searchType);
            return this;
        }
        public OnlineMusicRequest build() {
            return request;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }
}
