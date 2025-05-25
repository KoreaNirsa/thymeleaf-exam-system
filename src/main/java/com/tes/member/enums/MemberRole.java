package com.tes.member.enums;

/**
 * 시스템 사용자 역할(Role)을 정의하는 Enum입니다.
 * <p>
 * 역할은 권한에 따라 시스템 내 접근 가능한 기능을 구분하는 데 사용되며,
 * 학생(STUDENT)과 강사(INSTRUCTOR) 두 가지로 나뉩니다.
 * </p>
 * 
 * <ul>
 *   <li>{@code STUDENT} - 평가를 응시하고, 답안을 제출할 수 있는 일반 학생</li>
 *   <li>{@code INSTRUCTOR} - 학생을 관리하고 평가를 출제 및 채점할 수 있는 강사</li>
 * </ul>
 * 
 * 이 열거형은 {@link com.tes.member.domain.entity.Member} 엔티티의 {@code role} 필드와 매핑되어
 * 데이터베이스에 문자열(EnumType.STRING) 형태로 저장됩니다.
 * 
 * @author 
 * @since 1.0
 */
public enum MemberRole {
    /** 일반 학생 */
    STUDENT,

    /** 강사 */
    INSTRUCTOR
}