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

    String amount = ec.context.DonationAmount.toString()
    println("@!@!@!@!@@!@!@!DONATION AMOUNT STRING IS ${amount}")
    Double result = Double.parseDouble(amount)*100
    Integer integerAmount = Math.round(result)
    String processedAmount = Integer.toString(integerAmount)

    customerParams.put("amount", processedAmount);
    customerParams.put("currency", "usd");
    customerParams.put("customer", ec.context.description);
    Charge charge = Charge.create(customerParams);
    return
  }

  public static recurringCharge(ExecutionContext ec){
    Stripe.apiKey = "sk_test_E557Je0PHQJjZKvGIQoa4Vw0";
    // When it's time to charge the customer again, retrieve the customer ID which is stored as the cardNumber on their associated credit card in Moqui.
    Map<String, Object> params = new HashMap<>();
    params.put("amount", 1500); // $15.00 this time
    params.put("currency", "usd");
    params.put("customer", ec.context.cardNumber); // Previously stored, then retrieved
    Charge charge = Charge.create(params);
  }
}