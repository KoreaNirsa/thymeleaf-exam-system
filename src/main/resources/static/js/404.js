// 애니메이션 효과
document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('.error-container').classList.add('show');
    
    // 배경 요소 랜덤 위치 설정
    const shapes = document.querySelectorAll('.error-shape');
    shapes.forEach(shape => {
        const randomX = Math.random() * 100;
        const randomY = Math.random() * 100;
        const randomDelay = Math.random() * 2;
        const randomDuration = 3 + Math.random() * 2;
        
        shape.style.left = `${randomX}%`;
        shape.style.top = `${randomY}%`;
        shape.style.animationDelay = `${randomDelay}s`;
        shape.style.animationDuration = `${randomDuration}s`;
    });
});