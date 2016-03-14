<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login Success Page</title>
<style>
html {
	text-align: center;
}
</style>
</head>
<body>
	<%
		//allow access only if session exists
		String user = (String) session.getAttribute("user");
		String userName = null;
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("user"))
					userName = cookie.getValue();
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
		}
	%>
	<h3>
		Bonjour
		<%=userName%>, identifiants correct ! Votre ID de session est :<%=sessionID%></h3>
	User=<%=user%>
	<p>
	<form action="Logout" method="post">
		<input type="submit" value="Logout">
	</form>
	</p>

	<h4>Liste des services admin :</h4>
	<p>
		<a href="admin/ShowUsers" target="_blank">Voir les utilisateurs</a>
	</p>
	<p>
		<a href="admin/ShowGates" target="_blank">Voir les portes</a>
	</p>
	<p>
		<a href="admin/AddUser.html" target="_blank">Ajouter un
			utilisateur</a>
	</p>
</body>
</html>