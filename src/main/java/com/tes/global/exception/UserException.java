package com.tes.global.exception;

public class UserException extends RuntimeException {

    /**
	 * 수정 년도+월+일+시+분+횟수(001)
	 */
	private static final long serialVersionUID = 202505251558001L;

    /**
     * 사용자 정의 예외 생성자
     * 
     * @param message 예외 메시지
     */
    public UserException(String message) {
        super(message);
    }
}