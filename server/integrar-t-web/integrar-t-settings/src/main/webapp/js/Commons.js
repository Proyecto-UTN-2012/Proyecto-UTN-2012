function loadTags(){
	var accordion = $("#accordion");
	tags = 	[{title:"Test Image", bodyFunction: "buildTestImageSection"},
			 {title:"Test Sound", bodyFunction: "buildTestSoundSection"},
			 {title:"Test Get Resources", bodyFunction: "buildTestConsumeResourceSection"},
			 {title:"Agregar Pictograma", bodyFunction: "buildPictogramSection"},
			 {title:"Agregar Tarea", bodyFunction: "buildOrganizarTSection"}
			];
	for(var i in tags){
		accordion.append(
				"<section id='" + i + "'>" +
						"<h2><a href='#" + i + "'>" + tags[i].title + "</a></h2>" +
						"<div class='form'>" + window[tags[i].bodyFunction]() + "</div>" +
				"</section>");
	}
}

function setParams(formName, resourceType, activity, section, resourceName){
	section = (section == undefined)? "main" : section;
	resourceName = (resourceName == undefined)? "" : resourceName;
	var userId = $('#userId').val();
	var form = $("#" + formName);
	form.prop('action','rest/' + resourceType + '/' + userId + '/' + activity + '/' + section + "/" + resourceName);
};

function getResources(){
	var userId = $('#userId').val();
	var device = $('#device').val();
	$.ajax({
		type : 'GET',
		url : 'rest/updateRequest/'+userId+'/'+device+'/testActivity',
		success : function(activities){
			var div = $('#activities');
			for(var i=0;i<activities.length;i++){
				div.append('<p>http://localhost:8080/integrar-t-settings/statics/' + activities[i].path + '</p>');
			}
		}
	});
};

function onSubmitFile(){
	alert("La informacion se cargó correctamente");
};

function sendSignIn(){
	var user = new Object();
	user.userName = "pasutmarcelo@gmail.com";
	user.email = "pasutmarcelo@gmail.com";
	user.accountType = "google";
	user.token = "";
	alert(JSON.stringify(user));
	$.ajax({
		type : 'POST',
		url : 'rest/signIn',
		data : JSON.stringify(user) ,
		contentType : 'application/json',
		success : function() {
		}
	});
};

function sendUpdateData(activity, data){
	$.ajax({
		type : 'POST',
		url : 'rest/updateData/' + activity,
		data : JSON.stringify(data) ,
		contentType : 'application/json',
		success : onSubmitFile
	});	
};