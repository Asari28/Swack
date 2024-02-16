"use strict";

const abtn = document.getElementById("accountlock-btn");
const btn = document.getElementById("accountdelete-btn");

if(btn.name == "U0000"){
	abtn.classList.remove("active");
	btn.classList.remove("active");
}

