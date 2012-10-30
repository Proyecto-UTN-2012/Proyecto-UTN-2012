function buildPictogramSection(){
	var section = "<p>" + 
					"Nombre del Pictograma: " + 
					"<input id='pictogramName' placeholder='Nombre del Pictograma' type='text'/>" +
					"</p>" +
					"<p>" +
					"Selecciona los niveles: " +
					"<br/><input type='checkbox' id='level1'>Nivel 1</input>" +
					"<br/><input type='checkbox' id='level2'>Nivel 2</input>" +
					"<br/><input type='checkbox' id='level3'>Nivel 3</input>" +
//					"<select id='level'>" +
//						"<option value='1'>1</option>" +
//						"<option value='2'>2</option>" +
//						"<option value='3'>3</option>" +
//					"</select>" +
					"</p>" + 
					"<form id='formImagePictogram' action='' method='post' enctype='multipart/form-data' accept-charset='utf-8'>" +
					"<p>" +
					"Buscar un pictograma para subir: <input type='file' name='uploadedFile' size='50' accept='image/*'/>" +
					"</p>" +
					"</form>" +
					"<form id='formSoundPictogram' action='' method='post' enctype='multipart/form-data' accept-charset='utf-8'>"+
					"<p>"+
					"Buscar un sonido para subir: <input type='file' name='uploadedFile' size='50' accept='audio/*.mp3'/>"+
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
	setTimeout(sendPictogramData,100);
};

function sendPictogramData(){
	var pictogramName = $('#pictogramName').val();
	var data = new Object();
	data.user =  userName;
	var levels = [];
	if($('#level1')[0].checked) levels.push(1);
	if($('#level2')[0].checked) levels.push(2);
	if($('#level3')[0].checked) levels.push(3);
	data.levels = levels;
	data.name = pictogramName;
	sendUpdateData('pictogramActivity', data);
}