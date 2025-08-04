package com.example.jpaboard.controller;

// 스프링 웹 관련 어노테이션들
import com.example.jpaboard.dto.PostRequestDto;
import com.example.jpaboard.dto.PostResponseDto;
import com.example.jpaboard.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// 입력값 검증을 위한 어노테이션
import jakarta.validation.Valid;

/**
 * 게시물 컨트롤러 클래스
 * 
 * @RestController: 이 클래스가 REST API 컨트롤러임을 나타냄
 * @RequestMapping: 이 컨트롤러의 기본 URL 경로를 설정
 * 
 *                  컨트롤러는 클라이언트의 HTTP 요청을 받아서 서비스 계층으로 전달하고,
 *                  서비스 계층의 결과를 클라이언트에게 응답하는 역할을 담당합니다.
 */
@RestController
@RequestMapping("/api/posts") // 이 컨트롤러의 모든 엔드포인트는 /api/posts로 시작
@CrossOrigin(origins = "*") // CORS 설정 - 모든 도메인에서의 요청 허용
public class PostController {

    /**
     * 게시물 서비스
     * 비즈니스 로직을 처리하는 서비스 계층
     */
    private final PostService postService;

    /**
     * 생성자 주입 방식으로 의존성 주입
     * 
     * @param postService 게시물 서비스
     */
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 게시물 생성 API
     * 
     * @param requestDto 게시물 생성 요청 데이터
     * @return 생성된 게시물 정보와 HTTP 201 상태 코드
     * 
     * @PostMapping: POST 요청을 처리하는 메서드임을 나타냄
     * @Valid: 요청 데이터의 유효성 검사를 수행
     *         ResponseEntity: HTTP 응답 상태 코드와 본문을 함께 반환
     */
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto requestDto) {
        try {
            // 서비스 계층을 통해 게시물 생성
            PostResponseDto responseDto = postService.createPost(requestDto);

            // HTTP 201 Created 상태 코드와 함께 응답
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (Exception e) {
            // 로그 기록 (실제 프로덕션에서는 로거 사용)
            System.err.println("게시물 생성 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 게시물 목록 조회 API (페이징 포함)
     * 
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 페이징된 게시물 목록
     * 
     * @GetMapping: GET 요청을 처리하는 메서드임을 나타냄
     * @RequestParam: URL 쿼리 파라미터를 메서드 파라미터로 바인딩
     *                defaultValue: 파라미터가 없을 때의 기본값 설정
     */
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            // 페이징 파라미터 검증
            if (page < 0 || size <= 0 || size > 5) {
                return ResponseEntity.badRequest().build();
            }

            // 서비스 계층을 통해 게시물 목록 조회
            Page<PostResponseDto> posts = postService.getPosts(page, size);

            // HTTP 200 OK 상태 코드와 함께 응답
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            System.err.println("게시물 목록 조회 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 게시물 상세 조회 API
     * 
     * @param id 게시물 ID
     * @return 게시물 상세 정보
     * 
     *         @GetMapping("/{id}"): 경로 변수를 포함한 GET 요청 처리
     * @PathVariable: URL 경로의 변수를 메서드 파라미터로 바인딩
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        try {
            // ID 검증
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build();
            }

            // 서비스 계층을 통해 게시물 상세 조회
            PostResponseDto responseDto = postService.getPost(id);

            // HTTP 200 OK 상태 코드와 함께 응답
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            // 잘못된 ID로 인한 오류
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            // 게시물이 존재하지 않을 경우 HTTP 404 Not Found 응답
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("게시물 상세 조회 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 게시물 수정 API
     * 
     * @param id         게시물 ID
     * @param requestDto 수정할 게시물 데이터
     * @return 수정된 게시물 정보
     * 
     * @PutMapping: PUT 요청을 처리하는 메서드임을 나타냄
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDto requestDto) {

        try {
            // ID 검증
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build();
            }

            // 서비스 계층을 통해 게시물 수정
            PostResponseDto responseDto = postService.updatePost(id, requestDto);

            // HTTP 200 OK 상태 코드와 함께 응답
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            // 게시물이 존재하지 않을 경우 HTTP 404 Not Found 응답
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("게시물 수정 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 게시물 삭제 API
     * 
     * @param id 게시물 ID
     * @return 삭제 성공 시 HTTP 204 No Content 응답
     * 
     * @DeleteMapping: DELETE 요청을 처리하는 메서드임을 나타냄
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            // ID 검증
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build();
            }

            // 서비스 계층을 통해 게시물 삭제
            postService.deletePost(id);

            // HTTP 204 No Content 상태 코드와 함께 응답 (본문 없음)
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            // 게시물이 존재하지 않을 경우 HTTP 404 Not Found 응답
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.err.println("게시물 삭제 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 제목으로 게시물 검색 API
     * 
     * @param title 검색할 제목
     * @param page  페이지 번호 (기본값: 0)
     * @param size  페이지 크기 (기본값: 10)
     * @return 페이징된 검색 결과
     */
    @GetMapping("/search/title")
    public ResponseEntity<Page<PostResponseDto>> searchByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            // 파라미터 검증
            if (title == null || title.trim().isEmpty() || page < 0 || size <= 0 || size > 5) {
                return ResponseEntity.badRequest().build();
            }

            // 서비스 계층을 통해 제목으로 게시물 검색
            Page<PostResponseDto> posts = postService.searchByTitle(title, page, size);

            // HTTP 200 OK 상태 코드와 함께 응답
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            System.err.println("제목 검색 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 내용으로 게시물 검색 API
     * 
     * @param content 검색할 내용
     * @param page    페이지 번호 (기본값: 0)
     * @param size    페이지 크기 (기본값: 10)
     * @return 페이징된 검색 결과
     */
    @GetMapping("/search/content")
    public ResponseEntity<Page<PostResponseDto>> searchByContent(
            @RequestParam String content,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            // 파라미터 검증
            if (content == null || content.trim().isEmpty() || page < 0 || size <= 0 || size > 5) {
                return ResponseEntity.badRequest().build();
            }

            // 서비스 계층을 통해 내용으로 게시물 검색
            Page<PostResponseDto> posts = postService.searchByContent(content, page, size);

            // HTTP 200 OK 상태 코드와 함께 응답
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            System.err.println("내용 검색 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 작성자로 게시물 검색 API
     * 
     * @param author 검색할 작성자
     * @param page   페이지 번호 (기본값: 0)
     * @param size   페이지 크기 (기본값: 10)
     * @return 페이징된 검색 결과
     */
    @GetMapping("/search/author")
    public ResponseEntity<Page<PostResponseDto>> searchByAuthor(
            @RequestParam String author,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            // 파라미터 검증
            if (author == null || author.trim().isEmpty() || page < 0 || size <= 0 || size > 5) {
                return ResponseEntity.badRequest().build();
            }

            // 서비스 계층을 통해 작성자로 게시물 검색
            Page<PostResponseDto> posts = postService.searchByAuthor(author, page, size);

            // HTTP 200 OK 상태 코드와 함께 응답
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            System.err.println("작성자 검색 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 키워드로 게시물 검색 API (제목, 내용, 작성자)
     * 
     * @param keyword 검색 키워드
     * @param page    페이지 번호 (기본값: 0)
     * @param size    페이지 크기 (기본값: 10)
     * @return 페이징된 검색 결과
     */
    @GetMapping("/search/keyword")
    public ResponseEntity<Page<PostResponseDto>> searchByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            // 파라미터 검증
            if (keyword == null || keyword.trim().isEmpty() || page < 0 || size <= 0 || size > 5) {
                return ResponseEntity.badRequest().build();
            }

            // 서비스 계층을 통해 키워드로 게시물 검색
            Page<PostResponseDto> posts = postService.searchByKeyword(keyword, page, size);

            // HTTP 200 OK 상태 코드와 함께 응답
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            System.err.println("키워드 검색 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 조회수 순으로 게시물 목록 조회 API
     * 
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 페이징된 게시물 목록 (조회수 내림차순)
     */
    @GetMapping("/popular")
    public ResponseEntity<Page<PostResponseDto>> getPopularPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            // 페이징 파라미터 검증
            if (page < 0 || size <= 0 || size > 5) {
                return ResponseEntity.badRequest().build();
            }

            // 서비스 계층을 통해 조회수 순으로 게시물 목록 조회
            Page<PostResponseDto> posts = postService.getPostsByViewCount(page, size);

            // HTTP 200 OK 상태 코드와 함께 응답
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            System.err.println("인기 게시물 조회 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 최신순으로 게시물 목록 조회 API
     * 
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 10)
     * @return 페이징된 게시물 목록 (최신순)
     */
    @GetMapping("/latest")
    public ResponseEntity<Page<PostResponseDto>> getLatestPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            // 페이징 파라미터 검증
            if (page < 0 || size <= 0 || size > 5) {
                return ResponseEntity.badRequest().build();
            }

            // 서비스 계층을 통해 최신순으로 게시물 목록 조회
            Page<PostResponseDto> posts = postService.getPostsByLatest(page, size);

            // HTTP 200 OK 상태 코드와 함께 응답
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            System.err.println("최신 게시물 조회 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}