//Checks the radio button when selecting the input field that is on top of it
function setOtherAmount(){
    document.getElementById("otherAmountRadio").value = document.getElementById("otherAmtNumberInput").value 

    // console.log("VALUE::: " + document.getElementById("otherAmountRadio").value);   
}

//Sets the value of the $Other radio button based on what user inputs in the input field that is on top of it
function checkOtherRadio(){
    document.getElementById("otherAmountRadio").checked = true

    console.log("RADIO CHECKED?::: " + document.getElementById("otherAmountRadio").checked);
}

