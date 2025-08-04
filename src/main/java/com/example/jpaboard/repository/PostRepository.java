package com.example.jpaboard.repository;

// 스프링 데이터 JPA 관련 클래스들
import com.example.jpaboard.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 게시물 Repository 인터페이스
 * 
 * @Repository: 이 인터페이스가 데이터 액세스 계층의 컴포넌트임을 나타냄
 * 스프링이 이 인터페이스를 구현체로 자동 생성하여 빈으로 등록함
 * 
 * JpaRepository<Post, Long>: 
 * - Post: 엔티티 타입
 * - Long: 엔티티의 기본키 타입
 * 
 * JpaRepository는 다음 기능들을 제공합니다:
 * - 기본 CRUD 메서드 (save, findById, findAll, delete 등)
 * - 페이징 및 정렬 기능
 * - 쿼리 메서드 기능
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 제목으로 게시물 검색 (페이징 포함)
     * 
     * @param title 검색할 제목 (부분 일치)
     * @param pageable 페이징 정보
     * @return 페이징된 게시물 목록
     * 
     * 메서드명 규칙:
     * - findBy: 조회 메서드임을 나타냄
     * - Title: 엔티티의 title 필드를 기준으로 검색
     * - Containing: 부분 일치 검색 (LIKE 쿼리)
     * - IgnoreCase: 대소문자 구분 없이 검색
     */
    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    /**
     * 내용으로 게시물 검색 (페이징 포함)
     * 
     * @param content 검색할 내용 (부분 일치)
     * @param pageable 페이징 정보
     * @return 페이징된 게시물 목록
     */
    Page<Post> findByContentContainingIgnoreCase(String content, Pageable pageable);

    /**
     * 작성자로 게시물 검색 (페이징 포함)
     * 
     * @param author 검색할 작성자 (부분 일치)
     * @param pageable 페이징 정보
     * @return 페이징된 게시물 목록
     */
    Page<Post> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    /**
     * 제목 또는 내용으로 게시물 검색 (페이징 포함)
     * 
     * @param title 검색할 제목 (부분 일치)
     * @param content 검색할 내용 (부분 일치)
     * @param pageable 페이징 정보
     * @return 페이징된 게시물 목록
     * 
     * 메서드명 규칙:
     * - findBy: 조회 메서드임을 나타냄
     * - TitleContainingOrContentContaining: 제목 또는 내용에 포함된 검색
     * - IgnoreCase: 대소문자 구분 없이 검색
     */
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title, String content, Pageable pageable);

    /**
     * 제목, 내용, 작성자로 게시물 검색 (페이징 포함)
     * 
     * @param title 검색할 제목 (부분 일치)
     * @param content 검색할 내용 (부분 일치)
     * @param author 검색할 작성자 (부분 일치)
     * @param pageable 페이징 정보
     * @return 페이징된 게시물 목록
     */
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrAuthorContainingIgnoreCase(
            String title, String content, String author, Pageable pageable);

    /**
     * 조회수로 정렬하여 게시물 목록 조회 (페이징 포함)
     * 
     * @param pageable 페이징 정보
     * @return 페이징된 게시물 목록 (조회수 내림차순)
     */
    Page<Post> findAllByOrderByViewCountDesc(Pageable pageable);

    /**
     * 최신순으로 게시물 목록 조회 (페이징 포함)
     * 
     * @param pageable 페이징 정보
     * @return 페이징된 게시물 목록 (생성일시 내림차순)
     */
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * 제목 또는 내용으로 게시물 검색 (JPQL 사용)
     * 
     * @param keyword 검색 키워드
     * @param pageable 페이징 정보
     * @return 페이징된 게시물 목록
     * 
     * @Query: JPQL 쿼리를 직접 작성할 때 사용
     * JPQL은 SQL과 유사하지만 엔티티와 필드를 대상으로 쿼리를 작성
     * 
     * 쿼리 설명:
     * - SELECT p FROM Post p: Post 엔티티를 p라는 별칭으로 조회
     * - WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')): 
     *   제목을 소문자로 변환하여 키워드가 포함된지 검색
     * - OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')):
     *   내용을 소문자로 변환하여 키워드가 포함된지 검색
     * - LOWER(): 문자열을 소문자로 변환 (대소문자 구분 없이 검색)
     * - CONCAT('%', :keyword, '%'): 키워드 앞뒤에 %를 붙여 부분 일치 검색
     */
    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Post> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 제목, 내용, 작성자로 게시물 검색 (JPQL 사용)
     * 
     * @param keyword 검색 키워드
     * @param pageable 페이징 정보
     * @return 페이징된 게시물 목록
     */
    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.content) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Post> findByKeywordInAllFields(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 조회수가 높은 게시물 상위 N개 조회
     * 
     * @param limit 조회할 게시물 개수
     * @return 조회수 내림차순으로 정렬된 게시물 목록
     * 
     * @Query: JPQL 쿼리를 직접 작성
     * ORDER BY p.viewCount DESC: 조회수 내림차순 정렬
     * LIMIT :limit: 조회할 개수 제한
     */
    @Query("SELECT p FROM Post p ORDER BY p.viewCount DESC")
    Page<Post> findTopPostsByViewCount(Pageable pageable);

    /**
     * 특정 기간 내의 게시물 조회
     * 
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @param pageable 페이징 정보
     * @return 페이징된 게시물 목록
     */
    @Query("SELECT p FROM Post p WHERE p.createdAt BETWEEN :startDate AND :endDate " +
           "ORDER BY p.createdAt DESC")
    Page<Post> findByCreatedAtBetween(@Param("startDate") java.time.LocalDateTime startDate,
                                     @Param("endDate") java.time.LocalDateTime endDate,
                                     Pageable pageable);
} 