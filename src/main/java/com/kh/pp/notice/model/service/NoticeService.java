package com.kh.pp.notice.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.notice.model.dao.NoticeMapper;
import com.kh.pp.notice.model.dto.NoticeDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

	private final NoticeMapper noticeMapper;

	public List<NoticeDto> findNoticeAll(int page){
		int offset = page * 10;
		int limit = 10;
		
		return noticeMapper.findNoticeAll(offset,limit);
	}
	
	@Transactional
	public NoticeDto findById(Long noticeNo) {
		noticeMapper.updateCount(noticeNo);
		return noticeMapper.findById(noticeNo);
	}
	
	@Transactional
	public void save(NoticeDto notice) {
		noticeMapper.save(notice);
	}
	
	@Transactional
	public void update(NoticeDto notice) {
		noticeMapper.update(notice);
	}
	
	@Transactional
	public void delete(int noticeNo) {
		noticeMapper.delete(noticeNo);
	}

	public List<NoticeDto> search(String keyword,int page) {
		int offset = page * 10;
		int limit = 10;
		
		return noticeMapper.search(keyword,offset,limit);
	
	}

}
