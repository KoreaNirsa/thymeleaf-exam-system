// 모달 제어
 const subjectModal = document.getElementById('subjectModal');
 const problemModal = document.getElementById('problemModal');
 const submissionsModal = document.getElementById('submissionsModal');
 const codeModal = document.getElementById('codeModal');

 // CodeMirror 인스턴스
 let problemEditor;
 let codeEditor;
 let problemEditors = {
     first: null,
     second: null
 };
 // 과목 추가 모달
 function openSubjectModal() {
     subjectModal.style.display = "block";
 }

 function closeSubjectModal() {
     subjectModal.style.display = "none";
 }

 // 문제 편집 모달
 function openProblemModal(subject) {
     document.getElementById('problemSubject').value = subject;
     problemModal.style.display = "block";

     if (!problemEditors.first) {
         problemEditors.first = CodeMirror.fromTextArea(document.getElementById('firstProblem'), {
             mode: "javascript",
             theme: "monokai",
             lineNumbers: true,
             lineWrapping: true
         });
     }

     if (!problemEditors.second) {
         problemEditors.second = CodeMirror.fromTextArea(document.getElementById('secondProblem'), {
             mode: "javascript",
             theme: "monokai",
             lineNumbers: true,
             lineWrapping: true
         });
     }

     // CodeMirror는 숨겨진 상태에서 init 시 렌더 깨짐 → 첫 번째 탭이 보여지도록 보장
     setTimeout(() => {
         problemEditors.first.refresh();
     }, 100);
 }

 function closeProblemModal() {
     problemModal.style.display = "none";
 }

 // 제출 확인 모달
 function openSubmissionsModal(subject) {
     document.getElementById('submissionSubject').textContent = subject;
     submissionsModal.style.display = "block";
 }

 function closeSubmissionsModal() {
     submissionsModal.style.display = "none";
 }        // 과목 수정 모달
 const editSubjectModal = document.getElementById('editSubjectModal');
 const editSubjectForm = document.getElementById('editSubjectForm');

 function openEditSubjectModal(btn) {
     const subject = btn.dataset.subject;
     const firstDate = btn.dataset.firstDate;
     const secondDate = btn.dataset.secondDate;

     document.getElementById('editSubjectName').value = subject;
     document.getElementById('editFirstEvalDate').value = firstDate;
     document.getElementById('editSecondEvalDate').value = secondDate;

     editSubjectModal.style.display = "block";
 }

 function closeEditSubjectModal() {
     editSubjectModal.style.display = "none";
 }

 editSubjectForm.onsubmit = (e) => {
     e.preventDefault();
     const subject = document.getElementById('editSubjectName').value;
     const firstDate = document.getElementById('editFirstEvalDate').value;
     const secondDate = document.getElementById('editSecondEvalDate').value;

     // 여기에 과목 수정 로직 추가
     alert('과목이 수정되었습니다.');
     closeEditSubjectModal();
 };

 // 코드 확인 모달
 function openCodeModal(student) {
     codeModal.style.display = "block";
     
     // CodeMirror 초기화
     if (!codeEditor) {
         codeEditor = CodeMirror.fromTextArea(document.getElementById('submittedCode'), {
             mode: "javascript",
             theme: "monokai",
             lineNumbers: true,
             lineWrapping: true,
             readOnly: true
         });
     }
 }

 function closeCodeModal() {
     codeModal.style.display = "none";
 }

 // 평가 상태 토글
 function toggleEvaluation(btn, subject) {
     const isStarting = btn.classList.contains('start-eval-btn');
     const card = btn.closest('.evaluation-card');
     const status = card.querySelector('.eval-status');
     
     if (isStarting) {
         btn.classList.remove('start-eval-btn');
         btn.classList.add('end-eval-btn');
         btn.textContent = '평가 종료';
         status.className = 'eval-status in-progress';
         status.textContent = '진행중';
     } else {
         btn.classList.remove('end-eval-btn');
         btn.classList.add('start-eval-btn');
         btn.textContent = '평가 시작';
         status.className = 'eval-status completed';
         status.textContent = '완료';
     }
 }

 // 탭 전환
 function switchTab(btn, tab) {
     const tabs = document.querySelectorAll('.tab-btn');
     const contents = document.querySelectorAll('.eval-content');
     
     tabs.forEach(t => t.classList.remove('active'));
     contents.forEach(c => c.classList.remove('active'));
     
     btn.classList.add('active');
     document.getElementById(tab + 'Eval').classList.add('active');

     // 해당 탭의 CodeMirror 새로고침
     if (problemEditors[tab]) {
         setTimeout(() => {
             problemEditors[tab].refresh();
         }, 100); // display 변경 후 렌더링 반영 시간 확보
     }
 }

 function switchSubmissionTab(btn, tab) {
     const tabs = document.querySelectorAll('.submissions-header .tab-btn');
     tabs.forEach(t => t.classList.remove('active'));
     btn.classList.add('active');
     // 여기에 탭 전환 로직 추가
 }

 // 채점 제출
 function submitGrade() {
     const score = document.getElementById('score').value;
     const feedback = document.getElementById('feedback').value;
     
     if (!score) {
         alert('점수를 입력해주세요.');
         return;
     }
     
     // 채점 데이터 처리 로직 추가
     alert('채점이 완료되었습니다.');
     closeCodeModal();
 }

 // 글자 수 카운트
 const feedback = document.getElementById('feedback');
 const currentChar = document.getElementById('currentChar');
 
 if (feedback) {
     feedback.addEventListener('input', function() {
         currentChar.textContent = this.value.length;
     });
 }

 // 모달 외부 클릭 시 닫기
 window.onclick = (event) => {
     if (event.target == subjectModal) closeSubjectModal();
     if (event.target == problemModal) closeProblemModal();
     if (event.target == submissionsModal) closeSubmissionsModal();            if (event.target == codeModal) closeCodeModal();
     if (event.target == editSubjectModal) closeEditSubjectModal();
 }
