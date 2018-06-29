
function saveDetails() {
	var username = document.getElementById('name').value;
	var teamname = document.getElementById('team').value;
	document.cookie = "name=" + encodeURI(username);
	document.cookie = "team=" + encodeURI(teamname);
}

window.getCookie = function (name) {
	var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
	if (match) return match[2];
}

function cookieSaved() {
	var regexp = new RegExp("(?:^" + "name" + "|;\s*" + "name" + ")=(.*?)(?:;|$)", "g");
	var result = regexp.exec(document.cookie);
	return (result === null) ? false : true;
}

function deleteAllCookies() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i];
        var eqPos = cookie.indexOf("=");
        var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }
    		document.location.href = "/";
    
}

function checkUser() {
	if (cookieSaved()) {
		document.getElementById('loginBtn').hidden = true;
		document.getElementById('logout').hidden = false;
	} else {
		var a = document.getElementById('loginBtn').hidden = false;
		document.getElementById('uploadNav').href = "/";
		document.getElementById('viewAllNav').href = "/";
		document.getElementById('uploadNav').onclick = function(){alert("Press the login button first")}
		document.getElementById('viewAllNav').onclick = function(){alert("Press the login button first")}
		
	}
}


