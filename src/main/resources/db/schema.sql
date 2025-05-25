-- 과목 정보를 저장하는 테이블
CREATE TABLE subject (
  subject_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '과목 고유 ID',
  name VARCHAR(255) COMMENT '과목명',
  first_exam_date DATE COMMENT '1차 평가 예정일',
  second_exam_date DATE COMMENT '2차 평가 예정일'
);

-- 사용자 정보 (학생/강사) 공통 관리 테이블
CREATE TABLE member (
  member_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 고유 ID',
  name VARCHAR(255) NOT NULL COMMENT '이름',
  generation VARCHAR(50) COMMENT '기수 (강사의 경우 NULL 허용)',
  phone VARCHAR(20) COMMENT '연락처',
  password VARCHAR(255) NOT NULL COMMENT '비밀번호 (암호화 저장)',
  role VARCHAR(20) NOT NULL COMMENT '역할 (STUDENT or INSTRUCTOR)'
);

-- 과목별 평가 진행 상태를 저장하는 테이블 (1차/2차 구분 포함)
CREATE TABLE evaluation_status (
  evaluation_status_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '평가 상태 고유 ID',
  subject_id BIGINT COMMENT '평가가 진행되는 과목 ID',
  status VARCHAR(50) COMMENT '평가 상태 (예: 시작 대기, 평가 시작, 채점 진행중, 점수 확인)',
  round INT COMMENT '평가 차수 (1: 1차, 2: 2차)',
  FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);

-- 평가 문제 정보를 저장하는 테이블
CREATE TABLE exam_question (
  exam_question_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '문제 고유 ID',
  subject_id BIGINT COMMENT '문제가 속한 과목 ID',
  round INT COMMENT '차수 (1: 1차, 2: 2차)',
  question_text TEXT COMMENT '문제 설명 및 텍스트',
  FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);

-- 학생이 제출한 코드 답안 및 점수/피드백을 저장하는 테이블
CREATE TABLE student_exam_submission (
  student_exam_submission_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제출 고유 ID',
  member_id BIGINT COMMENT '답안을 제출한 학생 ID',
  subject_id BIGINT COMMENT '과목 ID',
  round INT COMMENT '평가 차수 (1: 1차, 2: 2차)',
  code_answer TEXT COMMENT '학생이 제출한 코드 답안',
  score INT COMMENT '자동 채점 또는 수동 채점 점수 (0~100)',
  feedback VARCHAR(1000) COMMENT '강사가 입력한 피드백',
  FOREIGN KEY (member_id) REFERENCES member(member_id),
  FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);