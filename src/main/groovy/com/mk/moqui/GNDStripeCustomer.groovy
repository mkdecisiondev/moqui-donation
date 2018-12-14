package com.mk.moqui;

import org.moqui.context.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import com.stripe.model.Customer

class GNDStripeCustomer {
  public Map<String, Object> createStripeCustomer(ExecutionContext ec) {
    // Set your secret key: remember to change this to your live secret key in production
    // See your keys here: https://dashboard.stripe.com/account/apikeys
    Stripe.apiKey = "sk_test_E557Je0PHQJjZKvGIQoa4Vw0";

    // Create a Customer:
    Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("source", ec.context.stripeToken);
    chargeParams.put("email", ec.context.emailAddress);
    Customer stripeCustomer = Customer.create(chargeParams);
    println("###########${stripeCustomer}#############")
    println("################${stripeCustomer.id}#########");
    return [stripeCustomerId:stripeCustomer.id]

  }

  public static chargeStripeCustomer(ExecutionContext ec){
    Stripe.apiKey = "sk_test_E557Je0PHQJjZKvGIQoa4Vw0";
    // Charge the Customer instead of the card:
    
    Map<String, Object> customerParams = new HashMap<>();

    String amount = ec.context.donationAmount
    // println("@!@!@!@!@@!@!@!DONATION AMOUNT STRING IS ${amount}")

    Double result = Double.parseDouble(amount)*100
    Integer integerAmount = Math.round(result)
    String processedAmount = Integer.toString(integerAmount)

    customerParams.put("amount", processedAmount);
    customerParams.put("currency", "usd");
    customerParams.put("customer", ec.context.description);
    Charge charge = Charge.create(customerParams);

    //Added code below to add data needed for email receipt to the execution context - data will be used with sendConfirmationEmail.groovy 

    Map<String, Object> receiptInfoMap = new HashMap<String, Object> ()

    String formattedDate = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date (charge.created*1000));

    receiptInfoMap.put("stripeLast4", charge.source.last4);
    receiptInfoMap.put("stripeCardBrand", charge.source.brand);
    receiptInfoMap.put("stripeTimeStamp", formattedDate);
    receiptInfoMap.put("stripeReceipt", charge.receiptNumber);
    // receiptInfoMap.put("stripeDonationAmount", );

    println("############# CHARGE INFO:##########");
    // println("${charge}") 
    println("LAST 4: ${charge.source.last4}")
    println("BRAND: ${charge.source.brand}")
    println("TIMESTAMP: ${formattedDate}")
    println("RECEIPT: ${charge.receiptNumber}")

    println("############ RECEIPTINFOMAP #############")
    println("LAST 4: ${receiptInfoMap.stripeLast4}")
    println("BRAND: ${receiptInfoMap.stripeCardBrand}")
    println("TIMESTAMP: ${receiptInfoMap.stripeTimeStamp}")
    println("RECEIPT: ${receiptInfoMap.stripeReceipt}")

    return receiptInfoMap

  }
}