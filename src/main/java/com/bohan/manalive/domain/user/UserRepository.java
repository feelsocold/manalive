package com.bohan.manalive.domain.user;

import com.bohan.manalive.web.community.domain.Board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>,CrudRepository<User, Long>{

    // 소셜 로그인으로 반환되는 값 중 email을 통해 이미 생성된 사용자인지 판단하기 위한 메소드
    Optional<User> findByEmail(String email);
    // 휴대폰 인증절차 전, 중복 확인
    Optional<User> findByPhone(String phone);

}
