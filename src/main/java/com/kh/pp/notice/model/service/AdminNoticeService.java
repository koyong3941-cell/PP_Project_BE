package com.kh.pp.notice.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.pp.common.page.PageResponse;
import com.kh.pp.exception.FailSaveException;
import com.kh.pp.exception.FailUpdateException;
import com.kh.pp.exception.FailUserRequestException;
import com.kh.pp.exception.NoticeNotFoundException;
import com.kh.pp.file.service.FileService;
import com.kh.pp.notice.model.dao.AdminNoticeMapper;
import com.kh.pp.notice.model.dao.NoticeImgMapper;
import com.kh.pp.notice.model.dto.AdminNoticeDto;
import com.kh.pp.notice.model.dto.NoticeImgDto;
import com.kh.pp.notice.model.vo.Notice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminNoticeService {
	private final AdminNoticeMapper adminNoticeMapper;
	private final NoticeImgMapper noticeImgMapper;
	private final FileService fileService;
	
	private void increaseNoticeCount(Long noticeNo) {
		adminNoticeMapper.increaseNoticeCount(noticeNo);
	}

	public PageResponse<AdminNoticeDto> findNoticeAll(int page) {
		int size = 10;
		int offset = page * size;
		
		int totalElements = adminNoticeMapper.getNoticeTotalElements();
				
		List<AdminNoticeDto> notices = adminNoticeMapper.findNoticeAll(offset, size);
		
		if(notices.isEmpty()) {
			throw new FailUserRequestException("사용자 요청에 실패하였습니다.");
		}
		
		return new PageResponse<>(notices, totalElements, page, size);
	}

	public AdminNoticeDto noticeDetail(Long noticeNo) {
		AdminNoticeDto notice = adminNoticeMapper.noticeDetail(noticeNo);
		if (notice == null) {
			throw new FailSaveException("저장에 실패했습니다.");
		}
		
		List<NoticeImgDto> images = noticeImgMapper.findNoticeImgByNoticeNo(noticeNo);
		notice.setNoticeImages(images);
		increaseNoticeCount(noticeNo);
		
		return notice;
	}

	public List<AdminNoticeDto> noticeSearch(String keyword, int page) {
		int size = 10;
		int offset = page * size;
		
		if(keyword == null || keyword.trim().isEmpty()) {
			return adminNoticeMapper.findNoticeAll(offset,size);
		}
		
		return adminNoticeMapper.searchNotice(keyword,offset,size);
	}
	
	@Transactional
	public void saveNotice(AdminNoticeDto notice) {
		long count= validateNoticeImages(notice.getImageFiles());
		
		Notice noticeEntity = Notice.builder()
				.memberNo(notice.getMemberNo())
				.noticeTitle(notice.getNoticeTitle())
				.noticeContent(notice.getNoticeContent())
				.build();
		 int result =  adminNoticeMapper.saveNotice(noticeEntity);
		
		
		if(result < 1) {
			throw new FailSaveException("작성에 실패했습니다");
		}
		if(count > 0) {
			Long noticeNo = adminNoticeMapper.getLastNoticeNoByMemberNo(notice.getMemberNo());
			
			saveNoticeImages(noticeNo,notice.getImageFiles());
		}
	
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
                    throw new FailSaveException("이미지 저장 중 오류가 발생했습니다.");
                }
            }
        }
	}

	@Transactional
	public void editNotice(AdminNoticeDto notice, Long memberNoFromToken, Long noticeNo) {
		long count = validateNoticeImages(notice.getImageFiles());

		Notice noticeEntity = Notice.builder()
				.noticeNo(notice.getNoticeNo())
				.memberNo(notice.getMemberNo())
				.noticeTitle(notice.getNoticeTitle())
				.noticeContent(notice.getNoticeContent())
				.build();
		
		int result = adminNoticeMapper.editNotices(noticeEntity);
		
		if (result < 1) {
			throw new FailUpdateException("수정에 실패했습니다.");
		}
		// 기존 이미지 삭제처리 (DEL_YN을 'Y'로)
		noticeImgMapper.deleteNoticeImgByNoticeNo(notice.getNoticeNo());
		
		if(count > 0) {
			saveNoticeImages(notice.getNoticeNo(), notice.getImageFiles());
		}
	}

	@Transactional
	public int deleteNotice(List<Long> noticeNos) {			
		if (noticeNos == null || noticeNos.isEmpty()) {
			throw new NoticeNotFoundException("삭제할 공지사항 번호를 선택해주세요.");
		}
		
		int result = adminNoticeMapper.deleteNotices(noticeNos);
		
		if (result == 0) {
			throw new FailUpdateException("삭제에 실패하였습니다.");
		}
		return result;
	}

}
