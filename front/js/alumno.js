

function consultaDocente(){
	
    jQuery.ajax({
        type: "POST",
        url: "http://localhost:5056/obtenerProfesores",
        success: function (data, status, jqXHR) {

                $("#selectDocente").empty()
                $("#selectDocente").append("<option value=0>--Seleccionar--</option>");
                for (var i = 0; i < data.length; i++) {
                      $("#selectDocente").append("<option value=" + data[i].idDocente + ">" + data[i].docente + "</option>");
                 }
                
                $("#selectDocente").trigger("chosen:updated");

        },
    
        error: function (jqXHR, status) {            
        	alert(status);        	
        }
    });

}


function filePDF(){

    var json = '{'+
                    '"idHorario":"'+$('#selectHorario').val()+'.pdf"'+
                '}';

    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'http://localhost:5056/obtenerHorario', true);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.responseType = 'blob';

    xhr.onload = function(e) {
      if (this.status == 200) {
        var blob = new Blob([this.response], {type: 'application/octet-stream'}),
            url = URL.createObjectURL(blob),
            _iFrame = document.createElement('iframe');

        _iFrame.setAttribute('src', url);
        _iFrame.setAttribute('style', 'visibility:hidden;');
        $('#mainPDF').append(_iFrame)        
      }
    };

    xhr.send(JSON.stringify(eval('('+json+')')));

}
