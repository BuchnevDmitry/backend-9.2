package com.edu.tool.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ToolImage {
    private MultipartFile file;
}
