import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.swing.text.html.Option;

//TODO : 후에 어노테이션으로 바꿀 것
public class User {
    private int seq; // 유저 식별 번호
    private String Id; // 유저 아이디
    private String nickname; //유저 닉네임
    private char gender;


    public int getMaxage() {
        return maxage;
    }

    public void setMaxage(int maxage) {
        this.maxage = maxage;
    }

    public int getMinage() {
        return minage;
    }

    public void setMinage(int minage) {
        this.minage = minage;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private int maxage;

    private int minage;

    private int age;
    private double lat; //위도
    private double lon; //경도
    private boolean[] options; //자신 옵션
    private boolean[] matchoptions; //상대에게 원하는 옵션


    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public boolean[] getOptions() {
        return options;
    }

    public void setOptions(boolean[] options) {
        this.options = options;
    }

    public boolean[] getMatchoptions() {
        return matchoptions;
    }

    public void setMatchoptions(boolean[] matchoptions) {
        this.matchoptions = matchoptions;
    }

    //TODO : 테스트 위한 생성자이므로 나중에 지울 것

    public User(int seq, String id, String nickname, char gender, int maxage, int minage, int age, double lat, double lon, boolean[] options, boolean[] matchoptions) {
        this.seq = seq;
        Id = id;
        this.nickname = nickname;
        this.gender = gender;
        this.maxage = maxage;
        this.minage = minage;
        this.age = age;
        this.lat = lat;
        this.lon = lon;
        this.options = options;
        this.matchoptions = matchoptions;
    }
}
