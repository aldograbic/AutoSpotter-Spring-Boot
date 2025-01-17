var priceFrom = document.getElementById("priceFrom");
var priceTo = document.getElementById("priceTo");

$(document).ready(function () {
  const queryParams = new URLSearchParams(window.location.search);
  const selectedVehicleType = queryParams.get('vehicleType');
  const selectedManufacturer = queryParams.get('manufacturer');
  const selectedModel = queryParams.get('vehicleModel');
  const selectedTransmission = queryParams.get('transmission');
  const selectedUserType = queryParams.get('userType');

  if (selectedTransmission) {
    $('#transmissionButtons button').removeClass('selected');

    switch (selectedTransmission) {
      case "Ručni":
        $('#transmissionManual').addClass('selected');
        break;
      case "Automatski":
        $('#transmissionAutomatic').addClass('selected');
        break;
      default:
        $('#transmissionAll').addClass('selected');
    }
  }

  if (selectedUserType) {
    $('#userTypeButtons button').removeClass('selected');
    switch (selectedUserType) {
      case "Privatni":
        $('#userTypePrivate').addClass('selected');
        break;
      case "Poslovni":
        $('#userTypeBusiness').addClass('selected');
        break;
      default:
        $('#userTypeAll').addClass('selected');
    }
  }

  $('input[type="reset"]').click(function () {
    $('.select2-basic').val('').trigger('change');
    $('.select2-withSearch').val('').trigger('change');

    selectTransmissionButton("transmissionAll");
    selectUserTypeButton("userTypeAll");
  });

  if (selectedVehicleType) {
    $('#vehicleType').val(selectedVehicleType);
    if (selectedVehicleType === "Automobil") {
      $('#bodyTypeSection').show();
      $('#bodyTypeSection').removeClass('hidden');
    } else if (selectedVehicleType === "Motocikl") {
      $('#motorcycleEngineType').show();
      $('#engineTypeSection').hide();
    }
    loadManufacturers(selectedVehicleType);
  }

  if (selectedManufacturer) {
    $('#manufacturer').val(selectedManufacturer);
    loadModels(selectedManufacturer);
  }

  if (selectedModel) {
    $('#model').val(selectedModel);
  }

  $('#vehicleType').change(function () {
    var selectedVehicleType = $('#vehicleType').val();
    
    if (selectedVehicleType === "Motocikl") {
      $('#bodyTypeSection').hide();
      $('#engineTypeSection').hide();
      $('#motorcycleEngineType').show();
    } else if (selectedVehicleType === "Automobil") {
      $('#bodyTypeSection').show();
      $('#engineTypeSection').show();
      $('#motorcycleEngineType').hide();
    } else {
      $('#bodyTypeSection').hide();
      $('#engineTypeSection').show();
      $('#motorcycleEngineType').hide();
    }
  
    loadManufacturers();
  
    const manufacturerParam = queryParams.get('manufacturer');
    if (manufacturerParam) {
      $('#manufacturer').val(manufacturerParam);
    }
  });
  
  $('#manufacturer').change(function () {
    loadModels();

    const modelParam = queryParams.get('model');
    if (modelParam) {
      $('#model').val(modelParam);
    }
  });
});

function sortListings(sortType) {
  const queryParams = new URLSearchParams(window.location.search);
  queryParams.set('sort', sortType);
  
  const newUrl = window.location.pathname + '?' + queryParams.toString();
  window.location.href = newUrl;
}

document.getElementById("transmissionButtons").addEventListener("click", function (event) {
  var buttonId = event.target.id;
  selectTransmissionButton(buttonId);
});

document.getElementById("userTypeButtons").addEventListener("click", function (event) {
  var buttonId = event.target.id;
  selectUserTypeButton(buttonId);
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
      success: function (response) {
        $('#manufacturer').empty();
        $('#manufacturer').append('<option value="" selected>Svi proizvođači</option>');
        response.forEach(function (manufacturer) {
          $('#manufacturer').append('<option value="' + manufacturer + '">' + manufacturer + '</option>');
        });
        $('#model').empty();
        $('#model').append('<option value="" disabled selected>Odabir</option>');
      },
      error: function (xhr, status, error) {
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
      success: function (response) {
        $('#model').empty();
        $('#model').append('<option value="" selected>Svi modeli</option>');
        response.forEach(function (model) {
          $('#model').append('<option value="' + model + '">' + model + '</option>');
        });
      },
      error: function (xhr, status, error) {
        // Handle error, if any
      }
    });
  }
}

