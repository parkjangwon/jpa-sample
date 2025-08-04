package com.example.jpaboard.service;

// 스프링 관련 어노테이션들
import com.example.jpaboard.dto.PostRequestDto;
import com.example.jpaboard.dto.PostResponseDto;
import com.example.jpaboard.entity.Post;
import com.example.jpaboard.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시물 서비스 클래스
 * 
 * @Service: 이 클래스가 서비스 계층의 컴포넌트임을 나타냄
 *           스프링이 이 클래스를 빈으로 등록하여 의존성 주입을 가능하게 함
 * 
 *           서비스 계층은 비즈니스 로직을 담당하며, 컨트롤러와 리포지토리 사이의 중간 계층입니다.
 */
@Service
@Transactional // 이 클래스의 모든 메서드에 트랜잭션 적용
public class PostService {

    /**
     * 게시물 리포지토리
     * 데이터 액세스 계층과의 통신을 담당
     */
    private final PostRepository postRepository;

    /**
     * 생성자 주입 방식으로 의존성 주입
     * 
     * @param postRepository 게시물 리포지토리
     */
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * 게시물 생성
     * 
     * @param requestDto 게시물 생성 요청 데이터
     * @return 생성된 게시물 응답 데이터
     * @throws IllegalArgumentException 요청 데이터가 유효하지 않은 경우
     */
    @Transactional
    public PostResponseDto createPost(PostRequestDto requestDto) {
        // 요청 데이터 검증
        if (requestDto == null) {
            throw new IllegalArgumentException("요청 데이터가 null입니다.");
        }
        if (requestDto.getTitle() == null || requestDto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (requestDto.getContent() == null || requestDto.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
        if (requestDto.getAuthor() == null || requestDto.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("작성자는 필수입니다.");
        }

        // PostRequestDto를 Post 엔티티로 변환
        Post post = new Post(
                requestDto.getTitle().trim(),
                requestDto.getContent().trim(),
                requestDto.getAuthor().trim());

        // 엔티티를 데이터베이스에 저장
        Post savedPost = postRepository.save(post);

        // 저장된 엔티티를 PostResponseDto로 변환하여 반환
        return new PostResponseDto(savedPost);
    }

    /**
     * 게시물 목록 조회 (페이징 포함)
     * 
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @return 페이징된 게시물 목록
     * @throws IllegalArgumentException 페이징 파라미터가 유효하지 않은 경우
     */
    @Transactional(readOnly = true) // 읽기 전용 트랜잭션으로 성능 최적화
    public Page<PostResponseDto> getPosts(int page, int size) {
        // 페이징 파라미터 검증
        if (page < 0) {
            throw new IllegalArgumentException("페이지 번호는 0 이상이어야 합니다.");
        }
        if (size <= 0 || size > 5) {
            throw new IllegalArgumentException("페이지 크기는 1 이상 5 이하여야 합니다.");
        }

        // 페이징 정보 생성 (최신순 정렬)
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // 데이터베이스에서 게시물 목록 조회
        Page<Post> posts = postRepository.findAll(pageable);

        // Post 엔티티를 PostResponseDto로 변환
        return posts.map(PostResponseDto::new);
    }

    /**
     * 게시물 상세 조회
     * 
     * @param id 게시물 ID
     * @return 게시물 상세 정보
     * @throws IllegalArgumentException ID가 유효하지 않은 경우
     * @throws RuntimeException         게시물이 존재하지 않을 경우
     */
    @Transactional
    public PostResponseDto getPost(Long id) {
        // ID 검증
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("유효하지 않은 게시물 ID입니다: " + id);
        }

        // ID로 게시물 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다. ID: " + id));

        // 조회수 증가
        post.incrementViewCount();

        // 조회수 증가된 엔티티를 데이터베이스에 저장
        Post savedPost = postRepository.save(post);

        // Post 엔티티를 PostResponseDto로 변환하여 반환
        return new PostResponseDto(savedPost);
    }

    /**
     * 게시물 수정
     * 
     * @param id         게시물 ID
     * @param requestDto 수정할 게시물 데이터
     * @return 수정된 게시물 응답 데이터
     * @throws IllegalArgumentException ID가 유효하지 않거나 요청 데이터가 유효하지 않은 경우
     * @throws RuntimeException         게시물이 존재하지 않을 경우
     */
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        // ID 검증
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("유효하지 않은 게시물 ID입니다: " + id);
        }

