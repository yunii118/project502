package org.choongang.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final HttpSession session;

    public boolean isAdmin(){
        if(isLogin()){
            return getMember().getAuthorities()
                    .stream().map(Authorities::getAuthority).anyMatch(a -> a == Authority.ADMIN || a == Authority.MANAGER);
        }

        return false;
    }

    /*편의메서드 추가*/
    // 의존성 주입만 해도 메서드 사용 가능함

    public boolean isLogin(){
        return getMember() != null;
    }
    public Member getMember(){
        return (Member) session.getAttribute("member");

    }

    public static void clearLoginData(HttpSession session){
        session.removeAttribute("username");
        session.removeAttribute("NotBlank_username");
        session.removeAttribute("NotBlank_password");
        session.removeAttribute("Global_error");
    }




}
