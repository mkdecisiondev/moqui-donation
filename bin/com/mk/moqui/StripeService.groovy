package com.mk.moqui;

import org.moqui.context.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;

class StripeService {
      public static void getDonation(ExecutionContext ec) {
            Stripe.apiKey = "sk_test_E557Je0PHQJjZKvGIQoa4Vw0";
            Map<String, Object> params = new HashMap<String, Object>();
            
            String amount = ec.context.DonationAmount
            Double result = Double.parseDouble(amount)*100
            Integer integerAmount = Math.round(result)
            String processedAmount = Integer.toString(integerAmount)

            params.put("amount", processedAmount);
            params.put("currency", "usd");
            params.put("source", ec.context.stripeToken);
            params.put("receipt_email", "justin1020@gmail.com");
            Charge charge = Charge.create(params);
      }
}
