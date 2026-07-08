package com.kh.pp.notice.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pp.auth.model.vo.CustomUserDetails;
import com.kh.pp.board.model.dto.BoardImgDto;
import com.kh.pp.common.page.PageResponse;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.exception.FailUpdateException;
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
	public void saveNotice(NoticeDto notice) {
		long count= validateNoticeImages(notice.getImageFiles());
		
		Notice noticeEntity = Notice.builder()
				.memberNo(notice.getMemberNo())
				.noticeTitle(notice.getNoticeTitle())
				.noticeContent(notice.getNoticeContent())
				.build();
		 int result =  noticeMapper.saveNotice(noticeEntity);
		
		
		if(result < 1) {
			throw new FailSaveException("작성에 실패했습니다");
		}
		if(count > 0) {
			Long noticeNo = noticeMapper.getLastNoticeNoByMemberNo(notice.getNoticeNo());
			
			saveNoticeImages(noticeNo,notice.getImageFiles());
			
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

	public PageResponse<NoticeDto> findNoticeAll(int page){
		int size = 10;
		int offset = page * size;
	
		
		List<NoticeDto> notices = noticeMapper.findNoticeAll(offset,size);
		int totalElements = noticeMapper.getNoticeTotalElements();
		
		return new PageResponse<>(notices,totalElements,page,size);
	}
	
	private long validateNoticeImages(List<MultipartFile> imageFiles) {
		if(imageFiles == null) {
			return 0;
		}
		long count = imageFiles.stream()
								.filter(file -> !file.isEmpty())
								.count();
		
		if(count > 5) {
			throw new FailSaveException("이미지는 최대 5장까지 업로드 할수 있습니다");
		}
		
		return count;
		
	}
	
	@Transactional
	public NoticeDto NoticeDetail(Long noticeNo) {
		NoticeDto notice = noticeMapper.NoticeDetail(noticeNo);
		if(notice == null) {
			throw new FailSaveException("해당 공지사항이 존재하지 않습니다.");
		}
		
		List<NoticeImgDto> images = noticeImgMapper.findNoticeImgByNoticeNo(noticeNo);
		notice.setNoticeImages(images);
		increaseNoticeCount(noticeNo);
		
		return notice;
	}
	

	public void editNotice(NoticeDto notice,Long memberNoFromToken, Long noticeNo) {
		long count =validateNoticeImages(notice.getImageFiles());

		Notice noticeEntity = Notice.builder()
				.noticeTitle(notice.getNoticeTitle())
				.noticeContent(notice.getNoticeContent())
				.build();
		
		int result = noticeMapper.editNotice(noticeEntity);
		
		if(result < 1) {
			throw new FailUpdateException("수정에 실패했습니다");
		}
		if(count > 0) {
			noticeImgMapper.deleteNoticeImgByNoticeNo(noticeNo);
			
			saveNoticeImages(noticeNo,notice.getImageFiles());
		}
		
	}
	
	private void increaseNoticeCount(Long noticeNo) {
		noticeMapper.increaseNoticeCount(noticeNo);
	}
	
	public void deleteNotice(CustomUserDetails userDetails, Long noticeNo) {
		noticeMapper.deleteNotice(noticeNo);
	}

	public List<NoticeDto> searchNotice(String keyword,int page) {
		int size = 10;
		int offset = page * size;
		
		if(keyword == null || keyword.trim().isEmpty()) {
			return noticeMapper.findNoticeAll(offset,size);
		}
		
		return noticeMapper.searchNotice(keyword,offset,size);
	
	}

	private void saveNoticeImages(Long noticeNo, List<MultipartFile> imageFiles) {
		if (imageFiles == null || imageFiles.isEmpty()) {
			return;
		}
	    int order = 1;

        for (MultipartFile file : imageFiles) {
            if (!file.isEmpty()) {
                try {
                    String saveName = fileService.store(file, "notice");

                    NoticeImgDto imgDto = new NoticeImgDto();
                    imgDto.setNoticeNo(noticeNo);
                    imgDto.setOriginalName(file.getOriginalFilename());
                    imgDto.setSaveName(saveName);
                    imgDto.setImgPath("/uploads/notice/");
                    imgDto.setImgOrder(order++);

                    int imgResult = noticeImgMapper.insertNoticeImg(imgDto);
	                    
                    if (imgResult < 1) {
                    	throw new FailSaveException("이미지 저장에 실패했습니다.");
                    }
                } catch (Exception e) {
                    log.error("이미지 저장 실패", e);
                    throw new FailSaveException("이미지 저장 중 오류가 발생했습니다.");
                }
            }
        }
	}

}