        // 요청 데이터 검증
        if (requestDto == null) {
            throw new IllegalArgumentException("요청 데이터가 null입니다.");
        }
        if (requestDto.getTitle() == null || requestDto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (requestDto.getContent() == null || requestDto.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
        if (requestDto.getAuthor() == null || requestDto.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("작성자는 필수입니다.");
        }

        // ID로 게시물 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다. ID: " + id));

        // 게시물 정보 업데이트
        post.setTitle(requestDto.getTitle().trim());
        post.setContent(requestDto.getContent().trim());
        post.setAuthor(requestDto.getAuthor().trim());

        // 업데이트된 엔티티를 데이터베이스에 저장
        Post savedPost = postRepository.save(post);

        // Post 엔티티를 PostResponseDto로 변환하여 반환
        return new PostResponseDto(savedPost);
    }

    /**
     * 게시물 삭제
     * 
     * @param id 게시물 ID
     * @throws IllegalArgumentException ID가 유효하지 않은 경우
     * @throws RuntimeException         게시물이 존재하지 않을 경우
     */
    @Transactional
    public void deletePost(Long id) {
        // ID 검증
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("유효하지 않은 게시물 ID입니다: " + id);
        }

        // ID로 게시물 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시물을 찾을 수 없습니다. ID: " + id));

        // 게시물 삭제
        postRepository.delete(post);
    }

    /**
     * 제목으로 게시물 검색
     * 
     * @param title 검색할 제목
     * @param page  페이지 번호 (0부터 시작)
     * @param size  페이지 크기
     * @return 페이징된 검색 결과
     * @throws IllegalArgumentException 검색 파라미터가 유효하지 않은 경우
     */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> searchByTitle(String title, int page, int size) {
        // 검색 파라미터 검증
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("검색 제목은 필수입니다.");
        }
        if (page < 0) {
            throw new IllegalArgumentException("페이지 번호는 0 이상이어야 합니다.");
        }
        if (size <= 0 || size > 5) {
            throw new IllegalArgumentException("페이지 크기는 1 이상 5 이하여야 합니다.");
        }

        // 페이징 정보 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // 제목으로 게시물 검색
        Page<Post> posts = postRepository.findByTitleContainingIgnoreCase(title.trim(), pageable);

