package com.bbolab.gaonna.core.exception;

import java.io.IOException;

public class FileServiceException extends RuntimeException {
    public FileServiceException(String s, IOException e) {
        super(s, e);
    }
}
