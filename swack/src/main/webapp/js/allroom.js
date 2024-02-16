"use strict";

document.addEventListener("DOMContentLoaded", () => {
  // elements
  const elSendButton = document.getElementById("send");
  const elRooms = document.getElementById("rooms");

// rooms要素で選択された部屋の数が0の場合、送信ボタンを無効にする
  if (countSelectRooms(elRooms) == 0) {
    elSendButton.style.color = "rgba(44, 45, 48, 0.75)";
    elSendButton.style.backgroundColor = "#e8e8e8";
    elSendButton.disabled = true;
  }
  elRooms.addEventListener("change", () => {
    if (countSelectRooms(elRooms) < 1) {
		// 選択された部屋が1つもない場合
      elSendButton.style.color = "rgba(44, 45, 48, 0.75)";
      elSendButton.style.backgroundColor = "#e8e8e8";
      elSendButton.disabled = true;
    } else {
		// 1つ以上の部屋が選択されている場合
      elSendButton.style.color = "#ffffff";
      elSendButton.style.backgroundColor = "#008952";
      elSendButton.disabled = false;
    }
  });
});
function countSelectRooms(rooms) {
  let count = 0;
  for (let i = 0; i < rooms.length; i++) {
    if (rooms[i].selected) {
      count++;
    }
  }
  return count;
}