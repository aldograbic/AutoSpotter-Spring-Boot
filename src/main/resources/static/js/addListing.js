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

  loadModels();

  var registeredYesRadio = document.getElementById("registeredYes");
  var registeredNoRadio = document.getElementById("registeredNo");
  var registeredDateInput = document.getElementById("registeredDateInput");
  var registeredDate = document.getElementById("registeredDate");
  
  var previousRegisteredOption = "";
  
  // Retrieve the session value for the previousRegisteredOption
  var previousRegisteredOptionValue = "<%= session.getAttribute('previousRegisteredOption') %>";
  if (previousRegisteredOptionValue) {
      previousRegisteredOption = previousRegisteredOptionValue;
  }
  
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
  
  if (previousRegisteredOption === "Da") {
      registeredYesRadio.checked = true;
  } else if (previousRegisteredOption === "Ne") {
      registeredNoRadio.checked = true;
  }
  

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

  function updateSelectedOptions() {
    const selectedCheckboxes = document.querySelectorAll('input[name="safetyFeatures"]:checked');
  }
  
  document.addEventListener('alpine:init', () => {
    const checkboxes = document.querySelectorAll('input[name="safetyFeatures"]');
  
    checkboxes.forEach((checkbox) => {
      checkbox.addEventListener('change', updateSelectedOptions);
    });
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const hideModalTriggers = document.querySelectorAll('[data-modal-hide]');

  hideModalTriggers.forEach(function (trigger) {
      const targetModalId = trigger.getAttribute('data-modal-hide');
      const targetModal = document.getElementById(targetModalId);

      if (targetModal) {
          trigger.addEventListener('click', function () {
              targetModal.classList.add('hidden');
          });
      }
  });
});

let naslovnaAdded = false;

function previewImages(event) {
  const imagePreviewsContainer = document.getElementById("imagePreviews");

  const files = event.target.files;
  for (let i = 0; i < files.length; i++) {
      const file = files[i];
      const reader = new FileReader();

      reader.onload = function (e) {
          const imgContainer = document.createElement("div");
          imgContainer.classList.add("image-container");

          const img = document.createElement("img");
          img.src = e.target.result;
          img.classList.add("w-48", "h-48", "object-cover", "rounded-md", "border");

          img.addEventListener("click", function () {
              openEnlargedImg(e.target.result);
          });

          if (!naslovnaAdded && i === 0) {
              // Add border and "Naslovna" label to the first image
              imgContainer.classList.add("naslovna-image");
              const label = document.createElement("div");
              label.classList.add("naslovna-label");
              label.textContent = "Naslovna slika";
              imgContainer.appendChild(label);
              naslovnaAdded = true;
          }

          imgContainer.appendChild(img);
          imagePreviewsContainer.appendChild(imgContainer);
      };

      reader.readAsDataURL(file);
  }
}

function openEnlargedImg(imgSrc) {
  const modal = document.getElementById('defaultModal');
  const enlargedImg = document.getElementById('enlargedImg');

  enlargedImg.src = imgSrc;
  modal.classList.remove('hidden');
}