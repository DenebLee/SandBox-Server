package kr.nanoit.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder

/**
 * Builder pattern이란 복합 객체의 생성 과정과 표현 방법을 분리하여 생성 절차에서 서로 다른 표현 방법을 분리하여 동일한 생성 절차에서
 * 서로 다른 표현 결과를 만들 수 있게 하는 패턴
 * 사용하는 이유:
 *   필수 및 선택인자가 많아질수록 생성자 방식보다 가독성이 좋다
 *
 */

public class Verification {

    private String id;

    private String password;

    private String encryptKey;

}
