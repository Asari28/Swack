"use strict";

document.addEventListener("DOMContentLoaded", () => {
  // elements
  const elBody = document.getElementById("body");
  const elErrorMsg = document.getElementById("errorMsg");
  const elMailAddress = document.getElementById("mailAddress");
  const elPassword = document.getElementById("password");
  const elLoginForm = document.getElementById("loginForm");

  elBody.style.display = "none";
  if (elErrorMsg.textContent.length == 0) {
	  // ローカルストレージから以前のログインデータを取得
    const oldData = localStorage.getItem("loginData");
    // 以前のログインデータが存在する場合
    if (oldData) {
      const realData = JSON.parse(oldData);
      // 自動ログインが有効な場合
      if (realData.autoLogin) {
		  // ログインフォームに以前のメールアドレスとパスワードを設定して自動でログイン
        elMailAddress.value = realData.mailAddress;
        elPassword.value = realData.password;
        elLoginForm.submit();
      } else {
        elBody.style.display = "block";
      }
    } else {
      elBody.style.display = "block";
    }
  } else {
    localStorage.removeItem("loginData");
    elBody.style.display = "block";
  }
});

function login() {
  // elements
  const elSave = document.getElementById("save");
  const elMailAddress = document.getElementById("mailAddress");
  const elPassword = document.getElementById("password");

  // 「ログイン状態を保持する」にチェックがついていたら
  const save = elSave.checked;
  if (save) {
    console.log("saved!");
    const loginData = {
      mailAddress: elMailAddress.value,
      password: elPassword.value,
      autoLogin: save,
    };
    localStorage.setItem("loginData", JSON.stringify(loginData));
  }
}