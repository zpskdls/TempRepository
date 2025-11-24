package com.example.KGCinema.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KGCinema.dto.CommentDTO;
import com.example.KGCinema.entity.Comment;
import com.example.KGCinema.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	CommentRepository commentRepository;

	public CommentService(CommentRepository repository) {
		this.commentRepository = repository;
	}

	// 댓글 작성
	public Comment addComment(Comment comment) {
		return commentRepository.save(comment);
	}

	// 게시글 댓글 조회
	public List<Comment> getCommentsByBoard(int boardSeq) {
		return commentRepository.findByBoardSeqOrderByLogtimeAsc(boardSeq);
	}

	// 단일 댓글 조회
	public Comment getComment(int commentSeq) {
		return commentRepository.findById(commentSeq).orElse(null);
	}

	// 삭제
	public boolean deleteComment(int commentSeq) {
		Optional<Comment> commentOpt = commentRepository.findById(commentSeq);
		if (commentOpt.isPresent()) {
			commentRepository.delete(commentOpt.get());
			return true;
		}
		return false;
	}

	// 댓글 수정
	public boolean updateComment(CommentDTO dto, String loginId) {
		Comment c = commentRepository.findById(dto.getComment_seq()).orElse(null);
		if (c == null || !loginId.equals(c.getUser_id()))
			return false;

		c.setContent(dto.getContent());
		commentRepository.save(c);
		return true;
	}

}
