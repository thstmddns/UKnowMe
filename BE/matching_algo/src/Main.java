import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {


    //TODO : 웹소켓에 현재 상태 신호등 해주는 리스너 하나 만들 것
    static  List<User>  watingUser = new ArrayList<>();  //오픈비두 서버에서 정보가 전달된 유저 ( seq,USER)
    static  List<User> connectUser = new ArrayList<>();  //오픈비두 서버에서 정보가 전달된 유저

    static List<User> ManList = new ArrayList<>();
    static List<User> WomanList = new ArrayList<>();

    static double nowlat;
    static double nowlon;

    public static void main(String[] args) {

        //TEST CASE//
        boolean[] smokeo = {true};
        boolean[] smokex = {false};

        connectUser.add(new User(1,"yesol1","예솔1",'W',3,3,21,0.2,0.1,smokeo,smokex));
        connectUser.add(new User(2,"yesol2","예솔2",'W',4,4,24,0.1,0.1,smokex,smokex));
        connectUser.add(new User(3,"yesol3","예솔3",'W',3,3,26,0.1,0.0,smokeo,smokeo));
        connectUser.add(new User(4,"yenam1","예남1",'W',3,3,24,0.0,0.0,smokeo,smokex));        connectUser.add(new User(1,"yesol1","예솔1",'W',3,3,24,37.1,127.1,smokeo,smokex));
        connectUser.add(new User(5,"yenam2","예남1",'W',1,1,50,0.0,0.0,smokeo,smokex));



        addGenderList(connectUser);
        System.out.println("end");

        // 남자 조건에 맞는 여자 리스트 생성
        List<User> UserTmpWomanList = new ArrayList<>();

            for (User manUser :ManList) {
                try{
                    for (User womanUser : WomanList) {
                        try{
                            nowlat = manUser.getLat();
                            nowlon = manUser.getLon();
                            if (isMaching(manUser, womanUser) &  isMaching(womanUser,manUser)){
                                UserTmpWomanList.add(womanUser);
                                }
                        }catch ( ArrayIndexOutOfBoundsException e){
                            break;
                        }
                    }



                Collections.sort(UserTmpWomanList, optionComparator);

                for (User womanUser: UserTmpWomanList) {
                    if(Arrays.asList(connectUser).contains(womanUser)){
                        startConnect(manUser,womanUser);
                    }
                }
            }catch ( ArrayIndexOutOfBoundsException e){
                    break;
                }
        }



    }



    //유저를 남자 리스트, 여자 리스트에 넣는 메소드
    public static void addGenderList( List<User> watingUser) {
        watingUser.forEach((user) -> {
            if (user.getGender() == 'M') {
                ManList.add(user);
            } else {
                WomanList.add(user);
            }
        });
    }

    //1번 유저 기준으로 옵션 체크
    public static boolean isMaching(User user1, User user2) {
        // 나이 체크
        if(user1.getMaxage() < user2.getAge()) return false;
        if(user1.getMinage() > user2.getAge()) return false;

        //TODO : 얕은검색 깊은검색 확인하기
        return Arrays.equals(user1.getMatchoptions(), user2.getOptions());
    }


    public static void startConnect(User user1, User user2) {
        //매칭된 남자, 여자 connectUser 에서 빼기

        //현재 남자, 여자 list 에서 빼기
        ManList.remove(user1);
        ManList.remove(user2);

        System.out.println(user1 + " " + user2);

    }
    //첫번째 남자의 위치로 정렬하는 comparator
    public static Comparator<User> optionComparator = new Comparator<User>() {
        @Override
        public int compare(User user1, User user2) {

            double ayd1 = Math.pow((user1.getLat()-nowlat),2);
            double axd1 =  Math.pow((user1.getLon()-nowlon),2);
            double user1far = Math.sqrt(ayd1+axd1);

            double ayd2 = Math.pow((user2.getLat()-nowlat),2);
            double axd2 =  Math.pow((user2.getLon()-nowlon),2);
            double user2far = Math.sqrt(ayd2+axd2);

            if (user1far < user2far) return 1;
            else return 0;
        }
    };

}
