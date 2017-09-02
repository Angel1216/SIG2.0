
function altaDocente(){

    var nombre = $('#txtNombre').val();
    var paterno = $('#txtPaterno').val();
    var materno = $('#txtMaterno').val();
    var grupo = $('#txtGrupo').val();

	var contact = '{'+
		'"nombre":"'+nombre+'",'+
		'"paterno":"'+paterno+'",'+
		'"materno":"'+materno+'",'+
		'"grupo":"'+grupo+'"'+
		'}';
	
    jQuery.ajax({
        type: "POST",
        url: "http://localhost:5055/altaRegistro",
        data: contact.toString(),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, status, jqXHR) {
            alert(data);
        },
        error: function (jqXHR, status) {            
        	alert(status);        	
        }
    });

}
