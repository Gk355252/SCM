
  console.log('form-reset Script is loading...');

  const form = document.getElementById('registrationForm');
  const resetButton = document.getElementById('resetButton');

  if (form && resetButton) {
    console.log('Form and reset button are found.');

    resetButton.addEventListener('click', function (event) {
      event.preventDefault(); // Prevent the default reset behavior
      console.log('Reset button clicked');

      // Clear input fields using a targeted approach
      const inputElements = ['name', 'email', 'password', 'phone', 'about'];
      inputElements.forEach(function(elementId) {
        const element = document.getElementById(elementId);
        if (element) {
          element.value = '';
        }
      });

      // Clear validation error messages
      const errorElements = ['nameError','emailError', 'passwordError', 'phoneNumberError', 'aboutError'];
      errorElements.forEach(function(elementId) {
        const element = document.getElementById(elementId);
        if (element) {
          element.innerHTML = '';
          element.style.display = 'none'; // Hide the error message container
        }
      });

      // Remove error styling (text-red-600) from any fields with validation errors
      const errorFields = document.querySelectorAll('.text-red-600');
      errorFields.forEach(function (field) {
        console.log('Removing error styling from:', field);
        field.classList.remove('text-red-600');
      });

      // Manually trigger change event on inputs to update any bound data
      inputElements.forEach(function(elementId) {
        const element = document.getElementById(elementId);
        if (element) {
          element.dispatchEvent(new Event('change', { bubbles: true }));
        }
      });
    });
  } else {
    console.log('Form or reset button not found.');
  }





