package com.bbolab.gaonna.api.v1.controller.validator;

import com.bbolab.gaonna.api.exception.validator.BboxConstraintException;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BboxConstraintValidator implements ConstraintValidator<BboxConstraint, String> {
    // TODO : Search를 허용할 최대 bbox 크기를 정의해야 함
    public static Double MaximumBboxWidth = 0.02;
    public static Double MaximumBboxHeight = 0.02;

    public static final String wrongFormatExceptionMsg = "Wrong bbox coordinates format";
    public static final String wrongCoordinatesExceptionMsg = "Wrong bbox coordinates. You should pass [bottom left, top right] coordinates";
    public static final String wrongBboxRangeExceptionMsg = "Wrong bbox coordinates. Too large bbox coordinates";

    @Override
    public boolean isValid(String bbox, ConstraintValidatorContext context) {
        Double[][] parsed = parseBboxStringToDoubleArray(bbox);
        validateBboxCooridinates(parsed);
        return true;
    }

    public static Double[][] parseBboxStringToDoubleArray(String bbox) throws BboxConstraintException {
        JSONArray rows = (JSONArray) JSONValue.parse(bbox);
        if(rows == null || rows.size() != 2 || ((JSONArray)rows.get(0)).size() != 2 || ((JSONArray)rows.get(1)).size() != 2 ) {
            throw new BboxConstraintException(wrongFormatExceptionMsg);
        }
        Double[][] result = new Double[rows.size()][2];
        try {
            for (int row = 0; row < 2; row++) {
                Double longitude = (Double) ((JSONArray) rows.get(row)).get(0);
                Double latitude = (Double) ((JSONArray) rows.get(row)).get(1);
                result[row][0] = longitude;
                result[row][1] = latitude;
            }
        } catch (ClassCastException e) {
            throw new BboxConstraintException(wrongFormatExceptionMsg);
        }
        return result;
    }

    public static void validateBboxCooridinates(Double[][] bbox) {
        // 입력받은 좌표가 좌하단, 우상단 좌표가 아닐 경우 + 입력받은 bbox 크기가 허용한 크기보다 클 경우
        if(bbox[0][0] >= bbox[1][0] && bbox[0][1] >= bbox[1][1]) {
            throw new BboxConstraintException(wrongCoordinatesExceptionMsg);

        }
        if((bbox[1][0] - bbox[0][0] > MaximumBboxWidth) || (bbox[1][1] - bbox[0][1] > MaximumBboxHeight)){
            throw new BboxConstraintException(wrongBboxRangeExceptionMsg);
        }
    }
}
