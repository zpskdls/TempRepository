package com.example.KGCinema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.KGCinema.entity.Board;

import jakarta.transaction.Transactional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	// 목록 조회
	@Query(value = "select * from " + "(select rownum rn, tt.* from "
			+ "(select * from KGCBoard order by seq desc) tt) "
			+ "where rn>=:startNum and rn<=:endNum", nativeQuery = true)
	List<Board> findByStartnumAndEndnum(@Param("startNum") int startNum, @Param("endNum") int endNum);
	
	@Modifying
    @Transactional
    @Query("UPDATE Board b SET b.hit = b.hit + 1 WHERE b.seq = :seq")
    void updateHit(@Param("seq") int seq);
}
