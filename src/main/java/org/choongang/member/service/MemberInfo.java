package org.choongang.member.service;

import lombok.Builder;
import lombok.Data;
import org.choongang.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String userId;
    private String password;
    private Member member;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return StringUtils.hasText(email)? email: userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠김 화깅ㄴ
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호 만료 확인
        // -> ex) 6개월마다 비밀번호 확인
        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