function selectTransmissionButton(buttonId) {
  var buttons = document.querySelectorAll('#transmissionButtons button');
  buttons.forEach(function (button) {
    button.classList.remove('selected');
  });

  var selectedButton = document.getElementById(buttonId);
  selectedButton.classList.add('selected');

  var selectedTransmissionValue = "";

  switch (buttonId) {
    case "transmissionAll":
      selectedTransmissionValue = "";
      break;
    case "transmissionManual":
      selectedTransmissionValue = "Ručni";
      break;
    case "transmissionAutomatic":
      selectedTransmissionValue = "Automatski";
      break;
  }

  document.getElementById("selectedTransmission").value = selectedTransmissionValue;
}

document.getElementById("transmissionButtons").addEventListener("click", function (event) {
  var buttonId = event.target.id;
  selectTransmissionButton(buttonId);
});


function selectUserTypeButton(buttonId) {
  var buttons = document.querySelectorAll('#userTypeButtons button');
  buttons.forEach(function (button) {
    button.classList.remove('selected');
  });

  var selectedButton = document.getElementById(buttonId);
  selectedButton.classList.add('selected');

  var selectedUserTypeValue = "";

  switch (buttonId) {
    case "userTypePrivate":
      selectedUserTypeValue = "Privatni";
      break;
    case "userTypeBusiness":
      selectedUserTypeValue = "Poslovni";
      break;
    case "userTypeAll":
      selectedUserTypeValue = "";
      break;
  }

  document.getElementById("selectedUserType").value = selectedUserTypeValue;
}

document.getElementById("userTypeButtons").addEventListener("click", function (event) {
  var buttonId = event.target.id;
  selectUserTypeButton(buttonId);
});

document.addEventListener("DOMContentLoaded", function () {
  const yearFromSelect = $("#yearFrom");
  const yearToSelect = $("#yearTo");
  const yearError = $("#yearError");

  // Add event listeners for the 'select2:select' event
  yearFromSelect.on("select2:select", validateYearRange);
  yearToSelect.on("select2:select", validateYearRange);

  const submitBtn = $("#submitBtn");

  function validateYearRange() {
    const yearFrom = parseInt(yearFromSelect.val());
    const yearTo = parseInt(yearToSelect.val());

    if (yearTo < yearFrom) {
      yearError.text("Druga odabrana godina mora biti veća od prve.");
      yearToSelect.addClass("border", "border-red-500");

      submitBtn.prop("disabled", true);
      submitBtn.addClass("disabled:opacity-25");
    } else {
      yearError.text("");
      yearToSelect.removeClass("border", "border-red-500");
      submitBtn.prop("disabled", false);
      submitBtn.removeClass("disabled:opacity-25");
    }
  }


  const priceFromSelect = document.getElementById("priceFrom");
  const priceToSelect = document.getElementById("priceTo");
  const priceError = document.getElementById("priceError");

  const mileageFromSelect = document.getElementById("mileageFrom");
  const mileageToSelect = document.getElementById("mileageTo");
  const mileageError = document.getElementById("mileageError");

  priceFromSelect.addEventListener("change", validatePriceRange);
  priceToSelect.addEventListener("change", validatePriceRange);

  mileageFromSelect.addEventListener("input", validateMileageRange);
  mileageToSelect.addEventListener("input", validateMileageRange);

  function validatePriceRange() {
    const priceFrom = parseInt(priceFromSelect.value);
    const priceTo = parseInt(priceToSelect.value);

    if (priceTo < priceFrom) {
      priceError.textContent = "Druga odabrana cijena mora biti veća od prve.";
      priceToSelect.classList.add("border", "border-red-500");
      submitBtn.disabled = true;
      submitBtn.classList.add("disabled:opacity-25");

    } else {
      priceError.textContent = "";
      priceToSelect.classList.remove("border", "border-red-500");
      submitBtn.disabled = false;
      submitBtn.classList.remove("disabled:opacity-25");
    }
  }

  function validateMileageRange() {
    const mileageFrom = parseInt(mileageFromSelect.value);
    const mileageTo = parseInt(mileageToSelect.value);

    if (mileageTo < mileageFrom) {
      mileageError.textContent = "Druga odabrana kilometraža mora biti veća od prve.";
      mileageToSelect.classList.add("border", "border-red-500");
      submitBtn.disabled = true;
      submitBtn.classList.add("disabled:opacity-25");
    } else {
      mileageError.textContent = "";
      mileageToSelect.classList.remove("border", "border-red-500");
      submitBtn.disabled = false;
      submitBtn.classList.remove("disabled:opacity-25");
    }
  }
});