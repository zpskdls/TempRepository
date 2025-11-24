package com.example.KGCinema.entity;

import java.util.Date;

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
@Table(name = "KGCBoard")
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seq;
	private String board_subject;
	@Lob
	private String board_content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date logtime;
	private int hit;
	private String user_id;
	private String author_name;

	public Board(String board_subject, String board_content, Date logtime, int hit, String userId, String author_name) {
        this.board_subject = board_subject;
        this.board_content = board_content;
        this.logtime = logtime;
        this.hit = hit;
        this.user_id = userId;
        this.author_name = author_name;
    }
}
