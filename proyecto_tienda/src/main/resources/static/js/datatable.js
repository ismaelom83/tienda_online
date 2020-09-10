	// Funcion sacada de la libreria de database para realizar paginaciones y filtrado de datos.
	$(document).ready(function () {
		$('#userList').dataTable( {
  "lengthMenu": [ [5, 10, 25, 50, -1], [5, 10, 25, 50, "All"] ],
  "language":idioma_espanol
} );
		});

			// Funcion sacada de la libreria de database para realizar paginaciones y filtrado de datos.
	$(document).ready(function () {
		$('#userList2').dataTable( {
  "lengthMenu": [ [5, 10, 25, 50, -1], [5, 10, 25, 50, "All"] ],
  "language":idioma_espanol
} );
		});


//esta variable es para cambiar el idima al español	de la libreria de datatable
		var idioma_espanol = {
    "sProcessing":     "Procesando...",
                "sLengthMenu":     "Mostrar _MENU_ registros",
                "sZeroRecords":    "No se encontraron resultados",
                "sEmptyTable":     "Ningún dato disponible en esta tabla =(",
                "sInfo":           " registros del _START_ al _END_ de _TOTAL_ ",
                "sInfoEmpty":      "registros del 0 al 0 de un total de 0 ",
                "sInfoFiltered":   "(filtrado de  de _MAX_ registros)",
                "sInfoPostFix":    "",
                "sSearch":         "Buscar:",
                "sUrl":            "",
                "sInfoThousands":  ",",
                "sLoadingRecords": "Cargando...",
                "oPaginate": {
                    "sFirst":    "Primero",
                    "sLast":     "Último",
                    "sNext":     "Siguiente",
                    "sPrevious": "Anterior"
                },
                "oAria": {
                    "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
                    "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                },
                "buttons": {
                    "copy": "Copiar",
                    "colvis": "Visibilidad"
                }
}