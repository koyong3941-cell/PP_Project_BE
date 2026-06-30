package com.kh.pp.board.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.pp.board.model.dto.BoardImgDto;

@Mapper
public interface BoardImgMapper {
	int insertBoardImg(BoardImgDto boardImgDto);
}
