function showManufacturers(odabir) {
    var manufacturerContainer = document.getElementById('manufacturer-container');
    manufacturerContainer.innerHTML = '';
  
    if (odabir === 'auto') {
        manufacturerContainer.innerHTML = '<h2 class="text-xl font-bold text-gray-700 mb-6 mt-6">Proizvođači automobila</h2>' +
                                       '<ul>' +
                                       '<li>Proizvođač 1</li>' +
                                       '<li>Proizvođač 2</li>' +
                                       '<li>Proizvođač 3</li>' +
                                       '</ul>';
    } else if (odabir === 'motor') {
        manufacturerContainer.innerHTML = '<h2 class="text-xl font-bold text-gray-700 mb-6 mt-6">Proizvođači motora</h2>' +
                                       '<ul>' +
                                       '<li>Proizvođač 4</li>' +
                                       '<li>Proizvođač 5</li>' +
                                       '<li>Proizvođač 6</li>' +
                                       '</ul>';
    } else if (odabir === 'kamion') {
        manufacturerContainer.innerHTML = '<h2 class="text-xl font-bold text-gray-700 mb-6 mt-6">Proizvođači kamiona</h2>' +
                                       '<ul>' +
                                       '<li>Proizvođač 7</li>' +
                                       '<li>Proizvođač 8</li>' +
                                       '<li>Proizvođač 9</li>' +
                                       '</ul>';
    } else if (odabir === 'dijelovi') {
        manufacturerContainer.innerHTML = '<h2 class="text-xl font-bold text-gray-700 mb-6 mt-6">Proizvođači dijelova</h2>' +
                                       '<ul>' +
                                       '<li>Proizvođač 10</li>' +
                                       '<li>Proizvođač 11</li>' +
                                       '<li>Proizvođač 12</li>' +
                                       '</ul>';
    }
}