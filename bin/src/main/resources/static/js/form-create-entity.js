function createSelect(element) {
	let prefix = element.id.substring(0, element.id.length - 6);
	let newElement = document.getElementById(prefix + "New");
	if (element.value == "0") {		
		newElement.classList.remove("d-none");
	} else {
		newElement.classList.add("d-none");
	}

}

function roundData(element) {
	element.value = Number(element.value).toFixed(2);
}