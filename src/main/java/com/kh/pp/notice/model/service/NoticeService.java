package com.kh.pp.notice.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.pp.notice.model.dao.NoticeMapper;
import com.kh.pp.notice.model.dto.NoticeDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

	private final NoticeMapper noticeMapper;

	public List<NoticeDto> findAll(){
		return noticeMapper.findAll();
	}
	
	@Transactional
	public NoticeDto findById(int noticeNo) {
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

	public void search(String noticeTitle) {
		noticeMapper.search(noticeTitle);
	
	}

}
