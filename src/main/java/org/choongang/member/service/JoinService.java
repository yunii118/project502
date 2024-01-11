package org.choongang.member.service;


import lombok.RequiredArgsConstructor;
import org.choongang.file.service.FileUploadService;
import org.choongang.member.constants.Authority;
import org.choongang.member.constants.Gender;
import org.choongang.member.controllers.JoinValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entities.*;
import org.choongang.member.repositories.AddressRepositoriy;
import org.choongang.member.repositories.AuthoritiesRepository;
import org.choongang.member.repositories.FarmerRepository;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@Service
@Transactional
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final FarmerRepository farmerRepository;

    private final AuthoritiesRepository authoritiesRepository;
    private final JoinValidator validator;
    private final PasswordEncoder encoder;
    private final FileUploadService fileUploadService;
    private final AddressRepositoriy addressRepositoriy;


    public void process(RequestJoin form, Errors errors){
        validator.validate(form, errors);
        if (errors.hasErrors()){
            return;
        }
        // 비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());
        String mType = form.getMTYPE();
        AbstractMember member = mType.equals("F") ? new Farmer() : new Member();
        member.setEmail(form.getEmail());
        member.setUsername(form.getUsername());
        member.setPassword(hash);
        member.setUserId(form.getUserId());
        member.setGid(form.getGid());
        member.setNickname(form.getNickname());
        member.setTel(form.getTel());


        if(mType.equals("F")) {
            // 농장회원
            Farmer farmer = (Farmer) member;
            farmer.setFarmTitle(form.getFarmTitle());

            processFarmer(farmer);
        }else{
            // 일반회원
            Member _member = (Member) member;
            _member.setGender(Gender.valueOf(form.getGender()));
            _member.setBirthdate(form.getBirthdate());

            processMember(_member);
        }

       // 회원가입시 일반사용자 권한 부여
        Authorities authorities = new Authorities();
        authorities.setMember(member);
        authorities.setAuthority(Authority.USER);
        authoritiesRepository.saveAndFlush(authorities);

        // 주소 처리
        Address address = Address.builder()
                .zoneCode(form.getZoneCode())
                .address(form.getAddress())
                .addressSub(form.getAddressSub())
                .member(member)
                .defaultAddress(true)
                .build();

        addressRepositoriy.saveAndFlush(address);


        // 파일 업로드 완료 처리
        fileUploadService.processDone(form.getGid());


    }

    public void processMember(Member member){
        memberRepository.saveAndFlush(member);
    }

    public void processFarmer(Farmer member){
        farmerRepository.saveAndFlush(member);
    }
}
