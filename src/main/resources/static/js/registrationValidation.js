let passwordCriteriaMet = {};

document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll('.buttonUser');
    buttons.forEach(button => {
      button.addEventListener("click", function() {
        selectUserType(this.getAttribute("data-user-type"));
      });
    });
  });
  
  function selectUserType(userType) {
    document.getElementById('selectedUserType').value = userType;
  
    const buttons = document.querySelectorAll('.buttonUser');
    buttons.forEach(button => {
      button.classList.remove('selectedRegistration');
    });
  
    const selectedButton = document.querySelector(`[data-user-type="${userType}"]`);
    selectedButton.classList.add('selectedRegistration');
  }


function checkUserTypeSelection() {
    const selectedUserType = document.getElementById('selectedUserType').value;
    const userTypeError = document.getElementById('userTypeError');

    if (!selectedUserType) {
        const userTypeErrorIcon = `
            <svg class="inline w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
            </svg>
            Molimo odaberite vrstu korisnika.`;
        userTypeError.innerHTML = userTypeErrorIcon;
        return false;
    } else {
        userTypeError.textContent = '';
    }
    return true;
}

function validateForm() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (password !== confirmPassword) {
        return false;
    }

    if (!passwordCriteriaMet.length || !passwordCriteriaMet.hasNumber || !passwordCriteriaMet.hasSpecialChar) {
        return false;
    }

    if (!document.getElementById('acceptTermsCheckbox').checked) {
        return false;
    }

    return true;
}

function validatePassword() {
    const password = document.getElementById('password').value;
    const lengthCriteria = document.getElementById('lengthCriteria');
    const numberCriteria = document.getElementById('numberCriteria');
    const specialCharCriteria = document.getElementById('specialCharCriteria');

    passwordCriteriaMet = {
        length: password.length >= 6,
        hasNumber: /\d/.test(password),
        hasSpecialChar: /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(password),
    };

    const gray = "rgb(107, 114, 128)";
    const lightGreen = "rgb(34, 197, 94)";

    lengthCriteria.style.color = passwordCriteriaMet.length ? lightGreen : gray;
    numberCriteria.style.color = passwordCriteriaMet.hasNumber ? lightGreen : gray;
    specialCharCriteria.style.color = passwordCriteriaMet.hasSpecialChar ? lightGreen : gray;
}

function checkPasswordsMatch() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const passwordError = document.getElementById('passwordError');

    if (password === confirmPassword) {
        passwordError.textContent = '';
    } else {
        const passwordErrorIcon = `
            <svg class="inline w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
            </svg>
            Lozinke se ne podudaraju.`;
        passwordError.innerHTML = passwordErrorIcon;
    }
}