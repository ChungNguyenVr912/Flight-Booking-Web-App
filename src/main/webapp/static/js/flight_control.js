function updateOptions2(selectedValue) {
    var select2 = document.getElementById("to");
    var options = select2.options;

    for (var i = 1; i < options.length; i++) {
        options[i].disabled = false;
    }

    if (selectedValue) {
        var optionToDisable = select2.querySelector("option[value='" + selectedValue + "']");
        if (optionToDisable) {
            optionToDisable.disabled = true;
        }
    }
}
function updateOptions1(selectedValue) {
    var select1 = document.getElementById("from");
    var options = select1.options;

    for (var i = 0; i < options.length; i++) {
        options[i].disabled = false;
    }

    if (selectedValue) {
        var optionToDisable = select1.querySelector("option[value='" + selectedValue + "']");
        if (optionToDisable) {
            optionToDisable.disabled = true;
        }
    }
}