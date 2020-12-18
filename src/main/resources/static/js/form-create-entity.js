function createSelect(element) {
	let prefix = element.id.substring(0, element.id.length - 6);
	let newElement = document.getElementById(prefix + "New");
	if (element.value == "create") {		
		newElement.classList.remove("d-none");
	} else {
		newElement.classList.add("d-none");
	}

}

function updateSelect(element) {
	let prefix = element.id.substring(0, element.id.length - 3);
	document.getElementById(prefix + "Select").value = element.value;
}