package com.kh.pp.board.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.pp.board.model.dto.BoardImgDto;

@Mapper
public interface BoardImgMapper {
	int insertBoardImg(BoardImgDto boardImgDto);

	List<BoardImgDto> findByBoardNo(Long boardNo);
	
	void deleteByBoardNo(Long boardNo);
}
