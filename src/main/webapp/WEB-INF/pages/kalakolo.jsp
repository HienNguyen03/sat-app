

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes" />

<title>opt.is linkshortener</title>

<link
	href="http://cdn.jsdelivr.net/webjars/Semantic-UI/2.2.2/semantic.min.css"
	th:href="@{/webjars/Semantic-UI/2.2.2/semantic.min.css}"
	rel="stylesheet" media="screen" />
<link href="../css/app.css" th:href="@{/css/app.css}" rel="stylesheet"
	media="screen" />
</head>
<body ng-app="linkApp">
	<div ng-controller="LinkController">
		<div class="center input-group">
			<input type="text" ng-model="longUrl" class="form-control"
				id="longUrl" placeholder="Enter a link to shorten it" /> <span
				class="input-group-btn">
				<button class="btn btn-default" type="button"
					ng-click="shorten(longUrl)">Shorten!</button>
			</span>
		</div>

		<div id="result" ng-show="link">
			You can get to your submitted URL through: <a
				ng-href="{{link.shortUrl}}">{{link.shortUrl}}</a>
		</div>

	</div>

	<script
		src="http://cdn.jsdelivr.net/webjars/angularjs/2.0.0-alpha.22/angular2.js"
		th:src="@{/webjars/angularjs/2.0.0-alpha.22/angular2.js}"></script>
	<script
		src="http://cdn.jsdelivr.net/webjars/Semantic-UI/2.2.2/semantic.min.js"
		th:src="@{/webjars/Semantic-UI/2.2.2/semantic.min.js}"></script>
	<script type="text/javascript" src="../js/app.js"
		th:src="@{/js/app.js}"></script>
</body>
</html>