package com.bbolab.gaonna.api.validator;

import com.bbolab.gaonna.api.exception.validator.BboxConstraintException;
import com.bbolab.gaonna.api.v1.controller.validator.BboxConstraintValidator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

public class BboxConstraintValidatorTest {

    @DisplayName("정상 좌표계 입력")
    @Test
    public void passConstraint() throws IOException {
        // given
        String str = "[[127.0403165,37.2746168],[127.04645333,37.2796836]]";

        // when & then
        Assertions.assertDoesNotThrow(() -> {
            Double[][] bbox = BboxConstraintValidator.parseBboxStringToDoubleArray(str);
            BboxConstraintValidator.validateBboxCooridinates(bbox);
        });
    }

    @DisplayName("비정상 좌표계 입력 - 개수 ")
    @Test
    public void failConstraint1() throws IOException {
        // given
        String str = "[[127.0403165,37.2746168,123.11],[127.04645333,37.2796836,223]]";
        // when & then
        assertBboxConstraintExceptionThrown(str);
    }

    @DisplayName("비정상 좌표계 입력 - double 형 아닌 경우")
    @Test
    public void failConstraint2() {
        // given
        String str = "[[127.0403165,37.27.46168],[127.04645333,37.279.6836]]";
        assertBboxConstraintExceptionThrown(str);
    }

    @DisplayName("비정상 좌표계 입력 - double 형 아닌 경우 ")
    @Test
    public void failConstraint3() {
        // given
        String str = "[[127.0403165,37.2746168],[127.04645333,\"37.2796836\"]]";
        assertBboxConstraintExceptionThrown(str);
    }

    @DisplayName("비정상 좌표계 입력 - 대괄호 짝이 안맞을 경우 ")
    @Test
    public void failConstraint4() {
        // given
        String str = "[[127.0403165,37.2746168],[127.04645333,37.2796836]";
        assertBboxConstraintExceptionThrown(str);
    }

    @DisplayName("비정상 좌표계 입력 - 좌표 개수가 하나 더 많을 경우")
    @Test
    public void failConstraint5() {
        // given
        String str = "[[127.0403165,37.2746168],[127.04645333,37.2796836],[127.04645333,37.2796836]]";
        assertBboxConstraintExceptionThrown(str);
    }

    void assertBboxConstraintExceptionThrown(String str) {
        Assertions.assertThrows(BboxConstraintException.class, () -> {
            Double[][] bbox = BboxConstraintValidator.parseBboxStringToDoubleArray(str);
            BboxConstraintValidator.validateBboxCooridinates(bbox);
        });
    }
}
