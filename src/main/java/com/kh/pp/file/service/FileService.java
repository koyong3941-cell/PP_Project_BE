package com.kh.pp.file.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
	
	// 파일을 uploads폴더에 저장하고 저장된 파일명을 반환 subDirectort는 경로 지정을 위해 해당 게시글 명(ex. board, plant 등)을 입력
	public String store(MultipartFile file, String subDirectory) {
        try {
            String originalName = file.getOriginalFilename();
            String extension = "";

            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }

            String saveName = UUID.randomUUID().toString() + extension;

            // uploads/{subDirectory}/ 경로 생성
            Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", subDirectory)
                    .toAbsolutePath().normalize();

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path targetPath = uploadPath.resolve(saveName);
            file.transferTo(targetPath.toFile());

            return saveName;

        } catch (IOException e) {
            log.error("파일 저장 실패", e);
            throw new RuntimeException("파일 저장에 실패했습니다.");
        }
    }
}
