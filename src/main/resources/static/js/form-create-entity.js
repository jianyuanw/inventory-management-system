$(document).ready(function() {
	let elements = document.getElementsByClassName("custom-select");
	
	for (let i = 0; i < elements.length; i++) {
		createSelect(elements[i]);
	}	
});

function createSelect(element) {
	let prefix = element.id.substring(0, element.id.length - 6);
	let newElement = document.getElementById(prefix + "New");
	let errorElement = document.getElementById(prefix + "NewError")
	if (element.value == "0") {
		newElement.value = "";		
		newElement.classList.remove("d-none");
		if (errorElement != null) {
		    errorElement.classList.remove("d-none");	
		}
		if (element.id == "categorySelect") {
			updateSubcategories(element.value);
		}
		
	} else {
		newElement.classList.add("d-none");		
		newElement.value = "placeholder";
		if (errorElement != null) {
			errorElement.classList.add("d-none");
		}
		if (element.id == "categorySelect") {
			updateSubcategories(element.value);
		}		
		
	}

}

function roundData(element) {
	element.value = Number(element.value).toFixed(2);
}

function updateSubcategories(categoryId) {
	let subcategory = document.getElementById("subcategorySelect");
	for (let i = 1; i < subcategory.length; i++) {
		if (subcategory.options[i].getAttribute("data-category") != categoryId) {
			subcategory.options[i].classList.add("d-none");
		} else {
			subcategory.options[i].classList.remove("d-none");
		}
	}
	
}