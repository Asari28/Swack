"use strict";

var deleteModal = document.getElementById('deleteModal')
deleteModal.addEventListener('show.bs.modal', function(event) {
	// モーダルを起動したボタン
	var button = event.relatedTarget
	// data-bs-*属性から情報を取り出す
	var recipient = button.getAttribute('data-bs-whatever')
	// モーダルのコンテンツを更新
	var modalTitle = deleteModal.querySelector('.modal-title')
	var modalBodyInput = deleteModal.querySelector('.modal-body input')

	modalTitle.textContent = 'メッセージを削除しますか？'
	modalBodyInput.value = recipient
})

//.deleteiconクラスを持つすべてのボタンに対してイベントリスナーを設定
document.querySelectorAll('.deleteicon').forEach(function(button) {
	button.addEventListener('click', function() {
		//ボタンに関連付けられたchatLogIdを取得
		var chatLogId = button.getAttribute('data-bs-whatever');
		var roomId = button.getAttribute('data-bs-whatever2')
		//フォーム内のchatLogIdInputに値を設定
		document.getElementById('chatLogIdInput').value = chatLogId;
		//フォーム内のroomIdInputに値を設定
		document.getElementById('roomIdInput').value = roomId;
	});
});

//メッセージ削除が成功・失敗したときのスクリプト
function deleteMessage(isError) {
	// エラーモーダル表示のための変数
	if (isError) {
		// メッセージの削除ロジックを続行
		alert("チャットを削除しました");
	} else {
		// エラーモーダルを表示=エラーの時
		alert("チャットの削除に失敗しました");
	}
}