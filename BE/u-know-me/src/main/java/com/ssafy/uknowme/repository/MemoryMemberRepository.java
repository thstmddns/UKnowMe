//package com.ssafy.uknowme.repository;
//
//import com.ssafy.uknowme.web.domain.Member;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//
//import static org.graalvm.compiler.nodeinfo.Verbosity.Id;
//
//@Repository
//public class MemoryMemberRepository implements MemberRepository{
//
//    private static Map<String, Member> store = new HashMap<>();
//    private static long sequence = 0L;
//
//
//    @Override
//    public Member save(Member member) {
//        member.member_seq(++sequence);               // id 세팅
//        store.put(member.getId(), member);      // store에 저장
//        return member;
//    }
//
//    @Override
//    public Optional<Member> findById(String id) {
//        return Optional.ofNullable(store.get(id));  // store에서 id를 가져옴
//    }
//
//    @Override
//    public Optional<Member> findByName(String name) {
//        return store.values().stream()
//                .filter(member -> member.getName().equals(name))  //같은 경우에만 필터링
//                .findAny();  //하나 찾아지면 반롼, 없으면 null
//
//    }
//
//    @Override
//    public List<Member> findAll() {
//        return new ArrayList<>(store.values());
//    }
//}

