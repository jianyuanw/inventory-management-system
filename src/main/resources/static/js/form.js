function cancel() {
	let elements = document.getElementsByTagName("input");
	for (let i = 0; i < elements.length; i++) {
		elements[i].value = "";
	}
}