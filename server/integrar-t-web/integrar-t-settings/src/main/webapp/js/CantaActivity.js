function buildCantaConCaliSection(){
	var section = "<p>" + 
					"Nombre de la canción: " + 
					"<input id='songName' placeholder='Nombre de la canción' type='text'/>" +
					"<form id='formSoundCanta' action='' method='post' enctype='multipart/form-data' accept-charset='utf-8'>"+
					"<p>"+
					"Buscar una canción para subir: <input type='file' name='uploadedFile' size='50' accept='audio/*'/>"+
					"</p>"+
					"</form>"+
					"<input type='button' value='Enviar' onclick='sendCantaForms()'/>";
	return section;
};


function sendCantaForms(){
	var songName = $('#songName').val();
	setParams('formSoundCanta', 'soundUpload', 'cantaActivity', undefined, songName + "_sound.mp3");
	document.getElementById("formSoundCanta").submit();
	setTimeout(sendCantaData,1000);
};

function sendCantaData(){
	var songName = $('#songName').val();
	var data = new Object();
	data.user =  userName;
	data.name = songName;
	sendUpdateData('cantaActivity', data);
}