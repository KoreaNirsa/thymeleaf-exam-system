// 비밀번호 수정 모달
const passwordModal = document.getElementById('passwordModal');
const passwordForm = document.getElementById('passwordForm');
const passwordError = document.getElementById('passwordError');
let memberId;
function openPasswordModal(inputMemberId) {
	memberId = inputMemberId;
    passwordModal.style.display = "block";
    passwordForm.reset();
    passwordError.textContent = '';
}

function closePasswordModal() {
    passwordModal.style.display = "none";
}

passwordForm.onsubmit = async (e) => {
    e.preventDefault();
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (newPassword !== confirmPassword) {
        passwordError.textContent = '비밀번호가 일치하지 않습니다.';
        return;
    }
	
	try {
	    const response = await fetch(`/api/student/${memberId}`, {
	        method: 'PATCH',
	        headers: {
	            'Content-Type': 'application/json'
	        },
	        body: JSON.stringify({
	            newPassword: newPassword,
	            confirmPassword: confirmPassword
	        })
	    });
		
	    if (response.ok) {
	        alert('비밀번호가 성공적으로 변경되었습니다.');
	        closePasswordModal();
	    } else {
	        const errorText = await response.text();
	        passwordError.textContent = `변경 실패: ${errorText}`;
	    }
	} catch (err) {
	    console.error(err);
	    passwordError.textContent = '서버 요청 중 오류가 발생했습니다.';
	}
};

// 점수 수정 모달
const scoreModal = document.getElementById('scoreModal');
const scoreForm = document.getElementById('scoreForm');
const score1 = document.getElementById('score1');
const score2 = document.getElementById('score2');

function openScoreModal(subjectName, firstScore, secondScore) {
	console.log(subjectName, firstScore, secondScore)
    document.getElementById('subjectName').value = subjectName;
	score1.value = firstScore;
	score2.value = secondScore;
    scoreModal.style.display = "block";
}

function closeScoreModal() {
    scoreModal.style.display = "none";
}

scoreForm.onsubmit = (e) => {
    e.preventDefault();
    // 점수 저장 로직 추가
    closeScoreModal();
};

// 피드백 모달
const feedbackModal = document.getElementById('feedbackModal');
const feedbackForm = document.getElementById('feedbackForm');
const feedbackContent = document.getElementById('feedbackContent');
const currentChar = document.getElementById('currentChar');

function openFeedbackModal(subjectName, feedback, subjectId, studentExamSubmissionId) {
	const feedbackSubjectId = document.createElement('input');
	feedbackSubjectId.type = 'hidden';
	feedbackSubjectId.id = 'subjectId';
	feedbackSubjectId.name = 'subjectId';
	feedbackSubjectId.value = subjectId;
	feedbackForm.appendChild(feedbackSubjectId);
	
	const feedbackStudentExamSubmissionId = document.createElement('input');
	feedbackStudentExamSubmissionId.type = 'hidden';
	feedbackStudentExamSubmissionId.id = 'studentExamSubmissionId';
	feedbackStudentExamSubmissionId.name = 'studentExamSubmissionId';
	feedbackStudentExamSubmissionId.value = studentExamSubmissionId;
	feedbackForm.appendChild(feedbackStudentExamSubmissionId);
	
	feedbackForm.action = `/evaluation/${studentExamSubmissionId}`;
	feedbackForm.method = 'post';
	
    document.getElementById('feedbackSubject').value = subjectName;
    feedbackContent.value = feedback;
    feedbackModal.style.display = "block";
	feedbackForm.onsubmit();
}

function closeFeedbackModal() {
	document.getElementById('subjectId').remove()
	document.getElementById('studentExamSubmissionId').remove()
    feedbackModal.style.display = "none";
}

feedbackContent.addEventListener('input', function() {
    currentChar.textContent = this.value.length;
});

/*feedbackForm.onsubmit = (e) => {
    e.preventDefault();
    // 피드백 저장 로직 추가
    closeFeedbackModal();
};*/

// 모달 외부 클릭 시 닫기
window.onclick = (event) => {
    if (event.target == scoreModal) {
        closeScoreModal();
    }
    if (event.target == feedbackModal) {
        closeFeedbackModal();
    }
}
