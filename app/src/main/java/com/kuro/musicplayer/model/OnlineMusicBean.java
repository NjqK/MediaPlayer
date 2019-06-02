package com.kuro.musicplayer.model;

import java.util.List;

public class OnlineMusicBean {

    /**
     * songs : [{"name":"爱","id":123456,"pst":0,"t":0,"ar":[{"id":3774,"name":"刘罡","tns":[],"alias":[]}],"alia":[],"pop":5,"st":0,"rt":"","fee":0,"v":3,"crbt":null,"cf":"","al":{"id":11935,"name":"二人传奇","picUrl":"https://p1.music.126.net/K9f4Ec2AR5HwDMChCb5_9Q==/83562883711712.jpg","tns":[],"pic":83562883711712},"dt":248006,"h":null,"m":{"br":160000,"fid":0,"size":4983586,"vd":0.482621},"l":{"br":96000,"fid":0,"size":2999116,"vd":0.324618},"a":null,"cd":"1","no":4,"rtUrl":null,"ftype":0,"rtUrls":[],"djId":0,"copyright":2,"s_id":0,"mv":0,"rtype":0,"rurl":null,"mst":9,"cp":0,"publishTime":1101830400000}]
     * privileges : [{"id":123456,"fee":0,"payed":0,"st":0,"pl":192000,"dl":192000,"sp":7,"cp":1,"subp":1,"cs":false,"maxbr":192000,"fl":320000,"toast":false,"flag":0,"preSell":false}]
     * code : 200
     */

    private int code;
    private List<SongsBean> songs;
    private List<PrivilegesBean> privileges;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SongsBean> getSongs() {
        return songs;
    }

    public void setSongs(List<SongsBean> songs) {
        this.songs = songs;
    }

