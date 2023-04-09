package com.mnesa.uploaddownload.service;

import com.mnesa.uploaddownload.entity.Attachment;
import com.mnesa.uploaddownload.model.ResponseData;
import com.mnesa.uploaddownload.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    public ResponseData saveAttachment(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")){
                throw new Exception("File Name contains invalid Sequence");
            }
            Attachment attachment = attachmentRepository.save(new Attachment(fileName,file.getContentType(),file.getBytes()));

            String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/download/")
                    .path(attachment.getId())
                    .toUriString();
            return new ResponseData(attachment.getFileName(),downloadURL,attachment.getFileType(),file.getSize());
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload :" + e.getMessage());
        }
    }

    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository.findById(fileId).orElseThrow(
                ()-> new Exception("File Not Found")
        );
    }
}
