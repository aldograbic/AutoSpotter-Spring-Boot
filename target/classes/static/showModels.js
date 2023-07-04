$(document).ready(function() {
    $('#manufacturer').change(function() {
      loadModels();
    });
  });

function loadModels() {
    var selectedManufacturer = $('#manufacturer').val();
    if (selectedManufacturer === "") {
      $('#model').empty();
      $('#model').append('<option value="" disabled selected>Odabir</option>');
    } else {
      $.ajax({
        url: '/models',
        type: 'POST',
        data: selectedManufacturer,
        contentType: 'text/plain',
        success: function(response) {
          $('#model').empty();
          $('#model').append('<option value="" disabled selected>Odabir</option>');
          response.forEach(function(model) {
            $('#model').append('<option value="' + model + '">' + model + '</option>');
          });
        },
        error: function(xhr, status, error) {
          // Handle error, if any
        }
      });
    }
}