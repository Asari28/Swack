"use strict";
//var errormsg=request.getAttribute("errorMsg");
//alert(errormsg);
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
	return new bootstrap.Tooltip(tooltipTriggerEl)
})

document.addEventListener("DOMContentLoaded", () => {
	// elements
	const elSendButton = document.getElementById("send");
	const elMessage = document.getElementById("message");
	const elInputTypeText = document.querySelector("input[type=text]");
	const elMessageForm = document.getElementById("messageForm");
	const elLogArea = document.getElementById("logArea");
	const limit = document.querySelector(".allroom");
	const str = limit.textContent;
	const len = 14; // 半角50字（全角約25字）
	if (str.length > len) {
		limit.textContent = str.substring(0, len) + "…";
	}

	// ログ表示部の最下部へ(110はheader+footerのheight)
	elLogArea.scrollTop = elLogArea.scrollHeight + 110;

	elInputTypeText.addEventListener("keydown", (e) => {
		if ((e.which && e.which === 13) || (e.keyCode && e.keyCode === 13)) {
			return false;
		} else {
			return true;
		}
	});

	if (elMessage.value.length == 0) {
		elSendButton.style.color = "rgba(44, 45, 48, 0.75)";
		elSendButton.style.backgroundColor = "#e8e8e8";
		elSendButton.disabled = true;
	}
	elMessage.addEventListener("keydown", (e) => {
		if (e.target.value.length < 1) {
			elSendButton.style.color = "rgba(44, 45, 48, 0.75)";
			elSendButton.style.backgroundColor = "#e8e8e8";
			elSendButton.disabled = true;
		} else {
			elSendButton.style.color = "#ffffff";
			elSendButton.style.backgroundColor = "#008952";
			elSendButton.disabled = false;
		}
	});
	elMessage.addEventListener("keydown", (e) => {
		// Ctrlキーが押されてる状態か判定
		if (e.ctrlKey) {
			// 押されたキーがEnterキーで、テキストエリアに何かが入力されているか判定
			if (
				((e.which && e.which === 13) || (e.keyCode && e.keyCode === 13)) &&
				e.target.value
			) {
				// フォームを送信
				elMessageForm.submit();
				return false;
			}
		}
	});
});

function logout() {
	// elements
	const elLogoutForm = document.getElementById("logoutForm");

	localStorage.removeItem("loginData");
	elLogoutForm.submit();
}

function doReload() {
	window.location.reload();
}