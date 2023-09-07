
function doLogin(){
  	
  	console.log(document.getElementById('user_id').value);
  	console.log(document.getElementById('user_pw').value);
  	
  	var data = {
		  user_id: document.getElementById('user_id').value,
		  user_pw: document.getElementById('user_pw').value
	  };
  
	$.ajax({
		type: "POST",
		url: "/project_education/api/v1/login.do",
		dataType: "text",
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(data)
	})
		.done(function (responseText) {
			alert("로그인 되었습니다.");
			window.location.href = "/project_education"+responseText+".do"
		})
		.fail(function (error) {
			alert(JSON.stringify(error.responseText));
		});
		
}

