<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <title>Swack - 強制退会画面</title>

    <link rel="shortcut icon" href="images/favicon.ico" />

    <!-- CDN : Bootstrap CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
      crossorigin="anonymous"
    />

    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="css/alluser.css" />
  </head>

  <body>
    <div class="container form-container">
      <div class="row">
        <div class="col-md-12 member-form">
          <h3>ワークスペースから退会させる</h3>
		  <p class="error" id="errorMsg">${errorMsg }</p>
          <form action="ExitServlet" method="post">
            <div class="form-group">
              <label class="control-label">メンバー</label>
              <select id="users" name="userIdList" class="form-select" multiple>
              	<c:forEach var="user" items="${userList}">
	            	<option value="${user.userId}">${user.userName}</option>
	            </c:forEach>
              </select>
              <span class="users-note"
                >退会させたい人を選んでください。</span
              >
            </div>

            <div class="member-form-btn">
              <a href="MainServlet?roomId=${roomId}"
                ><input
                  type="button"
                  class="btn btn-default"
                  value="キャンセル"
              /></a>
              <input type="submit" id="send" class="btn btn-default" name="btn" value="退会させる"/>
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

	<script src="js/alluser.js"></script>
  </body>
</html>
