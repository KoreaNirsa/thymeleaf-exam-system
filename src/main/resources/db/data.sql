-- 1. 과목(subject)
INSERT INTO subject (name, first_exam_date, second_exam_date)
VALUES ('웹 프로그래밍 기초', '2025-06-10', '2025-07-05');

-- 2. 사용자(member) – 강사
INSERT INTO member (member_id, name, generation, phone, password, role)
VALUES (1, '김재섭', 0, '010-8888-9999', '$2a$10$u.6YeYJGWpRYg8Y.lcCrzOdz1U.D3oV1PFAn9BBzvfK/ikZWLSP9G', 'INSTRUCTOR');

-- 3. 사용자(member) – 학생
INSERT INTO member (name, generation, phone, password, role)
VALUES ('이은하', '15기', '010-2345-6789', '$2a$10$u.6YeYJGWpRYg8Y.lcCrzOdz1U.D3oV1PFAn9BBzvfK/ikZWLSP9G', 'STUDENT');

-- 4. 평가 상태(evaluation_status) – 과목 ID 1 기준
INSERT INTO evaluation_status (subject_id, status, round)
VALUES (1, 'STARTED', 1);

-- 5. 문제 정보(exam_question) – 과목 ID 1 기준
INSERT INTO exam_question (subject_id, round, question_text)
VALUES (1, 1, 'HTML로 기본 구조를 작성하고, CSS로 버튼을 꾸미는 코드를 작성하세요.');

-- 6. 답안 제출(student_exam_submission) – 학생 ID 2, 과목 ID 1 기준
INSERT INTO student_exam_submission (member_id, subject_id, round, code_answer, score, feedback)
VALUES (
  2,
  1,
  1,
  '<!DOCTYPE html>\n<html>\n<head>\n<style>\nbutton { background-color: blue; color: white; }\n</style>\n</head>\n<body>\n<button>제출</button>\n</body>\n</html>',
  85,
  '구조는 잘 잡았으나, 접근성 고려 태그 사용이 부족합니다.'
);
