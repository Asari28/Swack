"use strict";

const form = document.getElementById("form");
const msg = document.getElementById("msg");
const msgList = ["Loading now...", "ローディング中...", "こんにちは！", "ようこそ！", "お久しぶりです！"]

document.addEventListener('DOMContentLoaded',() => {
	const index = Math.floor(Math.random() * msgList.length);
	msg.textContent = msgList[index];
});
//ログイン成功時２秒後にメイン画面へ
setTimeout(function(){
	form.submit();
}, 2000);