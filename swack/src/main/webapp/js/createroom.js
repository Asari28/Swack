"use strict";

document.addEventListener("DOMContentLoaded", () => {
	// elements
	const elNames = document.getElementById("names");
	const elSendButton = document.getElementById("send");
	const elH3 = document.querySelector("h3");
	const elCheckLabel = document.querySelector(".check_label");
	const elCheckText = document.querySelector(".check_text");
	const elInputTypeChk = document.getElementById("chk");
	const elInputTypeText = document.querySelector("input[type=text]");
	const eltext = document.getElementById("name");

	// names要素の値が空の場合、送信ボタンを無効にする
	if (elNames.value.length == 0) {
		elSendButton.style.color = "rgba(44, 45, 48, 0.75)";
		elSendButton.style.backgroundColor = "#e8e8e8";
		elSendButton.disabled = true;
	}
	// names要素の選択状態が変更されたとき
	elNames.addEventListener("change", () => {
		let count = 0;
		for (let i = 0; i < elNames.length; i++) {
			if (elNames[i].selected) {
				count++;
			}
		}

		if (count < 1) {
			//		メンバーを選択していなかったら
			elSendButton.style.color = "rgba(44, 45, 48, 0.75)";
			elSendButton.style.backgroundColor = "#e8e8e8";
			elSendButton.disabled = true;
		} else {
			//		1人以上選択していたら
			elSendButton.style.color = "#ffffff";
			elSendButton.style.backgroundColor = "#008952";
			elSendButton.disabled = false;
		}
	});

	elInputTypeChk.addEventListener("change", (e) => {
		var thisValue = e.target.checked;
		var state = thisValue ? true : false;
		// チェックボックスの状態に応じて表示を変更
		if (state) {
			elH3.textContent = "プライベートルームを作成する";
			elCheckLabel.textContent = "プライベート";
			elCheckText.textContent =
				"このルームは、招待によってのみ参加または確認することができます。";
		} else {
			elH3.textContent = "ルームを作成する";
			elCheckLabel.textContent = "パブリック";
			elCheckText.textContent =
				"このルームは、ワークスペースのメンバーであれば誰でも閲覧・参加することができます。";
		}
	});

	elInputTypeText.addEventListener("keydown", (e) => {
		if ((e.which && e.which === 13) || (e.keyCode && e.keyCode === 13)) {
			return false;
		} else {
			return true;
		}
	});
});
