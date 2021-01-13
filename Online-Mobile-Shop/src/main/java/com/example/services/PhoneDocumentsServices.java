package com.example.services;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.exception.FileStorageException;
import com.example.model.PhoneDocuments;
import com.example.repository.PhoneDocumentsRepository;
@Service
public class PhoneDocumentsServices {
	@Autowired
    private PhoneDocumentsRepository phoneDocumentsRepository;

 public PhoneDocuments storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            PhoneDocuments dbFile = new PhoneDocuments(fileName, file.getContentType(), file.getBytes());

            return phoneDocumentsRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public PhoneDocuments getFile(Integer fileId) throws FileNotFoundException {
        return phoneDocumentsRepository.findById(fileId).orElseThrow(()-> new FileNotFoundException("File not found with id " + fileId));
    }

}
