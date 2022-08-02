import java.util.*;

public class Main2 {
    static final int PEOPLE_NUM = 4;

    //TODO : 웹소켓에 현재 상태 신호등 해주는 리스너 하나 만들 것
    static List<User> watingUser = new ArrayList<>();  //오픈비두 서버에서 정보가 전달된 유저 ( seq,USER)
    static List<User> connectUser = new ArrayList<>();  //오픈비두 서버에서 정보가 전달된 유저

    static List<User> ManList = new ArrayList<>();
    static List<User> WomanList = new ArrayList<>();

    static double nowlat;
    static double nowlon;

    public static void main(String[] args) {

        //TEST CASE//
        int[] smokeo = {2};
        int[] smokex = {1};
        int[] smokeok = {0};


        connectUser.add(new User(1, "yesol1", "예솔1", 'W', 30, 19, 20, 0.2, 0.2, smokeo, smokeo));
        connectUser.add(new User(2, "yesol2", "예솔2", 'W', 25, 20, 24, 0.5, 0.5, smokeo, smokeo));
        connectUser.add(new User(3, "yesol3", "예솔3", 'W', 30, 23, 23, 0.1, 0.1, smokeo, smokeo));
        connectUser.add(new User(4, "yenam1", "예남1", 'M', 24, 20, 24, 0.1, 0.1, smokeo, smokeo));
        connectUser.add(new User(5, "yenam2", "예남1", 'M', 10, 10, 27, 0.0, 0.0, smokeo, smokeo));


        // 남자1 조건에 맞는 사람들의 리스트 생성
        //n^2
        List<User> UserTmpUserList = new ArrayList<>();

        for (User user : connectUser) {

            nowlat = user.getLat();
            nowlon = user.getLon();

            ManList.add(user);

            for (User machingUser : connectUser) {
                if (user.getSeq() == machingUser.getSeq()) continue;

                if (machingUser.getGender() == 'M' ){
                    for (User user1:WomanList) {
                        isMaching(user1,machingUser);
                        isMaching(machingUser,user1);
                    }
                }else{
                    for (User user1:ManList) {
                        isMaching(user1,machingUser);
                        isMaching(machingUser,user1);
                    }

                }
                for (User beforMatchingUser : UserTmpUserList) {
                    if (isMaching(beforMatchingUser, machingUser) & isMaching(beforMatchingUser, machingUser)) {
                        UserTmpUserList.add(beforMatchingUser);
                    }


                }



            }


            for (User womanUser : UserTmpUserList) {

                System.out.println(womanUser.getNickname());
            }

            int cnt = 0;
            for (User tmpUser : UserTmpUserList) {
                if (Arrays.asList(connectUser).contains(tmpUser)) {
                    cnt++;
                }
            }
            if (cnt == PEOPLE_NUM) startConnect();
        }
    }


    //1번 유저 기준으로 옵션 체크
    public static boolean isMaching(User user1, User user2) {
        // 나이 체크
        if (user1.getMaxage() < user2.getAge()) return false;
        if (user1.getMinage() > user2.getAge()) return false;

        for (int i = 0; i < user1.getOptions().length; i++) {
            if (user1.getMatchoptions()[i] == 2 & user2.getOptions()[i] == 1) return false;
            if (user1.getMatchoptions()[i] == 1 & user2.getOptions()[i] == 2) return false;
        }
        return true;
    }

    public static boolean startConnect() {
        //매칭된 남자, 여자 connectUser 에서 빼기
        //현재 남자, 여자 list 에서 빼기
        return true;
    }

}
