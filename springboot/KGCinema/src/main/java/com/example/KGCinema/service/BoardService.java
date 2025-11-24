package com.example.KGCinema.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.KGCinema.dto.BoardDTO;
import com.example.KGCinema.entity.Board;
import com.example.KGCinema.repository.BoardRepository;

@Service
public class BoardService {
	@Autowired
	BoardRepository boardRepository;
	
	// 총글수
		public int getCount() {
			return (int) boardRepository.count();
		}

		// 목록
		public List<Board> boardList(int startNum, int endNum) {
			return boardRepository.findByStartnumAndEndnum(startNum, endNum);
		}

		// 글 저장
		public Board boardWrite(BoardDTO dto) {
			return boardRepository.save(dto.toEntity());
		}

		// 조회수 증가하기
		public Board boardUpdateHit(int seq) {
			// 1. seq를 조회하여 데이터 가져오기
			Board board = boardRepository.findById(seq).orElse(null);
			if (board != null) {
				int hit = board.getHit();
				board.setHit(hit + 1);
				board = boardRepository.save(board);
			}
			return board;
		}

		// 상세보기
		public Board boardView(int seq) {
			return boardRepository.findById(seq).orElse(null);
		}

		// 글 삭제
		public boolean boardDelete(int seq, String userId) {
		    // 1. 삭제할 대상 가져오기
		    Board target = boardRepository.findById(seq).orElse(null);
		    
		    if (target == null) {
		        return false; // 게시글이 없으면 삭제 실패
		    }
		    
		    // 2. 작성자 확인
		    if (!target.getUser_id().equals(userId)) {
		        return false; // 작성자가 아니면 삭제 실패
		    }

		    // 3. 대상 entity 삭제
		    boardRepository.delete(target);

		    // 4. 삭제 확인
		    return !boardRepository.existsById(seq);
		}


		// 수정하기
		public boolean updateBoard(BoardDTO dto, String loginUserId) {

	        Board board = boardRepository.findById(dto.getSeq()).orElse(null);

	        if (board == null) return false;

	        // 작성자 본인 확인
	        if (!loginUserId.equals(board.getUser_id())) return false;

	        board.setBoard_subject(dto.getBoard_subject());
	        board.setBoard_content(dto.getBoard_content());
	        board.setLogtime(new Date());  // 수정 날짜 갱신

	        boardRepository.save(board);
	        return true;
	    }
}
