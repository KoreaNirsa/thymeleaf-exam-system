const menu = document.querySelector('.instructor-menu');
const submenu = document.querySelector('.submenu');
let timeout;

menu.addEventListener('mouseenter', () => {
    clearTimeout(timeout);
    submenu.style.display = 'block';
});

menu.addEventListener('mouseleave', () => {
    timeout = setTimeout(() => {
        submenu.style.display = 'none';
    }, 300); 
});