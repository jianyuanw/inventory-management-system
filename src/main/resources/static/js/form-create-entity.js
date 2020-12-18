function createSelect(element) {
	let prefix = element.id.substring(0, element.id.length - 6);
	var selector = document.getElementById(prefix+"Select");
	let newElement = document.getElementById(prefix + "New");
	if (selector.options[selector.options.length - 1].selected == true) {		
		newElement.classList.remove("d-none");
	} else {
		newElement.classList.add("d-none");
	}

}