//package com.project.common.service;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.project.common.exception.MyFileNotFoundException;
//
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//@Service
//public class FileService {
//
//
//    public String fileUpload(MultipartFile file) {
//    	String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//    	
//    	Path serverPath = Paths.get(
//                "/uploads" +
//                        File.separator +
//                        fileName);
//
//        try {
//            Files.copy(file.getInputStream(), serverPath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//        }
//        return fileName;
//    }
//    
//    public Resource loadFileAsResource(String fileName) {
//        try {
//            Path filePath = Paths.get(
//                    "/uploads" +
//                            File.separator +
//                            fileName);
//            Resource resource = new UrlResource(filePath.toUri());
//            if(resource.exists()) {
//                return resource;
//            } else {
//                throw new MyFileNotFoundException("파일 찾을 수 없음" );
//            }
//        } catch (MalformedURLException ex) {
//            throw new MyFileNotFoundException("파일 찾을 수 없음" , ex);
//        }
//    }
//}