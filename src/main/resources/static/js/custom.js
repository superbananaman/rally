function saveDetails() {
	var username = document.getElementById('name').value;
	var teamname = document.getElementById('team').value;
	document.cookie = "name=" + username.replace(/ /g, '');
	document.cookie = "team=" + teamname.replace(/ /g, '');
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

function checkUser() {
	if (cookieSaved()) {
		document.getElementById('loginBtn').hidden = true;
		document.getElementById('mainContent').innerHtml = "Welcome, " + window.getCookie('name');
	} else {
		var a = document.getElementById('loginBtn').hidden = false;
		document.getElementById('mainContent').innerHtml = "";
	}
}
$(document).ready(function () {
	$(document).on('submit', '#uploadForm', function () {
		var file = this.file.files[0];
		var itemName = this.childNodes[1].innerText
		var formData = new FormData();
		formData.append('file', file);
		$.ajax({
		    url: 'http://'+ window.location.host + '/store',
            type: "post",
            contentType: false,
            cache: false,
            enctype: "multipart/form-data",
			async: false,
			data: formData,
			processData:false,
			headers:{"item" : itemName}
		});
		window.location.href = 'http://'+ window.location.host + '/upload';
		return false;
	});
	
});