package com.kh.pp.board.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.board.model.dao.AdminBoardMapper;
import com.kh.pp.board.model.dto.BoardDto;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.exception.FailDeleteException;
import com.kh.pp.exception.FailUpdateException;
import com.kh.pp.exception.InvalidBoardNoException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly= true)
@RequiredArgsConstructor
public class AdminBoardService {

	private final AdminBoardMapper adminBoardMapper;

	// Read
	public PageResponse<BoardDto> findBoardAll(int page, int size) {
		int offset = page * size;
		
		int totalElements = adminBoardMapper.getBoardTotalElements();
		if (totalElements == 0) {
			return PageResponse.empty(page, size);
		}
		
		List<BoardDto> boards = adminBoardMapper.findBoardAll(offset, size);
		
		return new PageResponse<>(boards, totalElements, page, size);
	}

	public PageResponse<BoardDto> findBoardByKeyword(int page, int size, String keyword, String target) {
		if (keyword == null || keyword.trim().isEmpty()) {
			return findBoardAll(page, size);
		}
		
		int offset = page * size;
		
		List<String> keywordList = new ArrayList<>();
		String[] words = keyword.trim().split("\\s+");
		for (String word : words) {
			if (!word.isEmpty()) {
				keywordList.add(word);
			}
		}
		
		if (target == null || target.trim().isEmpty()) {
			target = "all";
		}
		
		int totalElements = adminBoardMapper.getBoardTotalElementsByKeyword(keywordList, target);
		
		if (totalElements == 0) {
			return PageResponse.empty(page, size);
		}
		
		List<BoardDto> boards = adminBoardMapper.findBoardByKeyword(offset, size, keywordList, target);
		
		return new PageResponse<>(boards, totalElements, page, size);
	}
	
	// Update
	@Transactional
	public int restoreBoards(List<Long> boardNos) {
		if (boardNos == null || boardNos.isEmpty()) {
	        throw new InvalidBoardNoException("복구할 식물 번호를 선택해주세요.");
	    }
		
		int result = adminBoardMapper.restoreBoards(boardNos);
		
		if (result == 0) {
			throw new FailUpdateException("복구에 실패하였습니다.");
		}
		return result;
	}

	// Delete
	@Transactional
	public int deleteBoards(List<Long> boardNos) {
		if (boardNos == null || boardNos.isEmpty()) {
	        throw new InvalidBoardNoException("삭제할 식물 번호를 선택해주세요.");
	    }
		
		int result = adminBoardMapper.deleteBoards(boardNos);
		
		if (result == 0) {
			throw new FailDeleteException("삭제에 실패하였습니다.");
		}
		return result;
	}
}
