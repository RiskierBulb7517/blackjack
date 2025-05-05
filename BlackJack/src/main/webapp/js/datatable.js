$(document).ready(function(){

	
	
	$('table').hide();
	
	$('table').each(function(){
	
			$(this).DataTable({
      language: {
        url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/it-IT.json' // Traduzione italiana
      }
    });
		}
	);
	
	setTimeout(function(){
		$('table').fadeIn(300);
	}, 100);
});
