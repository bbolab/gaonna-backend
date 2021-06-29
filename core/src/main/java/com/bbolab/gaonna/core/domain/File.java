package com.bbolab.gaonna.core.domain;

import com.bbolab.gaonna.core.exception.FileServiceException;
import lombok.*;
import org.apache.commons.io.FileUtils;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;
    private String fileLocation;

    public byte[] readAsBytes() {
        try {
            return FileUtils.readFileToByteArray(new java.io.File(fileLocation));
        }
        catch (IOException e) {
            throw new FileServiceException("failed to read bytes from file: {}", e);
        }
    }

    public String readAsString() {
        try {
            return FileUtils.readFileToString(new java.io.File(fileLocation), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            throw new FileServiceException("failed to read string from file: {}", e);
        }
    }
}
