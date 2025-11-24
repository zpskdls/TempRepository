package com.example.KGCinema.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KGCinema.dto.BoardDTO;
import com.example.KGCinema.entity.Board;
import com.example.KGCinema.service.BoardService;
import com.example.KGCinema.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;

	@Autowired
	UsersService usersService;

	@Autowired
	HttpSession session;
	
	// 1. 작성
	@PostMapping("/write")
	public Map<String, Object> insertBoard(@RequestBody BoardDTO dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		String user_id = (String) session.getAttribute("user_id");
		String user_name = usersService.findNameById(user_id);
		dto.setLogtime(new Date());
		dto.setUser_id(user_id);
		dto.setAuthor_name(user_name);

		Board board = boardService.boardWrite(dto);

		if (board != null) {
			map.put("rt", "OK");
		} else {
			map.put("rt", "FAIL");
		}
		return map;
	}

	// 2. 상세보기
	@GetMapping("/view/{seq}")
	public Map<String, Object> boardView(@PathVariable("seq") int seq) {
		boardService.boardUpdateHit(seq);
		Board board = boardService.boardView(seq);

		Map<String, Object> map = new HashMap<>();
		if (board != null) {
			map.put("rt", "OK");
			map.put("total", 1);
			map.put("item", board);
		} else {
			map.put("rt", "FAIL");
		}
		return map;
	}

	// 3. 목록보기
	@GetMapping("/list")
	public Map<String, Object> boardList(HttpServletRequest request) {
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 목록
		int pageSize = 10; // 한 페이지당 게시글 수
		int endNum = pg * pageSize;
		int startNum = (endNum - (pageSize - 1));
		List<Board> list = boardService.boardList(startNum, endNum);

		// 페이징 : 3블록
		int totalA = boardService.getCount();
		int totalP = (totalA + pageSize - 1) / pageSize;
		int startPage = (pg - 1) / 3 * 3 + 1;
		int endPage = startPage + 2;
		if (endPage > totalP)
			endPage = totalP;

		// 결과
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rt", "OK");
		map.put("total", list.size());
		map.put("pg", pg);
		map.put("totalP", totalP);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		map.put("items", list);
		return map;
	}

	// 게시글 수정 화면 조회
    @GetMapping("/modify/{seq}")
    public Map<String, Object> modifyView(@PathVariable("seq") int seq) {
        Map<String, Object> map = new HashMap<>();
        String loginUserId = (String) session.getAttribute("user_id");

        Board board = boardService.boardView(seq);
        if (board == null) {
            map.put("rt", "FAIL");
            map.put("msg", "게시글이 존재하지 않습니다.");
            return map;
        }

        // 작성자 체크
        if (!loginUserId.equals(board.getUser_id())) {
            map.put("rt", "NO_AUTH");
            map.put("msg", "수정 권한이 없습니다.");
            return map;
        }

        map.put("rt", "OK");
        map.put("item", board);
        return map;
    }

    // 게시글 수정 제출
    @PostMapping("/modify")
    public Map<String, Object> modifySubmit(@RequestBody BoardDTO dto) {
        Map<String, Object> map = new HashMap<>();
        String loginUserId = (String) session.getAttribute("user_id");

        Board board = boardService.boardView(dto.getSeq());
        if (board == null) {
            map.put("rt", "FAIL");
            map.put("msg", "게시글이 존재하지 않습니다.");
            return map;
        }

        // 작성자 체크
        if (!loginUserId.equals(board.getUser_id())) {
            map.put("rt", "NO_AUTH");
            map.put("msg", "수정 권한이 없습니다.");
            return map;
        }

        boolean updateResult = boardService.updateBoard(dto, loginUserId);
        map.put("rt", updateResult ? "OK" : "FAIL");
        return map;
    }

	// 5. 삭제하기
	@DeleteMapping("/delete/{seq}")
	public Map<String, Object> deleteBoard(@PathVariable("seq") int seq, HttpSession session) {
	    String userId = (String) session.getAttribute("user_id"); // 로그인 사용자
	    Map<String, Object> map = new HashMap<>();

	    boolean result = boardService.boardDelete(seq, userId);

	    map.put("rt", result ? "OK" : "FAIL");
	    return map;
	}
}
