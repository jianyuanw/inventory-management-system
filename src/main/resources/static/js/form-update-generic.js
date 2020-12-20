$(document).ready(function() {
	document.getElementsByClassName("selectpicker").forEach(updateUnits)
});

function updateUnits(element) {
	let units = element.options[element.selectedIndex].getAttribute("data-units");
	document.getElementById("quantity").value = Number(units);
	document.getElementById("quantity").max = units; 
}