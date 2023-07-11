$(document).ready(function() {
  $('#manufacturer').change(function() {
    loadModels();
  });

  function loadModels() {
    var selectedManufacturer = $('#manufacturer').val();
    if (selectedManufacturer === "") {
      $('#model').empty().append('<option value="" disabled selected>Odabir</option>');
    } else {
      $.ajax({
        url: '/models',
        type: 'POST',
        data: selectedManufacturer,
        contentType: 'text/plain',
        success: function(response) {
          $('#model').empty().append('<option value="" disabled selected>Odabir</option>');
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

var previousRegisteredOption = "";

registeredYesRadio.addEventListener("change", function() {
    if (this.checked) {
        registeredDateInput.style.display = "flex";
        registeredDate.required = true;

        if (previousRegisteredOption === "Ne") {
            registeredDate.value = "";
        }
    } else {
        registeredDateInput.style.display = "none";
        registeredDate.required = false;
    }

    previousRegisteredOption = "Da";
});

registeredNoRadio.addEventListener("change", function() {
    if (this.checked) {
        registeredDateInput.style.display = "none";
        registeredDate.required = false;

        if (previousRegisteredOption === "Da") {
            registeredDate.value = "1001-01-01";
        } else if (registeredDate.value === "") {
            registeredDate.value = "1001-01-01";
        }
    }

    previousRegisteredOption = "Ne";
});

  

  const selectedOptions = [];
                                  
 function toggleOption(value) {
   const index = selectedOptions.indexOf(value)
   if (index === -1) {
     selectedOptions.push(value)
   } else {
     selectedOptions.splice(index, 1)
   }
 }

  function validateInput(input) {
    input.value = input.value.replace(/,/g, '.');
  }
});