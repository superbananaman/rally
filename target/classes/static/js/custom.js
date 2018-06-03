function saveDetails(){
	var username = document.getElementById('name').value;
	var teamname = document.getElementById('team').value;
  	document.cookie = "name="+username.replace(/ /g,'');
  	document.cookie = "team="+teamname.replace(/ /g,'');
}

window.getCookie = function(name) {
  var match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
  if (match) return match[2];
}

function cookieSaved(){
  var regexp = new RegExp("(?:^" + "name" + "|;\s*"+ "name" + ")=(.*?)(?:;|$)", "g");
  var result = regexp.exec(document.cookie);
  return (result === null) ? false : true;
}

function checkUser(){
	if(cookieSaved()===false){
		var a = document.createElement('a');
		a.setAttribute('href', 'login.html');
		a.setAttribute('class', 'navbar-link');
		a.innerHTML = 'Login';
		document.getElementById('user').appendChild(a);
	}
	else{
		document.getElementById('user').textContent = "Welcome, "+ window.getCookie('name');
	}
}

