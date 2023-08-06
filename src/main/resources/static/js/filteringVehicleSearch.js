$(document).ready(function () {
  $('#vehicleType').change(function () {
    var selectedVehicleType = $('#vehicleType').val();
    if (selectedVehicleType === "Automobil") {
      $('#bodyTypeSection').show();
    } else {
      $('#bodyTypeSection').hide();
    }

    loadManufacturers();
  });

  $('#manufacturer').change(function () {
    loadModels();
  });
});


function loadManufacturers() {
  var selectedVehicleType = $('#vehicleType').val();
  if (selectedVehicleType === "") {
    $('#manufacturer').empty();
    $('#manufacturer').append('<option value="" disabled selected>Odabir</option>');
    $('#model').empty();
    $('#model').append('<option value="" disabled selected>Odabir</option>');
  } else {
    $.ajax({
      url: '/manufacturersSearch',
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
      url: '/modelsSearch',
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