        // Post 엔티티를 PostResponseDto로 변환
        return posts.map(PostResponseDto::new);
    }

    /**
     * 내용으로 게시물 검색
     * 
     * @param content 검색할 내용
     * @param page    페이지 번호 (0부터 시작)
     * @param size    페이지 크기
     * @return 페이징된 검색 결과
     * @throws IllegalArgumentException 검색 파라미터가 유효하지 않은 경우
     */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> searchByContent(String content, int page, int size) {
        // 검색 파라미터 검증
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("검색 내용은 필수입니다.");
        }
        if (page < 0) {
            throw new IllegalArgumentException("페이지 번호는 0 이상이어야 합니다.");
        }
        if (size <= 0 || size > 5) {
            throw new IllegalArgumentException("페이지 크기는 1 이상 5 이하여야 합니다.");
        }

        // 페이징 정보 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // 내용으로 게시물 검색
        Page<Post> posts = postRepository.findByContentContainingIgnoreCase(content.trim(), pageable);

        // Post 엔티티를 PostResponseDto로 변환
        return posts.map(PostResponseDto::new);
    }

    /**
     * 작성자로 게시물 검색
     * 
     * @param author 검색할 작성자
     * @param page   페이지 번호 (0부터 시작)
     * @param size   페이지 크기
     * @return 페이징된 검색 결과
     * @throws IllegalArgumentException 검색 파라미터가 유효하지 않은 경우
     */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> searchByAuthor(String author, int page, int size) {
        // 검색 파라미터 검증
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("검색 작성자는 필수입니다.");
        }
        if (page < 0) {
            throw new IllegalArgumentException("페이지 번호는 0 이상이어야 합니다.");
        }
        if (size <= 0 || size > 5) {
            throw new IllegalArgumentException("페이지 크기는 1 이상 5 이하여야 합니다.");
        }

        // 페이징 정보 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // 작성자로 게시물 검색
        Page<Post> posts = postRepository.findByAuthorContainingIgnoreCase(author.trim(), pageable);

        // Post 엔티티를 PostResponseDto로 변환
        return posts.map(PostResponseDto::new);
    }

    /**
     * 키워드로 게시물 검색 (제목, 내용, 작성자)
     * 
     * @param keyword 검색 키워드
     * @param page    페이지 번호 (0부터 시작)
     * @param size    페이지 크기
     * @return 페이징된 검색 결과
     * @throws IllegalArgumentException 검색 파라미터가 유효하지 않은 경우
     */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> searchByKeyword(String keyword, int page, int size) {
        // 검색 파라미터 검증
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색 키워드는 필수입니다.");
        }
        if (page < 0) {
            throw new IllegalArgumentException("페이지 번호는 0 이상이어야 합니다.");
        }
        if (size <= 0 || size > 5) {
            throw new IllegalArgumentException("페이지 크기는 1 이상 5 이하여야 합니다.");
        }

        // 페이징 정보 생성
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // 키워드로 게시물 검색 (제목, 내용, 작성자)
        Page<Post> posts = postRepository.findByKeywordInAllFields(keyword.trim(), pageable);

        // Post 엔티티를 PostResponseDto로 변환
        return posts.map(PostResponseDto::new);
    }

    /**
     * 조회수 순으로 게시물 목록 조회
     * 
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @return 페이징된 게시물 목록 (조회수 내림차순)
     * @throws IllegalArgumentException 페이징 파라미터가 유효하지 않은 경우
     */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPostsByViewCount(int page, int size) {
        // 페이징 파라미터 검증
        if (page < 0) {
            throw new IllegalArgumentException("페이지 번호는 0 이상이어야 합니다.");
        }
        if (size <= 0 || size > 5) {
            throw new IllegalArgumentException("페이지 크기는 1 이상 5 이하여야 합니다.");
        }

        // 페이징 정보 생성 (조회수 내림차순)
        Pageable pageable = PageRequest.of(page, size, Sort.by("viewCount").descending());

        // 조회수 순으로 게시물 목록 조회
        Page<Post> posts = postRepository.findAllByOrderByViewCountDesc(pageable);

        // Post 엔티티를 PostResponseDto로 변환
        return posts.map(PostResponseDto::new);
    }

    /**
     * 최신순으로 게시물 목록 조회
     * 
     * @param page 페이지 번호 (0부터 시작)
     * @param size 페이지 크기
     * @return 페이징된 게시물 목록 (최신순)
     * @throws IllegalArgumentException 페이징 파라미터가 유효하지 않은 경우
     */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPostsByLatest(int page, int size) {
        // 페이징 파라미터 검증
        if (page < 0) {
            throw new IllegalArgumentException("페이지 번호는 0 이상이어야 합니다.");
        }
        if (size <= 0 || size > 5) {
            throw new IllegalArgumentException("페이지 크기는 1 이상 5 이하여야 합니다.");
        }

        // 페이징 정보 생성 (생성일시 내림차순)
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // 최신순으로 게시물 목록 조회
        Page<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        // Post 엔티티를 PostResponseDto로 변환
        return posts.map(PostResponseDto::new);
    }
}