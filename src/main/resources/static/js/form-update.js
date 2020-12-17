$(document).ready(function() {
	document.getEleme
	let elements = document.getElementsByClassName("selectpicker");
	
	for (let i = 0; i < elements.length; i++) {
		if (elements[i].selectedIndex == null) {
			elements[i].selectedIndex = "0";
		}
		updateUnits(elements[i]);
	}
});

function updateUnits(element) {
	let units = element.options[element.selectedIndex].getAttribute("data-units");
	let index = element.id.substring(6);
	let unitElement = document.getElementById("units" + index);
	unitElement.value = units;
	unitElement.max = units;
}