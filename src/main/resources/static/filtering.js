var priceFrom = document.getElementById("priceFrom");
var priceTo = document.getElementById("priceTo");



$(document).ready(function() {
    $('#vehicleType').change(function() {
      loadManufacturers();
    });
    
    $('#manufacturer').change(function() {
      loadModels();
    });
});


priceFrom.addEventListener("input", validateInput);
priceTo.addEventListener("input", validateInput);

function validateInput(event) {
    var input = event.target;
    var value = input.value;
    var sanitizedValue = value.replace(/[^0-9]/g, '');
    input.value = sanitizedValue;
    
}
//dodat jos da nemre bit tocka, zarez i te pizdarije
  


function loadManufacturers() {
    var selectedVehicleType = $('#vehicleType').val();
    if (selectedVehicleType === "") {
        $('#manufacturer').empty();
        $('#manufacturer').append('<option value="" disabled selected>Odabir</option>');
    } else {
        $.ajax({
            url: '/manufacturers',
            type: 'POST',
            data: selectedVehicleType,
            contentType: 'text/plain',
            success: function(response) {
                $('#manufacturer').empty();
                $('#manufacturer').append('<option value="" disabled selected>Odabir</option>');
                response.forEach(function(manufacturer) {
                    $('#manufacturer').append('<option value="' + manufacturer + '">' + manufacturer + '</option>');
                });
            },
            error: function(xhr, status, error) {
                // Handle error, if any
            }
        });
    }
}

function loadModels() {
    var selectedManufacturer = $('#manufacturer').val();

    $.ajax({
        url: '/models',
        type: 'POST',
        data: selectedManufacturer,
        contentType: 'text/plain',
        success: function(response) {
            $('#model').html(response);
        },
        error: function(xhr, status, error) {
            // Handle error, if any
        }
    });
}

//funkcija da gumb bude kliknut