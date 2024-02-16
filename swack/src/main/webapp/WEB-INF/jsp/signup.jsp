<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Swack - 新規登録画面</title>
<link rel="shortcut icon" href="images/favicon.ico" />

<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="css/signup.css" />

</head>
<body id="body">
	<div class="container">
		<h1>Swack</h1>
		<h2>新規登録</h2>
		<div class="card">
			<p class="error" id="errorMsg">${errorMsg}</p>
			<form action="SignUpServlet" id="loginForm" method="post">
				<h3>Swackワークスペースに参加する</h3>
				<h4>氏名、メールアドレス、パスワードを入力してください。</h4>
				<input type="text" name="userName" id="userName" placeholder="氏名" /><br />
				<input type="email" name="mailAddress" id="mailAddress"
					placeholder="xxxxxx@xxx.xxx" /><br /> <input
					type="password" name="password" id="password" placeholder="パスワード" /><br />
				<input type="submit" value="参加する" onclick="login();" />
				<!-- <label><input type="checkbox" id="save" /><span>ログイン状態を保持する</span></label> -->
			</form>
		</div>

		<a href="LoginServlet">>> ログイン画面へ</a>
	</div>
	<!-- container -->
	<script src="js/sigup.js"></script>
</body>
</html>