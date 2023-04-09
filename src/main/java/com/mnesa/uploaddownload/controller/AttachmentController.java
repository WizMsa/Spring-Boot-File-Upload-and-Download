package com.mnesa.uploaddownload.controller;

import com.mnesa.uploaddownload.entity.Attachment;
import com.mnesa.uploaddownload.model.ResponseData;
import com.mnesa.uploaddownload.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;
    @PostMapping("/uploads")
    public ResponseEntity<ResponseData> upload(@RequestParam("file")MultipartFile file) throws IOException {
        return new ResponseEntity<>(attachmentService.saveAttachment(file), HttpStatus.OK) ;
    }
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable String fileId) throws Exception {
       Attachment attachment = attachmentService.getAttachment(fileId);

       return ResponseEntity.ok()
               .contentType(MediaType.parseMediaType(attachment.getFileType()))
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\""+attachment.getFileName()+"\"")
               .body(new ByteArrayResource(attachment.getData()));
    }
}
