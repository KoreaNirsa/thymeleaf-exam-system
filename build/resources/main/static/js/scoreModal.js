        const modal = document.getElementById('scoreModal');
        const scoreCheckBtns = document.getElementsByClassName('score-check');
        const closeBtn = document.getElementsByClassName('close')[0];

        Array.from(scoreCheckBtns).forEach(btn => {
            btn.onclick = () => modal.style.display = "block";
        });

        closeBtn.onclick = () => modal.style.display = "none";

        window.onclick = (event) => {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }