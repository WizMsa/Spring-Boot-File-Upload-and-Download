package com.mnesa.uploaddownload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
}
