package com.demo.service;

import com.demo.database.Database;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Service
public class ImageStorageSerivce implements  IStorageSerivce{
    private final Path storageFolder = Paths.get("uploads");
    private static final Logger logger=Logger.getLogger(String.valueOf(Database.class));
    private boolean isImageFile(MultipartFile file){
        String fileExtension = FileNameUtils.getExtension(file.getOriginalFilename());
        return  Arrays.asList(new String[]{"png","jpg","jpeg","bmp"}).contains(fileExtension.trim().toLowerCase(Locale.ROOT));

    }
    @Override
    public java.lang.String storageFile(MultipartFile file) {
        try{
            logger.info("storage file");
            if (file.isEmpty()){
                throw new RuntimeException("Failed to storage empty file");
            }
            if (!isImageFile(file)){
                throw new RuntimeException("You can only upload image file");
            }
            //file must be <= 5mb
            float fileSizeMeagabytes = file.getSize()/1000000.0f;
            if (fileSizeMeagabytes > 5.0f){
                throw new RuntimeException("File must be 5mb");
            }
            //file must be rename
            String fileExtension = FileNameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-","");
            generatedFileName=generatedFileName+"."+fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())){
                throw new RuntimeException("Cannot store file outside currnet directory");
            }
            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream,destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;

        }catch (Exception e){
            throw  new RuntimeException("Failed to store empty file !");
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return  Files.walk(this.storageFolder,1)
                    .filter(path -> !path.equals(this.storageFolder))
                    .map(this.storageFolder::relativize);
        }catch (IOException e){
            throw new RuntimeException("Failed to load stored files",e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try{
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return  bytes;
            }else {
                throw  new RuntimeException("Could not read file :"+fileName);
            }
        }catch (Exception e){
            throw  new RuntimeException("Could not read file :"+fileName, e);
        }
    }

    @Override
    public void deleteAllFiles() {


    }
}
