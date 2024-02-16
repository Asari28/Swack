"use strict";

document.addEventListener("DOMContentLoaded", () => {
		const elError = document.getElementById("error");
		if(elError.textContent.length > 0){
			alert(elError.textContent);
		}
});