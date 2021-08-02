package com.bbolab.gaonna.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeoDataFileHandlingException extends RuntimeException{
    private String detail;
}
