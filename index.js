const sendBtn = document.getElementById("sendBtn");
const registerBtn = document.getElementById("registerBtn");
const msg = document.getElementById("msg");
let generatedOtp = null;
let timer = null;

function showMessage(text, type) {
  msg.textContent = text;
  msg.className = "message " + (type || "");
}

function generateOtp() {
  return Math.floor(100000 + Math.random() * 900000).toString();
}

function startTimer() {
  let time = 30;
  sendBtn.disabled = true;
  sendBtn.textContent = Resend (${time}s);

  timer = setInterval(() => {
    time--;
    sendBtn.textContent = Resend (${time}s);

    if (time <= 0) {
      clearInterval(timer);
      sendBtn.disabled = false;
      sendBtn.textContent = "Send OTP";
    }
  }, 1000);
}

sendBtn.addEventListener("click", () => {
  const email = document.getElementById("email").value.trim();

  if (!email) {
    showMessage("Enter email to receive OTP.", "error");
    return;
  }

  generatedOtp = generateOtp();
  console.log("Generated OTP (simulation):", generatedOtp);

  showMessage("OTP sent! (Check console)", "success");
  startTimer();
});

registerBtn.addEventListener("click", () => {
  const first = document.getElementById("first").value.trim();
  const last = document.getElementById("last").value.trim();
  const userid = document.getElementById("userid").value.trim();
  const email = document.getElementById("email").value.trim();
  const otp = document.getElementById("otp").value.trim();
  const role = document.querySelector('input[name=role]:checked')?.value;

  if (!first || !last || !userid || !email) {
    showMessage("Fill all required fields.", "error");
    return;
  }

  if (!generatedOtp) {
    showMessage("Please request OTP first.", "error");
    return;
  }

  if (otp !== generatedOtp) {
    showMessage("Invalid OTP. Try again.", "error");
    return;
  }

  showMessage(Registration successful! Welcome ${first}., "success");

  generatedOtp = null;
  clearInterval(timer);
  sendBtn.disabled = false;
  sendBtn.textContent = "Send OTP";
});