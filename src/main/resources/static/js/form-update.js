$(document).ready(function() {
	let elements = document.getElementsByClassName("selectpicker");
	
	for (let i = 0; i < elements.length; i++) {
		updateUnits(elements[i]);
	}
});

function updateUnits(element) {
	let units = element.options[element.selectedIndex].getAttribute("data-units");
	let index = element.id.substring(6);
	let unitElement = document.getElementById("units" + index);
	if (unitElement.value == 0) {
		unitElement.value = units;
	}
	unitElement.max = units;
	let transactionElement = document.getElementById("transaction" + index);
	transactionElement.value = element.options[element.selectedIndex].getAttribute("value");
}