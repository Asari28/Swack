"use strict";

document.addEventListener("DOMContentLoaded", () => {
  // elements
  const elSendButton = document.getElementById("send");
  const elUsers = document.getElementById("users");

// ページの読み込み時にユーザーが選択されていない場合は、送信ボタンを無効にする
  if (countSelectUsers(elUsers) == 0) {
    elSendButton.style.color = "rgba(44, 45, 48, 0.75)";
    elSendButton.style.backgroundColor = "#e8e8e8";
    elSendButton.disabled = true;
  }
  elUsers.addEventListener("change", () => {
	  // 選択されたユーザーの数に基づいて送信ボタンのスタイルを更新
    if (countSelectUsers(elUsers) < 1) {
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
