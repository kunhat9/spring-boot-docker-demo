package com.demo.controller;

import com.demo.model.ResponeObject;
import com.demo.service.ImageStorageSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/api/v1/fileUpload")
public class FileUploadController {
    // this controller upload file image/ from client
    // Inject storage Service here
    @Autowired
    private ImageStorageSerivce storageSerivce;
    @PostMapping("")
    public ResponseEntity<ResponeObject> uploadFile(@RequestParam("file")MultipartFile file){
        try{
            String genarateFileName = storageSerivce.storageFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponeObject("ok","upload file success !",genarateFileName)
            );

        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponeObject("err","upload failed !","")
            );
        }
    }
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readFileContent(@PathVariable String fileName){
        try{
            byte[] bytes = storageSerivce.readFileContent(fileName);
            return  ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);

        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
    @GetMapping("")
    public ResponseEntity<ResponeObject>loadAll(){
        try {
            List<String> list = storageSerivce.loadAll()
                    .map(path -> {
                        // convert file nam to url
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                        "readFileContent",path.getFileName().toString()).build().toUri().toString();
                        return urlPath;
                    }).collect(Collectors.toList());
            return ResponseEntity.ok(new ResponeObject("ok","List files successfully",list));
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
