CREATE TABLE subject (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  first_exam_date DATE,
  second_exam_date DATE
);

CREATE TABLE instructor (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  phone VARCHAR(20)
);

CREATE TABLE student (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  batch VARCHAR(50),
  name VARCHAR(255),
  phone VARCHAR(20)
);

CREATE TABLE evaluation_status (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  subject_id BIGINT,
  status VARCHAR(50),
  round INT,
  FOREIGN KEY (subject_id) REFERENCES subject(id)
);

CREATE TABLE exam_question (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  subject_id BIGINT,
  round INT,
  question_text TEXT,
  FOREIGN KEY (subject_id) REFERENCES subject(id)
);

CREATE TABLE student_exam_submission (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  student_id BIGINT,
  subject_id BIGINT,
  round INT,
  code_answer TEXT,
  score INT,
  feedback VARCHAR(1000),
  FOREIGN KEY (student_id) REFERENCES student(id),
  FOREIGN KEY (subject_id) REFERENCES subject(id)
);