package com.example.KGCinema.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.KGCinema.entity.Board;

import lombok.Data;

@Data
public class BoardDTO {
	private int seq;
	private String board_subject;
	private String board_content;
	private Date logtime;
	private int hit;
	private String user_id;
	private String author_name;

	// 조회 시 포맷된 문자열 반환 (yyyy.MM.dd HH:mm)
    public String getFormattedLogtime() {
        if (logtime == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return sdf.format(logtime);
    }
    
    public Board toEntity() {
        return new Board(board_subject, board_content, new Date(), hit, user_id, author_name);
    }
}
