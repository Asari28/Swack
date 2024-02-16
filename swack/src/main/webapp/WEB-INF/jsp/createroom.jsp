<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <title>Swack - ルーム作成画面</title>

    <link rel="shortcut icon" href="images/favicon.ico" />

    <!-- CDN : Bootstrap CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
      crossorigin="anonymous"
    />

    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="css/createroom.css" />
  </head>

  <body>
    <div class="container form-container">
      <div class="row">
        <div class="col-md-12 room-form">
          <h3>ルームを作成する</h3>
          <p class="error" id="errorMsg">${errorMsg }</p>
          <p class="input_note_special medium_bottom_margin">
            ルームとはメンバーがコミュニケーションを取る場所です。特定のトピックに基づいてルームを作ると良いでしょう
            (例: #営業)。
          </p>

          <form action="CreateRoomServlet" method="post">
            <div class="form-check form-switch mt-3">
              <input
              	name="privated"
                class="form-check-input"
                id="chk"
                type="checkbox"
                autocomplete="off"
              />
              <label class="form-check-label" class="btn btn-secondary active">
                <span class="check_label">パブリック</span> </label
              ><br />
              <span class="check_text"
                >このルームは、ワークスペースのメンバーであれば誰でも閲覧・参加することができます。</span
              >
            </div>

            <div class="form-group mt-5">
              <label class="control-label">名前</label>
              <input
              	name="roomName"
                id="name"
                class="form-control"
                type="text"
                placeholder="# 例:営業"
                autofocus
              />
              <span class="name-note">ルームの名前を入力してください。</span>
            </div>

            <div class="form-group mt-5">
              <label class="control-label">招待の送信先:(任意)</label>
              <select name="joinUserId" id="names" class="form-select" multiple>
	              <c:forEach var="user" items="${userList }">
	              	<option value="${user.userId}">${user.userName}</option>
	              </c:forEach>
              </select>
              <span class="users-note"
                >このルームに追加したい人を選んでください。</span
              >
            </div>

            <div class="room-form-btn">
              <a href="MainServlet"><input type="button" class="btn btn-default" value="キャンセル"/></a>
              <input type="submit" id="send" class="btn btn-default" value="ルームを作成する"/>
            </div>
          </form>
        </div>
      </div>
    </div>
    <!-- container -->

    <!-- CDN : Bootstrap Bundle with Popper -->
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
      crossorigin="anonymous"
    ></script>

    <script src="js/createroom.js"></script>
  </body>
</html>
