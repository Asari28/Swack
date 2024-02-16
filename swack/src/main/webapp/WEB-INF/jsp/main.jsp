<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Swack - メイン画面</title>
<link rel="shortcut icon" href="images/favicon.ico">

<!-- CDN : Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous" />

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/editchat.css">
<!-- <link rel="stylesheet" href="css/loading.css"> -->

</head>
<body>
	<div class="container">
		<header class="header">
			<div class="top">
				${user.userName}<a href="AccountUnlockServlet" id="accountlock-btn" name="${user.userId}" class="active"><button>アカウントロック解除</button></a>
				<a href="ExitServlet" id="accountdelete-btn" name="${user.userId}" class="active"><button>アカウント削除</button></a>
			</div>
			<form action="LogoutServlet" id="logoutForm" method="get">
				<input type="submit" value="ログアウト" onclick="logout();">
			</form>
		</header>
		<section class="main">
			<div class="left">
				<h2>Swack</h2>
				<hr>
				<details open>
					<summary>
						ルーム <a href="CreateRoomServlet"><button>＋</button></a>
					</summary>
					<c:forEach var="room" items="${roomList}">
						<a class="list-name"
							href="LastJoinRoomServlet?roomId=${room.roomId}">#
							${room.roomName}</a>
						<br>
					</c:forEach>
					<a href="JoinRoomServlet?roomId=${room.roomId}" class="allroom"
						data-bs-toggle="tooltip" data-bs-placement="top"
						title="参加可能なルーム一覧を表示"> <!--<img src="images/serchicon.svg" class="serchicon"/> -->
						<span>+ルーム参加</span>
					</a>
				</details>
				<details open>
					<summary>
						ダイレクト <a href="CreateDirectServlet?roomId=${room.roomId}"><button>＋</button></a>
					</summary>
					<c:forEach var="direct" items="${directList}">
						<a class="list-name"
							href="LastJoinRoomServlet?roomId=${direct.roomId}">#
							${direct.roomName}</a>
						<br>
					</c:forEach>
				</details>
			</div>
			<!--left -->
			<div class="contents">
				<c:if test="${!room.directed}">
					<div class="join-member-button">
						<a href="JoinMemberServlet?roomId=${room.roomId}"><button>＋</button></a>
					</div>
				</c:if>
				<h2>
					${room.roomName}(${room.memberCount}) <img src="images/reload.svg"
						class="reload pointer" onclick="doReload();" />
				</h2>
				<h3 id="error" class="error" style="color: red">${errorMsg }</h3>
				<hr>
				<div id="logArea" class="contents-main">
					<c:forEach var="chatLog" items="${chatLogList}" varStatus="status">
						<c:if test="${status.last}">
							<input type="hidden" id="lastChatLogId"
								value="${chatLog.chatLogId}">
						</c:if>
						<div class="log-area" id="${chatLog.chatLogId}">
							<div class="log-icon">
								<img src="images/${chatLog.userId}.png"
									onerror="this.src='images/profile.png;'">
							</div>
							<div class="log-box">
								<p class="log-name">
									${chatLog.userName} <span class="log-time">[${chatLog.createdAt}]
										<button class="editicon"
											onclick="editChat(${chatLog.chatLogId},'${chatLog.userId }','${user.userId }');">
											<img src="images/pencil.svg">
										</button>
										<button type="button" class="deleteicon"
											data-bs-toggle="modal" data-bs-target="#deleteModal"
											data-bs-whatever="${chatLog.chatLogId}"
											data-bs-whatever2="${room.roomId}">
											<img src="images/trash.svg">
										</button>
									</span>
								</p>

								<div id="">
									<form action="EditChatLogServlet" method="post">
										<p class="message ${chatLog.chatLogId}">${chatLog.message}</p>
										<textarea class="input-Msg ${chatLog.chatLogId} active"
											name="message"></textarea>
										<input type="hidden" name="chatLogId"
											value="${chatLog.chatLogId}" />
										<div class="edit-bnt-box">
											<a href="MainServlet"> <input type="button"
												class="msg-Not-Btn ${chatLog.chatLogId} active"
												value="キャンセル">
											</a> <input type="submit"
												class="msg-Ok-Btn ${chatLog.chatLogId} active" id="regist"
												value="登録" />
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="contents-footer">
					<form action="MainServlet" method="post" id="messageForm">
						<input type="hidden" name="roomId" value="${room.roomId}">
						<div class="form-wrap">
							<input type="text" name="message" id=message autocomplete="off">
							<input type="submit" value="送信" id="send">
						</div>
					</form>
				</div>
			</div>
			<!--contents -->

			<!-- メッセージ削除モーダル -->
			<div class="modal fade" id="deleteModal" tabindex="-1"
				aria-labelledby="deleteModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="btn-close" data-bs-dismiss="modal"
								aria-label="Close"></button>
						</div>
						<div class="modal-body">
							<h5 class="modal-title" id="deleteModalLabel">メッセージを削除しますか？</h5>
						</div>
						<div class="modal-footer">
							<form action="DeleteChatLogServlet" method="post">
								<input type="hidden" name="chatLogId" id="chatLogIdInput">
								<input type="hidden" name="roomId" id="roomIdInput">
								<button type="button" class="btn btn-secondary"
									data-bs-dismiss="modal">キャンセル</button>
								<button type="submit" class="btn btn-primary" name="action"
									value="delete">削除</button>
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- /メッセージ削除モーダルウィンドウ -->

		</section>
		<!--main -->
	</div>
	<!-- container -->
	<!-- CDN : Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

	<script src="js/messagedelete.js"></script>
	<!-- エラーの表示について -->
	<!-- コードが汚い -->
	<c:if test="${errorMsg eq '成功'}">
		<script>
			var isError = true;
			deleteMessage(isError);
		</script>
	</c:if>
	<c:if test="${errorMsg eq '失敗'}">
		<script>
			var isError = false;
			deleteMessage(isError);
		</script>
	</c:if>
	<!-- /エラーの表示について -->
	<div id="loading">
		<div class="spinner"></div>
	</div>
	<!-- script -->
	<script src="js/main.js"></script>
	<script src="js/editchat.js"></script>
	<script src="js/exit.js"></script>
	<script src="js/error.js"></script>
</body>
</html>
