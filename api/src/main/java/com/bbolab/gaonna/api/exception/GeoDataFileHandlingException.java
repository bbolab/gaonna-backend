package com.bbolab.gaonna.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class GeoDataFileHandlingException extends RuntimeException{
    private String detail;
}
