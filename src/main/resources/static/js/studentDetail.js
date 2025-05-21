// 비밀번호 수정 모달
const passwordModal = document.getElementById('passwordModal');
const passwordForm = document.getElementById('passwordForm');
const passwordError = document.getElementById('passwordError');

function openPasswordModal() {
    passwordModal.style.display = "block";
    passwordForm.reset();
    passwordError.textContent = '';
}

function closePasswordModal() {
    passwordModal.style.display = "none";
}

passwordForm.onsubmit = (e) => {
    e.preventDefault();
    const newPassword = document.getElementById('newPassword').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (newPassword !== confirmPassword) {
        passwordError.textContent = '비밀번호가 일치하지 않습니다.';
        return;
    }

    // 비밀번호 변경 로직 추가
    alert('비밀번호가 성공적으로 변경되었습니다.');
    closePasswordModal();
};

// 점수 수정 모달
const scoreModal = document.getElementById('scoreModal');
const scoreForm = document.getElementById('scoreForm');

function openScoreModal(subject) {
    document.getElementById('subjectName').value = subject;
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

function openFeedbackModal(subject) {
    document.getElementById('feedbackSubject').value = subject;
    feedbackModal.style.display = "block";
}

function closeFeedbackModal() {
    feedbackModal.style.display = "none";
}

feedbackContent.addEventListener('input', function() {
    currentChar.textContent = this.value.length;
});

feedbackForm.onsubmit = (e) => {
    e.preventDefault();
    // 피드백 저장 로직 추가
    closeFeedbackModal();
};

// 모달 외부 클릭 시 닫기
window.onclick = (event) => {
    if (event.target == scoreModal) {
        closeScoreModal();
    }
    if (event.target == feedbackModal) {
        closeFeedbackModal();
    }
}
