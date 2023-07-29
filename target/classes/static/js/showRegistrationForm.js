function selectUserType(userType) {
    document.getElementById('selectedUserType').value = userType;

    const buttons = document.querySelectorAll('.buttonUser');
    buttons.forEach(button => {
        button.classList.remove('selected');
    });
    const selectedButton = document.querySelector(`[onclick="selectUserType('${userType}')]`);
    selectedButton.classList.add('selected');
}

function checkUserTypeSelection() {
    const selectedUserType = document.getElementById('selectedUserType').value;
    if (!selectedUserType) {
        alert('Molimo odaberite vrstu korisnika.');
        return false;
    }
    return true;
}

function validateForm() {
    if (!document.getElementById('acceptTermsCheckbox').checked) {
        document.getElementById('termsError').style.display = 'inline';
        return false;
     }
     return true;
 }