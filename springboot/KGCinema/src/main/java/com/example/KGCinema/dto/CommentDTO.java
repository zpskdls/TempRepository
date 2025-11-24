package com.example.KGCinema.dto;

import java.util.Date;

import com.example.KGCinema.entity.Board;
import com.example.KGCinema.entity.Comment;

import lombok.Data;

@Data
public class CommentDTO {
	private int comment_seq;
	private int board_seq;
	private String content;
	private Date logtime;
	private String user_id;
	private String author_name;
	
	public Comment toEntity() {
		return new Comment(board_seq, content, new Date(), user_id, author_name);
	}
}
