package com.advocacia.api.services;

import java.util.Optional;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.advocacia.api.domain.archive.Archive;

public interface IArchiveService {

    Archive findByPath(String path);
    Optional<Archive> findById(String id);
    Archive findById(Long id);
    Resource loadFileAsResource(String filePath);
    Archive sendDocument(MultipartFile archive, String archiveName);
    Archive saveModel(MultipartFile archive);
    void deleteById(String id);
    List<Archive> findAllModel();
    String readArchive(String name);

    boolean archiveExists(String name);
}