var priceFrom = document.getElementById("priceFrom");
var priceTo = document.getElementById("priceTo");

document.getElementById("transmissionButtons").addEventListener("click", function(event) {
  var buttonId = event.target.id;
  selectTransmissionButton(buttonId);
});

document.getElementById("userTypeButtons").addEventListener("click", function(event) {
  var buttonId = event.target.id;
  selectUserTypeButton(buttonId);
});

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
  var sanitizedValue = value.replace(/[^0-9]/g, "");
  input.value = sanitizedValue;
}
// Additional validation to disallow dots, commas, and other characters

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

function selectTransmissionButton(buttonId) {
    var buttons = document.querySelectorAll('#transmissionButtons button');
    buttons.forEach(function(button) {
      button.classList.remove('selected');
    });
  
    var selectedButton = document.getElementById(buttonId);
    selectedButton.classList.add('selected');
  }
  
  function selectUserTypeButton(buttonId) {
    var buttons = document.querySelectorAll('#userTypeButtons button');
    buttons.forEach(function(button) {
      button.classList.remove('selected');
    });
  
    var selectedButton = document.getElementById(buttonId);
    selectedButton.classList.add('selected');
}