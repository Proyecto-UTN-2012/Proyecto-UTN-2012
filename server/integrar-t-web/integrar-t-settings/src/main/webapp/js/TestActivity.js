function buildTestImageSection(){
	var section = "<form id=\"formImage\" action=\"\" method=\"post\" enctype=\"multipart/form-data\">" +
		"<p>" + 
			"Seleccione una imagen para subir: <input type=\"file\" name=\"uploadedFile\" size=\"50\" />" +
		"</p>" +

		"<input type=\"submit\" value=\"Upload It\" onclick=\"setParams('formImage', 'imageUpload', 'testActivity')\"/>" +
	"</form>";
	return section;
}

function buildTestSoundSection(){
	var section = "<form id=\"formSound\" action=\"\" method=\"post\" enctype=\"multipart/form-data\">" +
	"<p>" + 
	"Seleccione un sonido para subir:  <input type=\"file\" name=\"uploadedFile\" size=\"50\" />" +
	"</p>" +
	
	"<input type=\"submit\" value=\"Upload It\" onclick=\"setParams('formSound', 'soundUpload', 'testActivity')\"/>" +
	"</form>";
	return section;
}

function buildTestConsumeResourceSection(){
	return "<input type='button' onclick='getResources()' value='Consumir Recursos'/>";
}