function saveDetails(){
	localStorage.setItem("name", document.getElementById('name').value);
	localStorage.setItem("team", document.getElementById('team').value);
	window.location("index.html");
}

function checkUser(){
	if(localStorage.getItem("name") === null){
		var a = document.createElement('a');
		a.setAttribute('href', 'login.html');
		a.setAttribute('class', 'navbar-link');
		a.innerHTML = 'Login';
		document.getElementById('user').appendChild(a);
	}
	else{
		document.getElementById('user').textContent = "Welcome, "+ localStorage.getItem("name");
	}
}

