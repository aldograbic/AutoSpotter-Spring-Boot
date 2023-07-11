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

function loadManufacturers() {
  var selectedVehicleType = $('#vehicleType').val();
  if (selectedVehicleType === "") {
    $('#manufacturer').empty();
    $('#manufacturer').append('<option value="" disabled selected>Odabir</option>');
    $('#model').empty();
    $('#model').append('<option value="" disabled selected>Odabir</option>');
  } else {
    $.ajax({
      url: '/manufacturers',
      type: 'POST',
      data: selectedVehicleType,
      contentType: 'text/plain',
      success: function(response) {
        $('#manufacturer').empty();
        $('#manufacturer').append('<option value="" selected>Svi proizvođači</option>');
        response.forEach(function(manufacturer) {
          $('#manufacturer').append('<option value="' + manufacturer + '">' + manufacturer + '</option>');
        });
        $('#model').empty();
        $('#model').append('<option value="" disabled selected>Odabir</option>');
      },
      error: function(xhr, status, error) {
        // Handle error, if any
      }
    });
  }
}

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
        $('#model').append('<option value="" selected>Svi modeli</option>');
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


document.addEventListener("DOMContentLoaded", function () {
  const yearFromSelect = document.getElementById("yearFrom");
  const yearToSelect = document.getElementById("yearTo");
  const yearError = document.getElementById("yearError");

  yearFromSelect.addEventListener("change", validateYearRange);
  yearToSelect.addEventListener("change", validateYearRange);

  function validateYearRange() {
      const yearFrom = parseInt(yearFromSelect.value);
      const yearTo = parseInt(yearToSelect.value);

      if (yearTo < yearFrom) {
          yearError.textContent = "Druga odabrana godina mora biti veća od prve.";
          yearToSelect.classList.add("border", "border-red-500");
      } else {
          yearError.textContent = "";
          yearToSelect.classList.remove("border", "border-red-500");
      }
  }

  const priceFromSelect = document.getElementById("priceFrom");
  const priceToSelect = document.getElementById("priceTo");
  const priceError = document.getElementById("priceError");

  priceFromSelect.addEventListener("change", validatePriceRange);
  priceToSelect.addEventListener("change", validatePriceRange);

  function validatePriceRange() {
      const priceFrom = parseInt(priceFromSelect.value);
      const priceTo = parseInt(priceToSelect.value);

      if (priceTo < priceFrom) {
          priceError.textContent = "Druga odabrana cijena mora biti veća od prve.";
          priceToSelect.classList.add("border", "border-red-500");
      } else {
          priceError.textContent = "";
          priceToSelect.classList.remove("border", "border-red-500");
      }
  }
});
