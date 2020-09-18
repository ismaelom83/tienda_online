function submitChangePassword(){
	var params = {};
	params["id"] = $("#id").val();
	params["currentPassword"] = $("#currentPassword").val();
	params["newPassword"] = $("#newPassword").val();
	params["confirmPassword"] = $("#confirmPassword").val();
	$.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/editarUsuario/cambiarPassword",
        data: JSON.stringify(params),
        dataType: 'text',
        cache: false,
        timeout: 600000,
        success: function (data) {
        	$("#changePasswordForm")[0].reset();
        	
        	$("#changePasswordError").addClass( "d-none" );
        	$("#formSuccess").removeClass( "d-none" );
			$("#formSuccess").html("Password Actualizado exitosmante!.");
            $('#changePasswordModal').modal('toggle');
            setTimeout(function(){	$("#formSuccess").hide('slow'); }, 2000);
        },
        error: function (e) {
            $("#changePasswordError").removeClass( "d-none" );
            $("#formSuccess").addClass( "d-none" );
			$("#changePasswordError").html(e.responseText);
        }
    });

}