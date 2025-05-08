    $(document).ready(function() {
        $('#partiteTable').hide();

        $('#partiteTable').DataTable({
			responsive: true,
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.6/i18n/it-IT.json'
            }
        });

        setTimeout(function () {
            $('#partiteTable').fadeIn(300);
        }, 100);
    });