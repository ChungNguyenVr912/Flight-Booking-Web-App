// const form = document.querySelector('.needs-validation');
const inputUser = document.getElementById("username");
const inputEmail = document.getElementById("email");
const invalidUsernameFeedback = document.getElementById("invalid-username")
const invalidEmailFeedback = document.getElementById("invalid-email")
const validUsernameFeedback = document.getElementById("valid-username")
const validEmailFeedback = document.getElementById("valid-email")
const loginForm = document.getElementById("loginForm");
const loginFeedbackField = document.getElementById("loginFeedback")

inputUser.addEventListener("blur", function () {
    var value = inputUser.value;
    if (value !== "" && value !== null) {
        const request = new XMLHttpRequest();
        request.open('post', 'http://localhost:8080/home', true);
        request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE && request.status === 200) {
                if (request.responseText === "valid") {
                    validUsernameFeedback.textContent = "Username is valid!";
                    inputUser.style.borderColor = "green";
                    invalidUsernameFeedback.textContent = "";
                } else {
                    validUsernameFeedback.textContent = "";
                    invalidUsernameFeedback.textContent = "Username is invalid!";
                    inputUser.style.borderColor = "red";
                }
            }
        };
        request.send("action=check_username&username=" + encodeURIComponent(value));
    } else {
        validUsernameFeedback.textContent = "";
        invalidUsernameFeedback.textContent = "";
    }
});

inputEmail.addEventListener("blur", function () {
    var value = inputEmail.value;
    if (value !== "" && value !== null) {
        const request = new XMLHttpRequest();
        request.open('post', 'http://localhost:8080/home', true);
        request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        request.onreadystatechange = function () {
            if (request.readyState === XMLHttpRequest.DONE && request.status === 200) {
                if (request.responseText === "valid") {
                    validEmailFeedback.textContent = "Email is valid!";
                    invalidEmailFeedback.textContent = "";
                    inputEmail.style.borderColor = "green";
                } else {
                    validEmailFeedback.textContent = "";
                    invalidEmailFeedback.textContent = "Email is invalid!";
                    inputEmail.style.borderColor = "red";
                }
            }
        };
        request.send("action=check_email&email=" + encodeURIComponent(value));
    } else {
        invalidEmailFeedback.textContent = "";
        validEmailFeedback.textContent = "";
    }
});

// loginForm.addEventListener("submit", function () {
//     const request = new XMLHttpRequest();
//     request.open('post', 'http://localhost:8080/home', true);
//     request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
//     request.onreadystatechange = function () {
//         if (request.readyState === XMLHttpRequest.DONE && request.status === 200) {
//             loginFeedbackField.textContent = request.responseText;
//         }
//     };
// });


(() => {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    const forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation();
                validUsernameFeedback.textContent = "";
                validEmailFeedback.textContent = "";
            }
            form.classList.add('was-validated')
        }, false)
    })
})()
