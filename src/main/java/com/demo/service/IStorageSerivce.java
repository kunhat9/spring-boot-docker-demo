package com.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageSerivce {
    public String storageFile(MultipartFile file);
    public Stream<Path> loadAll();
    public byte[] readFileContent(String fileName);
    public  void deleteAllFiles();
}
