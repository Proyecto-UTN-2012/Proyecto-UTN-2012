function buildOrganizarTSection(){
	var section = "<p>" + 
					"Nombre de la tarea: " + 
					"<input id='taskName' placeholder='Nombre de la tarea' type='text'/>" +
					"</p>" +
					"<form id='formPictogram' action='' method='post' enctype='multipart/form-data' accept-charset='utf-8'>" +
					"<p>" +
					"Buscar un pictograma para subir: <input type='file' name='uploadedFile' size='50' accept='image/*'/>" +
					"</p>" +
					"</form>" +
					"<form id='formLargeImage' action='' method='post' enctype='multipart/form-data' accept-charset='utf-8'>"+
					"<p>"+
					"Buscar una imagen representativa para subir: <input type='file' name='uploadedFile' size='50' accept='image/*'/>"+
					"</p>"+
					"</form>"+
					"<form id='formSmallImage' action='' method='post' enctype='multipart/form-data' accept-charset='utf-8'>"+
					"<p>"+
					"Buscar una imagen representativa de tamaño icono para subir: <input type='file' name='uploadedFile' size='50' accept='image/*'/>"+
					"</p>"+
					"</form>"+
					"<input type='button' value='Enviar' onclick='sendCalendarForms()'/>";
	return section;
};


function sendCalendarForms(){
	var taskName = $('#taskName').val();
	setParams('formPictogram', 'imageUpload', 'calendarActivity', undefined, taskName + "_pictogram.png");
	setParams('formLargeImage', 'imageUpload', 'calendarActivity', undefined, taskName + "_large.png");
	setParams('formSmallImage', 'imageUpload', 'calendarActivity', undefined, taskName + "_small.png");
	document.getElementById("formPictogram").submit();
	setTimeout(sendLarge,100);
};

function sendLarge(){
	document.getElementById("formLargeImage").submit();
	setTimeout(sendSmall,100);
};

function sendSmall(){
	document.getElementById("formSmallImage").submit();
	setTimeout(sendData,100);
};

function sendData(){
	var taskName = $('#taskName').val();
	var data = new Object();
	data.user =  $('#userId').val();
	data.name = taskName;
	sendUpdateData('calendarActivity', data);
}