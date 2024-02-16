"use strict";

document.addEventListener("DOMContentLoaded", () => {
	// elements
	const elSendButton = document.getElementById("send");
	const elUsers = document.getElementById("users");

	// users要素で選択されたユーザーの数が0の場合、送信ボタンを無効にする
	if (countSelectUsers(elUsers) == 0) {
		elSendButton.style.color = "rgba(44, 45, 48, 0.75)";
		elSendButton.style.backgroundColor = "#e8e8e8";
		elSendButton.disabled = true;
	}
	elUsers.addEventListener("change", () => {
		if (countSelectUsers(elUsers) < 1) {
			//		ユーザーが一人も選択されなかった場合
			elSendButton.style.color = "rgba(44, 45, 48, 0.75)";
			elSendButton.style.backgroundColor = "#e8e8e8";
			elSendButton.disabled = true;
		} else {
			//		一人以上選択された場合
			elSendButton.style.color = "#ffffff";
			elSendButton.style.backgroundColor = "#008952";
			elSendButton.disabled = false;
		}
	});
});

function countSelectUsers(users) {
	let count = 0;
	for (let i = 0; i < users.length; i++) {
		if (users[i].selected) {
			count++;
		}
	}
	return count;
}
