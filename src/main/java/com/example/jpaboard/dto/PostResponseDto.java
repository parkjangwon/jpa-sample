package com.example.jpaboard.dto;

// 날짜/시간 관련 클래스들
import java.time.LocalDateTime;

/**
 * 게시물 응답 DTO (Data Transfer Object) 클래스
 * 
 * DTO는 계층 간 데이터 전송을 위한 객체입니다.
 * 서버에서 클라이언트로 보내는 데이터의 구조를 정의합니다.
 * 
 * 이 클래스는 게시물 조회 시 클라이언트에게 보내는 데이터의 구조를 정의합니다.
 */
public class PostResponseDto {

    /**
     * 게시물 ID
     */
    private Long id;

    /**
     * 게시물 제목
     */
    private String title;

    /**
     * 게시물 내용
     */
    private String content;

    /**
     * 작성자
     */
    private String author;

    /**
     * 조회수
     */
    private Integer viewCount;

    /**
     * 생성일시
     */
    private LocalDateTime createdAt;

    /**
     * 수정일시
     */
    private LocalDateTime updatedAt;

    /**
     * 기본 생성자
     * JSON 직렬화 시 필요
     */
    public PostResponseDto() {
    }

    /**
     * Post 엔티티로부터 PostResponseDto를 생성하는 생성자
     * 
     * @param post Post 엔티티
     */
    public PostResponseDto(com.example.jpaboard.entity.Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor();
        this.viewCount = post.getViewCount();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

    /**
     * 모든 필드를 포함한 생성자
     * 
     * @param id 게시물 ID
     * @param title 제목
     * @param content 내용
     * @param author 작성자
     * @param viewCount 조회수
     * @param createdAt 생성일시
     * @param updatedAt 수정일시
     */
    public PostResponseDto(Long id, String title, String content, String author, 
                          Integer viewCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter와 Setter 메서드들

    /**
     * 게시물 ID 반환
     * 
     * @return 게시물 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 게시물 ID 설정
     * 
     * @param id 게시물 ID
     */
    public void setId(Long id) {
        this.id = id;
    }

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
     * 조회수 반환
     * 
     * @return 조회수
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * 조회수 설정
     * 
     * @param viewCount 조회수
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * 생성일시 반환
     * 
     * @return 생성일시
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * 생성일시 설정
     * 
     * @param createdAt 생성일시
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 수정일시 반환
     * 
     * @return 수정일시
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 수정일시 설정
     * 
     * @param updatedAt 수정일시
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 객체의 문자열 표현을 반환
     * 디버깅이나 로깅 시 유용
     * 
     * @return 객체의 문자열 표현
     */
    @Override
    public String toString() {
        return "PostResponseDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", viewCount=" + viewCount +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 