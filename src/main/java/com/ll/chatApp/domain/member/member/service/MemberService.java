package com.ll.chatApp.domain.member.member.service;
import com.ll.chatApp.domain.article.article.entity.Article;
import com.ll.chatApp.domain.member.member.entity.Member;
import com.ll.chatApp.domain.member.member.repository.MemberRepository;
import com.ll.chatApp.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public Member join(String username, String password) {
        Member existingMember = memberRepository.findByUsername(username);
        if (existingMember != null) {
            return existingMember; // 중복 회원이 있으면 해당 회원 반환
        }
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        return memberRepository.save(member);
    }

    /*public Member join(String username, String password){
        Member CheckedSignUpMember = memberRepository.findByUsername(username);
        if (CheckedSignUpMember != null) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }
        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        return memberRepository.save(member);
    }*/
    public Optional<Member> findById(Long id) {

        return memberRepository.findById(id);
    }
    public Member getMember(String username) {
        return memberRepository.findByUsername(username);
    }


}
