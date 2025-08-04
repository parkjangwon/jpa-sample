package com.example.jpaboard.dto;

// 입력값 검증을 위한 어노테이션들
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 게시물 요청 DTO (Data Transfer Object) 클래스
 * 
 * DTO는 계층 간 데이터 전송을 위한 객체입니다.
 * 클라이언트로부터 받은 데이터를 서버에서 처리하기 위한 형태로 변환합니다.
 * 
 * 이 클래스는 게시물 생성 및 수정 시 클라이언트로부터 받는 데이터의 구조를 정의합니다.
 */
public class PostRequestDto {

    /**
     * 게시물 제목
     * 
     * @NotBlank: 빈 문자열이나 공백만 있는 문자열을 허용하지 않음
     * @Size: 문자열 길이 제한 (최소 1자, 최대 200자)
     */
    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 1, max = 200, message = "제목은 1자 이상 200자 이하여야 합니다.")
    private String title;

    /**
     * 게시물 내용
     * 
     * @NotBlank: 빈 문자열이나 공백만 있는 문자열을 허용하지 않음
     * @Size: 문자열 길이 제한 (최소 1자, 최대 10000자)
     */
    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 1, max = 10000, message = "내용은 1자 이상 10000자 이하여야 합니다.")
    private String content;

    /**
     * 작성자
     * 
     * @NotBlank: 빈 문자열이나 공백만 있는 문자열을 허용하지 않음
     * @Size: 문자열 길이 제한 (최소 1자, 최대 100자)
     */
    @NotBlank(message = "작성자는 필수입니다.")
    @Size(min = 1, max = 100, message = "작성자는 1자 이상 100자 이하여야 합니다.")
    private String author;

    /**
     * 기본 생성자
     * JSON 역직렬화 시 필요
     */
    public PostRequestDto() {
    }

    /**
     * 모든 필드를 포함한 생성자
     * 
     * @param title 제목
     * @param content 내용
     * @param author 작성자
     */
    public PostRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // Getter와 Setter 메서드들

    /**
     * 제목 반환
     * 
     * @return 제목
     */
    public String getTitle() {
        return title;
    }

    /**
     * 제목 설정
     * 
     * @param title 제목
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 내용 반환
     * 
     * @return 내용
     */
    public String getContent() {
        return content;
    }

    /**
     * 내용 설정
     * 
     * @param content 내용
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 작성자 반환
     * 
     * @return 작성자
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 작성자 설정
     * 
     * @param author 작성자
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 객체의 문자열 표현을 반환
     * 디버깅이나 로깅 시 유용
     * 
     * @return 객체의 문자열 표현
     */
    @Override
    public String toString() {
        return "PostRequestDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
} 