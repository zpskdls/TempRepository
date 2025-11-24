package com.example.KGCinema.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "board_comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_seq")
	private int commentSeq;
	@Column(name = "board_seq")
	private int boardSeq;
	@Lob
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date logtime;
	private String user_id;
	private String author_name;

	public Comment(int board_seq, String content, Date logtime, String userId, String author_name) {
		this.boardSeq = board_seq;
		this.content = content;
		this.logtime = logtime;
		this.user_id = userId;
		this.author_name = author_name;
	}
}
