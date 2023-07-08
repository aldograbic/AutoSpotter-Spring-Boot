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

var registeredYesRadio = document.getElementById("registeredYes");
var registeredNoRadio = document.getElementById("registeredNo");
var registeredDateInput = document.getElementById("registeredDateInput");
var registeredDate = document.getElementById("registeredDate");

registeredYesRadio.addEventListener("change", function() {
  if (this.checked) {
    registeredDateInput.style.display = "flex";
    registeredDate.required = true;
  } else {
    registeredDateInput.style.display = "none";
    registeredDate.required = false;
  }
});

registeredNoRadio.addEventListener("change", function() {
  if (this.checked) {
    registeredDateInput.style.display = "none";
    registeredDate.required = false;
    registeredDate.value = "1001-01-01";
  }
});