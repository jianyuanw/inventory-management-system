$(document).ready(function() {
	document.getElementsByClassName("custom-select").forEach(updateUnits)
});

function updateUnits(element) {
	document.getElementById("productName").value = element.options[element.selectedIndex].getAttribute("data-name")
	document.getElementById("reorderQty").value = element.options[element.selectedIndex].getAttribute("data-quantity")
	document.getElementById("reorderDate").value = element.options[element.selectedIndex].getAttribute("data-date")
}