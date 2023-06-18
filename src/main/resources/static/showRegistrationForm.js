function showOptionForm(option) {
    var formPrivateUser = document.getElementById("formPrivateUser");
    var formBusinessUser = document.getElementById("formBusinessUser");
    var btnPrivateUser = document.getElementById("btnPrivateUser");
    var btnBusinessUser = document.getElementById("btnBusinessUser");

    if (option === 1) {
        formPrivateUser.style.display = "block";
        formBusinessUser.style.display = "none";
        btnPrivateUser.disabled = true;
        btnBusinessUser.disabled = false;
        btnPrivateUser.classList.add("disabled:opacity-25");
    } else if (option === 2) {
        formPrivateUser.style.display = "none";
        formBusinessUser.style.display = "block";
        btnPrivateUser.disabled = false;
        btnBusinessUser.disabled = true;
        btnBusinessUser.classList.add("disabled:opacity-25");
    }
}
