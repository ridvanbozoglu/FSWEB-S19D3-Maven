package com.workintech.s19d2.service;

import com.workintech.s19d2.repository.MemberRepository;
import com.workintech.s19d2.repository.RoleRepository;
import com.workintech.s19d2.dto.RegisterResponse;
import com.workintech.s19d2.dto.RegistrationMember;
import com.workintech.s19d2.entity.Member;
import com.workintech.s19d2.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService {
    private MemberRepository memberRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(MemberRepository memberRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse register(RegistrationMember registrationMember){
        String encodedPassword = passwordEncoder.encode(registrationMember.password());
        Role userRole = roleRepository.findByAuthority("USER").get();

        List<Role> roles = new LinkedList<>();
        roles.add(userRole);

        Member member = new Member();
        member.setEmail(registrationMember.email());
        member.setPassword(encodedPassword);
        member.setRoles(roles);

        Member newMember = memberRepository.save(member);

        return new RegisterResponse(newMember.getEmail(), "Başarıyla kayıt olundu");
    }

    public Member register(String email, String password){
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent()) {
            throw new RuntimeException("User with given email already exist");
        }
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("ADMIN").get();


        List<Role> roles = new LinkedList<>();
        roles.add(userRole);

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encodedPassword);
        member.setRoles(roles);

        return memberRepository.save(member);
    }
}
