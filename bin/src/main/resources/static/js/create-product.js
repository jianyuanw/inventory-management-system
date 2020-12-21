function createSelect(element) {
	let prefix = element.id.substring(0, element.id.length - 6);
	let newElement = document.getElementById(prefix + "New");
	if (element.options[element.options.length - 1].selected == true) {
		newElement.classList.remove("d-none");
		newElement.value = "";
	} else {
		newElement.classList.add("d-none");
		newElement.value = "";
	}

}