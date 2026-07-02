package com.kh.pp.notice.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.file.service.FileService;
import com.kh.pp.notice.model.dao.NoticeImgMapper;
import com.kh.pp.notice.model.dao.NoticeMapper;
import com.kh.pp.notice.model.dto.NoticeDto;
import com.kh.pp.notice.model.dto.NoticeImgDto;
import com.kh.pp.notice.model.vo.Notice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

	private final NoticeMapper noticeMapper;
	private final NoticeImgMapper noticeImgMapper;
	private final FileService fileService;
	
	
	@Transactional
	public void save(NoticeDto notice, CustomUserDetails userDetails) {
		validateNotice(notice);
		
		Notice noticeEntity = Notice.builder()
				.memberNo(notice.getMemberNo())
				.noticeTitle(notice.getNoticeTitle())
				.noticeContent(notice.getNoticeContent())
				.build();
		noticeMapper.save(noticeEntity);
		
		Long noticeNo = noticeMapper.getLastBoardNoByMemberNo(notice.getMemberNo());
		
		if (notice.getImageFiles() != null) {
			long validImageCount = notice.getImageFiles().stream()
					.filter(file -> !file.isEmpty())
					.count();
			if(validImageCount > 5) {
				throw new FailSaveException("이미지는 최대 5장까지 업로드 할수 있습니다");
			}
		}
		
		if(notice.getImageFiles() != null && !notice.getImageFiles().isEmpty()) {
			int order = 1;
			
			for (MultipartFile file : notice.getImageFiles()) {
				if(!file.isEmpty()) {
					try {
						String save = fileService.store(file,"notice");
					
						NoticeImgDto imgDto = new NoticeImgDto();
						
					}catch(Exception e) {
						log.error("이미지 저장 실패",e);
						throw new FailSaveException("이미지 저장중 오류가 발생했습니다");
					}
				}
			}
		}
	}
	
	private void validateNotice(NoticeDto notice) {
		if(notice.getNoticeTitle() == null || notice.getNoticeTitle().isEmpty()) {
			throw new FailSaveException("제목은 필수입니다");
		}
		if(notice.getNoticeContent() == null || notice.getNoticeContent().isEmpty()) {
			throw new FailSaveException("내용은 필수입니다");
		}
		
	}

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
	

	public void update(NoticeDto notice,MultipartFile file,CustomUserDetails userDetails, Long noticeNo) {
		

		
		noticeMapper.update(notice,noticeNo,file);
	}
	
	
	public void delete(CustomUserDetails userDetails, Long noticeNo) {
		noticeMapper.delete(noticeNo);
	}

	public List<NoticeDto> search(String keyword,int page) {
		int offset = page;
		int limit = 1;
		
		return noticeMapper.search(keyword,offset,limit);
	
	}



}
