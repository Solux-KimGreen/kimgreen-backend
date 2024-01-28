package com.kimgreen.backend.domain.member.service;

import com.kimgreen.backend.config.Authentication.JwtProvider;
import com.kimgreen.backend.domain.BadgeList;
import com.kimgreen.backend.domain.notification.service.FCMService;
import com.kimgreen.backend.domain.member.dto.Auth.ChangePasswordDto;
import com.kimgreen.backend.domain.member.dto.Auth.LogInRequestDto;
import com.kimgreen.backend.domain.member.dto.Auth.SignUpRequestDto;
import com.kimgreen.backend.domain.member.dto.Auth.TokenDto;
import com.kimgreen.backend.domain.member.entity.Member;
import com.kimgreen.backend.domain.member.entity.RefreshToken;
import com.kimgreen.backend.domain.member.repository.MemberProfileImgRepository;
import com.kimgreen.backend.domain.member.repository.MemberRepository;
import com.kimgreen.backend.domain.member.repository.RefreshTokenRepository;
import com.kimgreen.backend.domain.profile.entity.Badge;
import com.kimgreen.backend.domain.profile.entity.ProfileBadge;
import com.kimgreen.backend.domain.profile.entity.RepresentativeBadge;
import com.kimgreen.backend.domain.profile.repository.BadgeRepository;
import com.kimgreen.backend.domain.profile.repository.ProfileBadgeRepository;
import com.kimgreen.backend.domain.profile.repository.RepresentativeBadgeRepository;
import com.kimgreen.backend.exception.DuplicateEmail;
import com.kimgreen.backend.exception.LogInFailureEmail;
import com.kimgreen.backend.exception.LogInFailurePassword;
import com.kimgreen.backend.exception.RefreshTokenExpired;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberProfileImgRepository profileImgRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BadgeRepository badgeRepository;
    private final ProfileBadgeRepository profileBadgeRepository;
    private final RepresentativeBadgeRepository representativeBadgeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberService memberService;
    private static final String SUCCESS = "success";
    private static final String EXPIRED = "expired";
    private final FCMService FCMService;

    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        String email = signUpRequestDto.getEmail();
        String password = signUpRequestDto.getPassword();
        String nickname = signUpRequestDto.getName();

        validateEmail(email);
        saveMember(signUpRequestDto,email, password, nickname);
        updateSprout(memberRepository.findByEmail(email));
    }

    @Transactional
    public void logout(String token) {
        SecurityContextHolder.clearContext();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Authorization", "");
    }

    @Transactional
    public TokenDto logIn(LogInRequestDto dto) {
        String email = dto.getEmail();
        if(!(memberRepository.existsByEmail(email))) {
            throw new LogInFailureEmail();
        }
        Member member = memberRepository.findByEmail(dto.getEmail());
        checkPassword(dto.getPassword(), member.getPassword());


        // user 검증
        Authentication authentication = setAuthentication(dto);
        // token 생성
        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);
        User user = (User) authentication.getPrincipal(); // user 정보
        RefreshToken generatedRefreshToken = RefreshToken.builder()
                .refreshToken(refreshToken)
                .email(memberRepository.findByEmail(email).getEmail())
                .build();
        // refresh token 저장
        saveRefreshToken(email, generatedRefreshToken);
        // FCM token 저장
        //FCMService.saveToken(member,dto.getFcmToken());


        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }

    @Transactional
    public TokenDto tokenReissue(TokenDto tokenDto) {
        //어차피 accessToken 만료인 경우에 호출되기 때문에 AT는 검증할 필요X
        String accessToken = tokenDto.getAccessToken();
        String refreshToken = tokenDto.getRefreshToken();
        Authentication authentication = jwtProvider.getAuthentication(refreshToken);
        String email = authentication.getName();

        // refresh token 검증
        if (StringUtils.hasText(refreshToken) && jwtProvider.validateToken(refreshToken).equals(SUCCESS)) {
            System.out.println("getting new access token");
            // access token 재발급
            String newAccessToken = jwtProvider.generateAccessToken(authentication);

            System.out.println("Reissue access token success");
            return tokenDto.builder()
                    .refreshToken(refreshToken)
                    .accessToken(newAccessToken)
                    .build();
        } else { //refresh token 만료
            refreshTokenRepository.deleteByEmail(email);
            //RT 만료됐다는걸 알리는 예외 발생 -> 로그인으로 유도
            throw new RefreshTokenExpired();
        }
    }

    @Transactional
    public void changePassword(ChangePasswordDto dto) {
        Member member = memberService.getCurrentMember();
        checkPassword(dto.getPasswordToCheck(), member.getPassword());
        member.changePassword(passwordEncoder.encode(dto.getNewPassword()));
    }

    public void checkPassword(String getPassword, String password) {
        if (!(passwordEncoder.matches(getPassword, password))) {
            throw new LogInFailurePassword();
        }
    }

    public Authentication setAuthentication(LogInRequestDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
    public void saveRefreshToken(String email, RefreshToken generatedRefreshToken) {
        if (!refreshTokenRepository.existsByEmail(email)) {
            refreshTokenRepository.save(generatedRefreshToken);
        } else {
            refreshTokenRepository.findByEmail(email).updateRefreshToken(generatedRefreshToken.getRefreshToken());
        }
    }

    public void validateEmail(String email) {
        if(memberRepository.existsByEmail(email)) {
            throw new DuplicateEmail();
        }
    }

    @Transactional
    public void saveMember(SignUpRequestDto signUpRequestDto,String email, String password, String nickname) {
        memberRepository.save(signUpRequestDto.toMemberEntity(email, passwordEncoder.encode(password),nickname));
        Member member = memberRepository.findByEmail(email);
        profileImgRepository.save(signUpRequestDto.toMemberProfileImgEntity(member));
        badgeRepository.save(Badge.builder().member(member).build());
        profileBadgeRepository.save(ProfileBadge.builder()
                .member(member)
                .profileBadge_1(BadgeList.BLANK)
                .profileBadge_2(BadgeList.BLANK)
                .profileBadge_3(BadgeList.BLANK)
                .profileBadge_4(BadgeList.BLANK)
                .profileBadge_5(BadgeList.BLANK).build());
        representativeBadgeRepository.save(
                RepresentativeBadge.builder()
                        .representativeBadge(BadgeList.BLANK)
                        .member(member)
                        .build());
    }

    @Transactional
    public void updateSprout(Member member) {
        badgeRepository.findByMember(member).setSproutIsAchieved(true);
    }

}
