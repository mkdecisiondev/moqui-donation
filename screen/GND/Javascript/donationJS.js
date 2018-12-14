//Sets the value of the $Other radio button based on what user inputs in the input field that is on top of it
function setOtherAmount(){
    document.getElementById("otherAmountRadio").value = document.getElementById("otherAmtNumberInput").value 
    // console.log("VALUE::: " + document.getElementById("otherAmountRadio").value);   
}

//Checks the radio button when selecting the input field that is on top of it
function checkOtherRadio(){
    document.getElementById("otherAmountRadio").checked = true
    // console.log("RADIO CHECKED?::: " + document.getElementById("otherAmountRadio").checked);
}

//Changes CSS class of field to show validation UI styling
function addValidationErrorStyling(event){
    let inputField = document.getElementById(event.target.id)
    console.log(inputField.validity.valid);
    
    if(inputField.validity.valid){
        inputField.classList.remove("inputField")
        inputField.classList.remove("inputFieldInvalid")
        inputField.classList.add("inputFieldValid")
    } else {
        inputField.classList.remove("inputField")
        inputField.classList.remove("inputFieldValid")
        inputField.classList.add("inputFieldInvalid")
    }
}


