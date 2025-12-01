// main.js Handles OTP sending for login and registration pages

document.addEventListener('DOMContentLoaded', function () {

    // SHA-256 hash function using CryptoJS
    function hashOtp(otp) {
        return CryptoJS.SHA256(otp).toString();
    }

    // Intercept login form submit
    const loginForm = document.querySelector('form[action="/login"]');
    if (loginForm) {
        loginForm.addEventListener('submit', function (e) {
            e.preventDefault();

            const otpInput = loginForm.querySelector('input[name="otp"]');
            const plainOtp = otpInput.value.trim();

            // hash OTP
            const hashedOtp = hashOtp(plainOtp);

            // replace OTP with hash
            otpInput.value = hashedOtp;

            // submit form
            loginForm.submit();
        });
    }

    // handle login OTP send
    const loginOtpBtn = document.getElementById('btn-send-login-otp');
    if (loginOtpBtn) {
        loginOtpBtn.addEventListener('click', function () {
            const email = document.getElementById('login-email').value;
            if (!email) {
                showMessage('Please enter your email.', false);
                return;
            }
            sendOtp('/send-login-otp', { email: email });
        });
    }

    // handle registration OTP send
    const regOtpBtn = document.getElementById('btn-send-reg-otp');
    if (regOtpBtn) {
        regOtpBtn.addEventListener('click', function () {
            const nameEl = document.getElementById('reg-name');
            const emailEl = document.getElementById('reg-email');
            const roleElement = document.querySelector('input[name="role"]:checked');
            const name = nameEl ? nameEl.value.trim() : '';
            const email = emailEl ? emailEl.value.trim() : '';
            if (!name || !email || !roleElement) {
                showMessage('Please fill in all fields and select a role.', false);
                return;
            }

            const role = roleElement.value;
            sendOtp('/send-reg-otp', { name: name, email: email, role: role });
        });
    }

    // Helper function to send OTP
    function sendOtp(url, data) {
        fetch(url, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams(data),
        })
            .then(response => response.text())
            .then(result => showMessage(result, !result.includes("Error")))
            .catch(error => showMessage("Error sending OTP: " + error.message, false));
    }

    // Helper for messages
    function showMessage(text, isSuccess) {
        const messageDiv = document.querySelector(".message");
        if (messageDiv) {
            messageDiv.textContent = text;
            messageDiv.className = "message" + (isSuccess ? " success" : "");
            messageDiv.style.display = "block";
        } else {
            alert(text);
        }
    }
});
