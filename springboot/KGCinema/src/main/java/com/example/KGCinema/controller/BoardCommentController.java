package com.example.KGCinema.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.KGCinema.dto.CommentDTO;
import com.example.KGCinema.entity.Comment;
import com.example.KGCinema.service.CommentService;
import com.example.KGCinema.service.UsersService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/board/comment")
public class BoardCommentController {
	@Autowired
	CommentService service;

	@Autowired
	UsersService usersService;

	@Autowired
	HttpSession session;

	// 댓글 작성
	@PostMapping("/add")
	public Comment addComment(@RequestBody Comment comment) {
		String user_id = (String) session.getAttribute("user_id");
		String user_name = usersService.findNameById(user_id);
		
		comment.setLogtime(new Date());
		comment.setUser_id(user_id);
		comment.setAuthor_name(user_name);
		
		return service.addComment(comment);
	}

	// 특정 게시글 댓글 조회
	@GetMapping("/list/{boardSeq}")
	public List<Comment> getComments(@PathVariable("boardSeq") int boardSeq) {
		return service.getCommentsByBoard(boardSeq);
	}
	
	// 삭제
	@DeleteMapping("/delete/{commentSeq}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable("commentSeq") int commentSeq) {
        boolean success = service.deleteComment(commentSeq);

        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("rt", "OK");
            return ResponseEntity.ok(response);
        } else {
            response.put("rt", "FAIL");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
	
	 // 댓글 수정
    @PutMapping("/update")
    public Map<String, Object> updateComment(@RequestBody CommentDTO dto) {
        Map<String, Object> map = new HashMap<>();
        String loginId = (String) session.getAttribute("user_id");

        // 댓글 조회
        Comment comment = service.getComment(dto.getComment_seq());
        if (comment == null) {
            map.put("rt", "FAIL");
            map.put("msg", "댓글이 존재하지 않습니다.");
            return map;
        }

        // 작성자 확인
        if (!loginId.equals(comment.getUser_id())) {
            map.put("rt", "NO_AUTH");
            map.put("msg", "수정 권한이 없습니다.");
            return map;
        }

        boolean result = service.updateComment(dto, loginId);
        map.put("rt", result ? "OK" : "FAIL");
        return map;
    }
}
