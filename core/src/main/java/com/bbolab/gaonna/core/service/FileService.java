package com.bbolab.gaonna.core.service;

import com.bbolab.gaonna.core.domain.File;
import com.bbolab.gaonna.core.exception.FileServiceException;
import com.bbolab.gaonna.core.exception.NoSuchEntityException;
import com.bbolab.gaonna.core.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    @Transactional
    public File create(byte[] bytes) {
        String filename = UUID.randomUUID().toString();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        }
        catch (IOException e) {
            throw new FileServiceException("failed to create file: {}", e);
        }
        return fileRepository.save(File.builder().fileLocation(filename).build());
    }

    public File get(UUID fileId) {
        return fileRepository.findById(fileId).orElseThrow(NoSuchEntityException::new);
    }

    public void delete(UUID fileId) {
        fileRepository.deleteById(fileId);
    }
}
