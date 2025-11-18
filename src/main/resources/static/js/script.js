// ============================
// LOGIN FORM
// ============================
const loginForm = document.getElementById('loginForm');
if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const credentials = {
            email: document.getElementById('email').value,
            password: document.getElementById('password').value
        };

        try {
            const response = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(credentials)
            });

            const message = await response.text();

            if (response.ok) {
                alert("✅ " + message);
                localStorage.setItem("loggedInEmail", credentials.email); // Save login session
                setTimeout(() => {
                    window.location.href = "/dashboard.html"; //delay redirect by 1.5 seconds using setTimeout.
                }, 1500);
            } else {
                alert("❌ " + message);
            }

        } catch (error) {
            alert("⚠️ Failed to connect to server: " + error.message);
        }
    });
}

// ============================
// FEEDBACK FORM
// ============================
const feedbackForm = document.getElementById('feedbackForm');
if (feedbackForm) {
    feedbackForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const feedback = {
            email: feedbackForm.email.value,
            message: feedbackForm.message.value,
            rating: feedbackForm.rating.value
        };

        try {
            const response = await fetch("http://localhost:8080/feedback/add", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(feedback)
            });

            const message = await response.text();

            if (response.ok) {
                document.getElementById('feedbackResult').textContent = "✅ Feedback submitted successfully!";
                feedbackForm.reset();
                setTimeout(() => {
                    window.location.href = "/dashboard.html"; //delay redirect by 1.5 seconds using setTimeout.
                }, 1500);
            } else {
                document.getElementById('feedbackResult').textContent = "❌ " + message;
            }
        } catch (error) {
            document.getElementById('feedbackResult').textContent = "⚠️ " + error.message;
        }
    });
}

// ============================
// NOTIFICATION FORM SCRIPT
// ============================

document.addEventListener("DOMContentLoaded", function () {
    const notifyForm = document.getElementById("notifyForm");
    const resultBox = document.getElementById("notifyResult");

    if (notifyForm) {
        notifyForm.addEventListener("submit", async (e) => {
            e.preventDefault();

            // Collect data from form inputs
            const notificationData = {
                recipientEmail: notifyForm.recipientEmail.value.trim(),
                message: notifyForm.message.value.trim()
            };

            // Debug log (helps verify data)
            console.log("📤 Sending:", notificationData);

            if (!notificationData.recipientEmail || !notificationData.message) {
                resultBox.textContent = "⚠️ Please fill in both fields.";
                resultBox.style.color = "orange";
                return;
            }

            try {
                const response = await fetch("http://localhost:8080/notifications/send", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(notificationData)
                });

                const message = await response.text();

                if (response.ok) {
                    resultBox.textContent = "✅ Notification sent successfully!";
                    resultBox.style.color = "green";
                    notifyForm.reset();
                } else {
                    resultBox.textContent = "❌ Failed to send: " + message;
                    resultBox.style.color = "red";
                }
            } catch (error) {
                resultBox.textContent = "⚠️ Network error: " + error.message;
                resultBox.style.color = "red";
            }
        });
    }
});


// ============================
// REGISTRATION FORM
// ============================
const registerForm = document.getElementById('registerForm');
if (registerForm) {
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const user = {
            username: registerForm.username.value,
            email: registerForm.email.value,
            password: registerForm.password.value,
            phone: registerForm.phone.value,
            address: registerForm.address.value
        };

        try {
            const response = await fetch("http://localhost:8080/users/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(user)
            });

            const message = await response.text();

            if (response.ok) {
                alert("✅ Registration successful! You can now log in.");
                registerForm.reset();
                window.location.href = "/login.html"; // Redirect after registration
            } else {
                alert("❌ Error: " + message);
            }

        } catch (error) {
            alert("⚠️ Failed to connect to server: " + error.message);
        }
    });
}

// ============================
// DASHBOARD HANDLER
// ============================
const userEmailDisplay = document.getElementById("userEmail");
if (userEmailDisplay) {
    const email = localStorage.getItem("loggedInEmail");
    if (email) {
        userEmailDisplay.textContent = email;
    } else {
        window.location.href = "/login.html"; // Redirect if not logged in
    }
}
