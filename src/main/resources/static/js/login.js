function handleLogin(event) {
    event.preventDefault();
    const generation = document.getElementById('generation').value;
    const name = document.getElementById('name').value;
    const password = document.getElementById('password').value;

    // 여기에 로그인 로직 추가
    console.log('Login attempt:', { generation, name, password });
    
    // 임시로 index.html로 이동
    window.location.href = 'index.html';
}