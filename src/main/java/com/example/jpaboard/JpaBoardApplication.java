package com.example.jpaboard;

// 스프링 부트 자동 설정을 위한 어노테이션
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * JPA 학습을 위한 게시판 애플리케이션 메인 클래스
 * 
 * @SpringBootApplication 어노테이션은 다음 세 가지 어노테이션을 포함합니다:
 * - @Configuration: 이 클래스가 스프링 설정 클래스임을 나타냄
 * - @EnableAutoConfiguration: 스프링 부트의 자동 설정을 활성화
 * - @ComponentScan: 컴포넌트 스캔을 활성화하여 @Component, @Service, @Repository, @Controller 등을 찾음
 */
@SpringBootApplication
public class JpaBoardApplication {

    /**
     * 애플리케이션 진입점
     * 
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        // 스프링 애플리케이션을 시작하는 메서드
        // JpaBoardApplication.class를 기준으로 컴포넌트 스캔을 수행
        SpringApplication.run(JpaBoardApplication.class, args);
    }
} 