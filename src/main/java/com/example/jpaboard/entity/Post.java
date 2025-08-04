package com.example.jpaboard.entity;

// JPA 관련 어노테이션들을 임포트
import jakarta.persistence.*;
// 입력값 검증을 위한 어노테이션들
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
// 날짜/시간 관련 클래스들
import java.time.LocalDateTime;

/**
 * 게시물 엔티티 클래스
 * 
 * @Entity: 이 클래스가 JPA 엔티티임을 나타내는 어노테이션
 * JPA가 이 클래스를 기반으로 데이터베이스 테이블을 생성하고 관리함
 */
@Entity
// 테이블 이름을 명시적으로 지정 (기본값은 클래스명과 동일)
@Table(name = "posts")
public class Post {

    /**
     * 게시물 ID (기본키)
     * 
     * @Id: 이 필드가 기본키(Primary Key)임을 나타냄
     * @GeneratedValue: 기본키 값이 자동으로 생성됨을 나타냄
     * strategy = GenerationType.IDENTITY: 데이터베이스의 AUTO_INCREMENT 기능 사용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 게시물 제목
     * 
     * @Column: 데이터베이스 컬럼 설정
     * nullable = false: NULL 값 허용하지 않음
     * length = 200: 컬럼 길이 제한
     * 
     * @NotBlank: 빈 문자열이나 공백만 있는 문자열을 허용하지 않음
     * @Size: 문자열 길이 제한 (최소 1자, 최대 200자)
     */
    @Column(nullable = false, length = 200)
    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 1, max = 200, message = "제목은 1자 이상 200자 이하여야 합니다.")
    private String title;

    /**
     * 게시물 내용
     * 
     * @Column: 데이터베이스 컬럼 설정
     * columnDefinition = "TEXT": 데이터베이스에서 TEXT 타입으로 저장
     * 
     * @NotBlank: 빈 문자열이나 공백만 있는 문자열을 허용하지 않음
     * @Size: 문자열 길이 제한 (최소 1자, 최대 10000자)
     */
    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 1, max = 10000, message = "내용은 1자 이상 10000자 이하여야 합니다.")
    private String content;

    /**
     * 작성자
     * 
     * @Column: 데이터베이스 컬럼 설정
     * nullable = false: NULL 값 허용하지 않음
     * length = 100: 컬럼 길이 제한
     * 
     * @NotBlank: 빈 문자열이나 공백만 있는 문자열을 허용하지 않음
     * @Size: 문자열 길이 제한 (최소 1자, 최대 100자)
     */
    @Column(nullable = false, length = 100)
    @NotBlank(message = "작성자는 필수입니다.")
    @Size(min = 1, max = 100, message = "작성자는 1자 이상 100자 이하여야 합니다.")
    private String author;

    /**
     * 조회수
     * 
     * @Column: 데이터베이스 컬럼 설정
     * nullable = false: NULL 값 허용하지 않음
     * columnDefinition = "INT DEFAULT 0": 기본값 0으로 설정
     */
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer viewCount = 0;

    /**
     * 생성일시
     * 
     * @Column: 데이터베이스 컬럼 설정
     * nullable = false: NULL 값 허용하지 않음
     * updatable = false: 수정 시 이 필드는 업데이트하지 않음
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 수정일시
     * 
     * @Column: 데이터베이스 컬럼 설정
     * nullable = false: NULL 값 허용하지 않음
     */
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 기본 생성자
     * JPA에서 엔티티를 생성할 때 사용
     */
    public Post() {
    }

    /**
     * 모든 필드를 포함한 생성자
     * 
     * @param title 제목
     * @param content 내용
     * @param author 작성자
     */
    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewCount = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 엔티티가 저장되기 전에 호출되는 메서드
     * 생성일시와 수정일시를 자동으로 설정
     */
    @PrePersist
    protected void onCreate() {
        // 엔티티가 처음 저장될 때 현재 시간으로 설정
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * 엔티티가 업데이트되기 전에 호출되는 메서드
     * 수정일시를 자동으로 업데이트
     */
    @PreUpdate
    protected void onUpdate() {
        // 엔티티가 업데이트될 때 현재 시간으로 설정
        updatedAt = LocalDateTime.now();
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
     * 게시물 제목 반환
     * 
     * @return 게시물 제목
     */
    public String getTitle() {
        return title;
    }

    /**
     * 게시물 제목 설정
     * 
     * @param title 게시물 제목
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 게시물 내용 반환
     * 
     * @return 게시물 내용
     */
    public String getContent() {
        return content;
    }

    /**
     * 게시물 내용 설정
     * 
     * @param content 게시물 내용
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
     * 조회수 증가 메서드
     * 게시물을 조회할 때마다 호출되어 조회수를 1 증가시킴
     */
    public void incrementViewCount() {
        this.viewCount++;
    }

    /**
     * 객체의 문자열 표현을 반환
     * 디버깅이나 로깅 시 유용
     * 
     * @return 객체의 문자열 표현
     */
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", viewCount=" + viewCount +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 