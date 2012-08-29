function buildPictogramSection(){
	var section = "<p>" + 
					"Nombre del Pictograma: " + 
					"<input id='pictogramName' placeholder='Nombre del Pictograma' type='text'/>" +
					"</p>" +
					"<p>" +
					"Selecciona un nivel: " +
					"<select id='level'>" +
						"<option value='1'>1</option>" +
						"<option value='2'>2</option>" +
						"<option value='3'>3</option>" +
					"</select>" +
					"</p>" + 
					"<form id='formImagePictogram' action='' method='post' enctype='multipart/form-data' accept-charset='utf-8'>" +
					"<p>" +
					"Buscar un pictograma para subir: <input type='file' name='uploadedFile' size='50' accept='image/*'/>" +
					"</p>" +
					"</form>" +
					"<form id='formSoundPictogram' action='' method='post' enctype='multipart/form-data' accept-charset='utf-8'>"+
					"<p>"+
					"Buscar un sonido para subir: <input type='file' name='uploadedFile' size='50' accept='audio/*'/>"+
					"</p>"+
					"</form>"+
					"<input type='button' value='Enviar' onclick='sendPictogramForms()'/>";
	return section;
};


function sendPictogramForms(){
	var pictogramName = $('#pictogramName').val();
	setParams('formImagePictogram', 'imageUpload', 'pictogramActivity', undefined, pictogramName + "_image.png");
	setParams('formSoundPictogram', 'soundUpload', 'pictogramActivity', undefined, pictogramName + "_sound.mp3");
	document.getElementById("formImagePictogram").submit();
	setTimeout(sendSound,100);
};

function sendSound(){
	document.getElementById("formSoundPictogram").submit();
	setTimeout(sendData,100);
};

function sendData(){
	var pictogramName = $('#pictogramName').val();
	var data = new Object();
	data.user =  $('#userId').val();
	data.level = $('#level').val();
	data.name = pictogramName;
	sendUpdateData('pictogramActivity', data);
}