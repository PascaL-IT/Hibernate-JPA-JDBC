<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>List of tennis players</title>
<link href="styles.css" rel="stylesheet">
</head>

<body>
	<h1>List of players</h1>
	
	<div class="lrow">
		<div class="lcolumn">
			<h2>Man category:</h2>
			<table>
				<c:forEach var="mplayer" items="${menPlayers}">
					<tr>
						<td>${mplayer.forename}</td>
						<td>${mplayer.name}</td>
					</tr>
				</c:forEach>
			</table>
		</div>

		<div class="lcolumn">
			<h2>Woman category:</h2>
			<table>
				<c:forEach var="wplayer" items="${womenPlayers}">
					<tr>
						<td>${wplayer.forename}</td>
						<td>${wplayer.name}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

</body>
</html>