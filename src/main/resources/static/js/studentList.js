// 모달 제어
const modal = document.getElementById('addStudentModal');

function openAddModal() {
    modal.style.display = "block";
}

function closeAddModal() {
    modal.style.display = "none";
}

// 모달 외부 클릭 시 닫기
window.onclick = (event) => {
    if (event.target == modal) {
        closeAddModal();
    }
}

/*// 폼 제출 처리
document.getElementById('addStudentForm').onsubmit = (e) => {
    e.preventDefault();
    // 여기에 폼 데이터 처리 로직 추가
    closeAddModal();
}*/