package com.kh.pp.notice.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.exception.FailSaveException;
import com.kh.pp.notice.model.dao.NoticeMapper;
import com.kh.pp.notice.model.dto.NoticeDto;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly= true)
@RequiredArgsConstructor
public class NoticeService {
	private final NoticeMapper noticeMapper;
	
	public List<NoticeDto> findNoticeAll(int page) {
		int offset = page * 10;
		int limit = 10;
		
		return noticeMapper.findNoticeAll(offset, limit);
	}
	
	
	public NoticeDto noticeDetail(Long noticeNo) {
		increaseCount(noticeNo);
		NoticeDto notice = getNoticeNoOrThrow(noticeNo);
		
		return notice;
	}
	
	private void increaseCount(Long noticeNo) {
		noticeMapper.increaseCount(noticeNo);
	}
	
	// ------ 접근 실패 시  ------	
	private NoticeDto getNoticeNoOrThrow(Long noticeNo) {
		NoticeDto noticeDetail = noticeMapper.noticeDetail(noticeNo);
		if (noticeDetail == null) {
			throw new FailSaveException("유효하지 않은 접근입니다.");
		}
		return noticeDetail; 
	}



	
}
