// //Radio buttons have same name
// //other has different name

// //Used by radio buttons
// function clearOtherAmount(){
//     document.getElementById('amount').value = ''
// }

// //used by $other button/input
// function selectOtherRadioButton(){
//     document.getElementById("rdo-amount-other").checked = true
// }

// function focusOnOtherAmount(){
//     const otherAmount = document.getElementById("amount")
//     otherAmount.focus()
//     otherAmount.select()
// }


function checkDonationAmount(){
   let x = document.getElementsByName("DonationAmount")
   
   console.log("Elements " + document.getElementsByName("DonationAmount"));
   
   console.log("Elements value = " + x);
   
   let y = document.getElementById("amountBox1")

   console.log(y);
   console.log(y.value);
   
}


function setOtherAmount(){
    document.getElementById("otherAmountRadio").value = document.getElementById("otherAmtNumberInput").value 

    // console.log("VALUE::: " + document.getElementById("otherAmountRadio").value);   
}

function checkOtherRadio(){
    document.getElementById("otherAmountRadio").checked = true

    // console.log("RADIO CHECKED?::: " + document.getElementById("otherAmountRadio").checked);
}

