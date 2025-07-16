// CodeMirror 초기화
 let editor = CodeMirror.fromTextArea(document.getElementById('code-answer'), {
     mode: "javascript",
     theme: "monokai",
     lineNumbers: true,
     autoCloseBrackets: true,
     matchBrackets: true,
     indentUnit: 2,
     tabSize: 2,
     lineWrapping: true,
     extraKeys: {
         "Tab": function(cm) {
             if (cm.somethingSelected()) {
                 cm.indentSelection("add");
             } else {
                 cm.replaceSelection("  ", "end");
             }
         }
     }
 });

 // 제출 함수
 function submitAnswer() {
     const code = editor.getValue();
     if (!code.trim()) {
         alert('답안을 작성해주세요.');
         return;
     }
     
     // 여기에 제출 로직 추가
     if (confirm('작성한 답안을 제출하시겠습니까?')) {
         alert('답안이 제출되었습니다.');
         // 제출 후 처리 로직
     }
 }

 // 뒤로가기 확인
 function confirmGoBack() {
     if (editor.getValue().trim() && !confirm('뒤로가기를 하면 현재 내용이 사라집니다. 정말 취소하시겠습니까?')) {
         return;
     }
     window.history.back();
 }

 // 페이지 이탈 방지
 window.addEventListener('beforeunload', function(e) {
     if (editor.getValue().trim()) {
         e.preventDefault();
         e.returnValue = '';
     }
 });