    public List<PrivilegesBean> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegesBean> privileges) {
        this.privileges = privileges;
    }

    public static class SongsBean {
        /**
         * name : 爱
         * id : 123456
         * pst : 0
         * t : 0
         * ar : [{"id":3774,"name":"刘罡","tns":[],"alias":[]}]
         * alia : []
         * pop : 5.0
         * st : 0
         * rt :
         * fee : 0
         * v : 3
         * crbt : null
         * cf :
         * al : {"id":11935,"name":"二人传奇","picUrl":"https://p1.music.126.net/K9f4Ec2AR5HwDMChCb5_9Q==/83562883711712.jpg","tns":[],"pic":83562883711712}
         * dt : 248006
         * h : null
         * m : {"br":160000,"fid":0,"size":4983586,"vd":0.482621}
         * l : {"br":96000,"fid":0,"size":2999116,"vd":0.324618}
         * a : null
         * cd : 1
         * no : 4
         * rtUrl : null
         * ftype : 0
         * rtUrls : []
         * djId : 0
         * copyright : 2
         * s_id : 0
         * mv : 0
         * rtype : 0
         * rurl : null
         * mst : 9
         * cp : 0
         * publishTime : 1101830400000
         */

        private String name;
        private int id;
        private int pst;
        private int t;
        private double pop;
        private int st;
        private String rt;
        private int fee;
        private int v;
        private Object crbt;
        private String cf;
        private AlBean al;
        private int dt;
        private Object h;
        private MBean m;
        private LBean l;
        private Object a;
        private String cd;
        private int no;
        private Object rtUrl;
        private int ftype;
        private int djId;
        private int copyright;
        private int s_id;
        private int mv;
        private int rtype;
        private Object rurl;
        private int mst;
        private int cp;
        private long publishTime;
        private List<ArBean> ar;
        private List<?> alia;
        private List<?> rtUrls;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPst() {
            return pst;
        }

        public void setPst(int pst) {
            this.pst = pst;
        }

        public int getT() {
            return t;
        }

        public void setT(int t) {
            this.t = t;
        }

        public double getPop() {
            return pop;
        }

        public void setPop(double pop) {
            this.pop = pop;
        }

        public int getSt() {
            return st;
        }

        public void setSt(int st) {
            this.st = st;
        }

        public String getRt() {
            return rt;
        }

        public void setRt(String rt) {
            this.rt = rt;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }

        public Object getCrbt() {
            return crbt;
        }

        public void setCrbt(Object crbt) {
            this.crbt = crbt;
        }

        public String getCf() {
            return cf;
        }

        public void setCf(String cf) {
            this.cf = cf;
        }

        public AlBean getAl() {
            return al;
        }

        public void setAl(AlBean al) {
            this.al = al;
        }

        public int getDt() {
            return dt;
        }

        public void setDt(int dt) {
            this.dt = dt;
        }

        public Object getH() {
            return h;
        }

        public void setH(Object h) {
            this.h = h;
        }

        public MBean getM() {
            return m;
        }

        public void setM(MBean m) {
            this.m = m;
        }

        public LBean getL() {
            return l;
        }

        public void setL(LBean l) {
            this.l = l;
        }

        public Object getA() {
            return a;
        }

        public void setA(Object a) {
            this.a = a;
        }

        public String getCd() {
            return cd;
        }

        public void setCd(String cd) {
            this.cd = cd;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public Object getRtUrl() {
            return rtUrl;
        }

        public void setRtUrl(Object rtUrl) {
            this.rtUrl = rtUrl;
        }

        public int getFtype() {
            return ftype;
        }

        public void setFtype(int ftype) {
            this.ftype = ftype;
        }

        public int getDjId() {
            return djId;
        }

        public void setDjId(int djId) {
            this.djId = djId;
        }

        public int getCopyright() {
            return copyright;
        }

        public void setCopyright(int copyright) {
            this.copyright = copyright;
        }

        public int getS_id() {
            return s_id;
        }

        public void setS_id(int s_id) {
            this.s_id = s_id;
        }

        public int getMv() {
            return mv;
        }

        public void setMv(int mv) {
            this.mv = mv;
        }

        public int getRtype() {
            return rtype;
        }

        public void setRtype(int rtype) {
            this.rtype = rtype;
        }

        public Object getRurl() {
            return rurl;
        }

        public void setRurl(Object rurl) {
            this.rurl = rurl;
        }

        public int getMst() {
            return mst;
        }

        public void setMst(int mst) {
            this.mst = mst;
        }

        public int getCp() {
            return cp;
        }

        public void setCp(int cp) {
            this.cp = cp;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public List<ArBean> getAr() {
            return ar;
        }

        public void setAr(List<ArBean> ar) {
            this.ar = ar;
        }

        public List<?> getAlia() {
            return alia;
        }

        public void setAlia(List<?> alia) {
            this.alia = alia;
        }

        public List<?> getRtUrls() {
            return rtUrls;
        }

        public void setRtUrls(List<?> rtUrls) {
            this.rtUrls = rtUrls;
        }

        public static class AlBean {
            /**
             * id : 11935
             * name : 二人传奇
             * picUrl : https://p1.music.126.net/K9f4Ec2AR5HwDMChCb5_9Q==/83562883711712.jpg
             * tns : []
             * pic : 83562883711712
             */

            private int id;
            private String name;
            private String picUrl;
            private long pic;
            private List<?> tns;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public long getPic() {
                return pic;
            }

            public void setPic(long pic) {
                this.pic = pic;
            }

            public List<?> getTns() {
                return tns;
            }

            public void setTns(List<?> tns) {
                this.tns = tns;
            }
        }

        public static class MBean {
            /**
             * br : 160000
             * fid : 0
             * size : 4983586
             * vd : 0.482621
             */

            private int br;
            private int fid;
            private int size;
            private double vd;

            public int getBr() {
                return br;
            }

            public void setBr(int br) {
                this.br = br;
            }

            public int getFid() {
                return fid;
            }

            public void setFid(int fid) {
                this.fid = fid;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public double getVd() {
                return vd;
            }

            public void setVd(double vd) {
                this.vd = vd;
            }
        }

        public static class LBean {
            /**
             * br : 96000
             * fid : 0
             * size : 2999116
             * vd : 0.324618
             */

            private int br;
            private int fid;
            private int size;
            private double vd;

            public int getBr() {
                return br;
            }

            public void setBr(int br) {
                this.br = br;
            }

            public int getFid() {
                return fid;
            }

            public void setFid(int fid) {
                this.fid = fid;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public double getVd() {
                return vd;
            }

            public void setVd(double vd) {
                this.vd = vd;
            }
        }

        public static class ArBean {
            /**
             * id : 3774
             * name : 刘罡
             * tns : []
             * alias : []
             */

            private int id;
            private String name;
            private List<?> tns;
            private List<?> alias;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<?> getTns() {
                return tns;
            }

            public void setTns(List<?> tns) {
                this.tns = tns;
            }

            public List<?> getAlias() {
                return alias;
            }

            public void setAlias(List<?> alias) {
                this.alias = alias;
            }
        }
    }

    public static class PrivilegesBean {
        /**
         * id : 123456
         * fee : 0
         * payed : 0
         * st : 0
         * pl : 192000
         * dl : 192000
         * sp : 7
         * cp : 1
         * subp : 1
         * cs : false
         * maxbr : 192000
         * fl : 320000
         * toast : false
         * flag : 0
         * preSell : false
         */

        private int id;
        private int fee;
        private int payed;
        private int st;
        private int pl;
        private int dl;
        private int sp;
        private int cp;
        private int subp;
        private boolean cs;
        private int maxbr;
        private int fl;
        private boolean toast;
        private int flag;
        private boolean preSell;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public int getPayed() {
            return payed;
        }

        public void setPayed(int payed) {
            this.payed = payed;
        }

        public int getSt() {
            return st;
        }

        public void setSt(int st) {
            this.st = st;
        }

        public int getPl() {
            return pl;
        }

        public void setPl(int pl) {
            this.pl = pl;
        }

        public int getDl() {
            return dl;
        }

        public void setDl(int dl) {
            this.dl = dl;
        }

        public int getSp() {
            return sp;
        }

        public void setSp(int sp) {
            this.sp = sp;
        }

        public int getCp() {
            return cp;
        }

        public void setCp(int cp) {
            this.cp = cp;
        }

        public int getSubp() {
            return subp;
        }

        public void setSubp(int subp) {
            this.subp = subp;
        }

        public boolean isCs() {
            return cs;
        }

        public void setCs(boolean cs) {
            this.cs = cs;
        }

        public int getMaxbr() {
            return maxbr;
        }

        public void setMaxbr(int maxbr) {
            this.maxbr = maxbr;
        }

        public int getFl() {
            return fl;
        }

        public void setFl(int fl) {
            this.fl = fl;
        }

        public boolean isToast() {
            return toast;
        }

        public void setToast(boolean toast) {
            this.toast = toast;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public boolean isPreSell() {
            return preSell;
        }

        public void setPreSell(boolean preSell) {
            this.preSell = preSell;
        }
    }
}
