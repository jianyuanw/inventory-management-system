$(document).ready(function() {
	document.getElementsByClassName("selectpicker").forEach(updateUnits)
});

function updateUnits(element) {
	let units = element.options[element.selectedIndex].getAttribute("data-units");	
	document.getElementById("quantity").max = units; 
	document.getElementById("qoh").value = Number(units);	
	document.getElementById("mrq").value = element.options[element.selectedIndex].getAttribute("data-mrq")
	document.getElementById("rol").value = element.options[element.selectedIndex].getAttribute("data-rol")
	document.getElementById("itemId").value = element.options[element.selectedIndex].getAttribute("value")
}