package com.mk.moqui;
import org.moqui.context.ExecutionContext;

import java.io.IOException;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;  

public class sendConfirmationEmail {
  
  // Replace sender@example.com with your "From" address.
  // This address must be verified with Amazon SES.
  static final String FROM = "justin1020@gmail.com";

  // Replace recipient@example.com with a "To" address. If your account
  // is still in the sandbox, this address must be verified.
  // static final String TO = "jerome.shep@gmail.com";

  // The configuration set to use for this email. If you do not want to use a
  // configuration set, comment the following variable and the 
  // .withConfigurationSetName(CONFIGSET); argument below.
//   static final String CONFIGSET = "ConfigSet";

  // The subject line for the email.
  static final String SUBJECT = "Thank you for your donation to GND!";
  
  // The HTML body for the email.
  // static final String HTMLBODY = """<h3>Please confirm your email using the link below:</h1><p><a href="http://localhost:8080/apps/SESexample/emailConfirmed">http://localhost:8080/apps/SESexample/emailConfirmed</a></p>""";

  // The email body for recipients with non-HTML email clients.
  static final String TEXTBODY = "Guru Nanak Dwara thanks you for your generous donation!";

  public static Map<String, Object> sendEmail (ExecutionContext ec){
    

    String HTMLBODY = """<body style="width:500px; text-align:center;">

    <img src="gnd-logo.svg" alt="Guru Nanak Dwara logo" style="width:50px; margin-top:13px">

    <p style="margin-top:0; font-family: 'Raleway', sans-serif; font-size:16px;"> 
        Guru Nanak Dwara
    </p>

    <div style="background:#ED9757;">
        <h1 style="padding: 20px 20px ; font-family:'Noto Sans SC', sans-serif; font-size:26px; color:white; overflow-wrap:break-word; hypens:auto">
                ${ec.context.firstName}, you donated 
                <span style="display:block;"> 
                    \$${ec.context.donationAmount}! 
                </span>
        </h1>
    </div>
   

    <h3 style="margin-bottom:0; margin-top: 26px; font-family: 'Raleway', sans-serif; font-size:18px;">
        Donation details
    </h3>

    <div style="text-align:left; padding-left: 20px; font-family: 'Raleway', sans-serif;font-size:16px;">
        <p>Date: ${ec.context.stripeTimeStamp} </p>
        <p>Amount: \$${ec.context.donationAmount} </p>
        <p>Receipt No.: ${ec.context.stripeReceipt}</p>
        <p>${ec.context.stripeCardBrand} xxx${ec.context.stripeLast4}</p>
    </div>

    <img src="powered_by_stripe.png" alt="Powered by Stripe badge" style="width:75px; margin-bottom: 26px;">

    <div style="background:#959595; color:white; padding: 10px 10px; font-size:10px;">
        Guru Nanak Dwara is a tax-exempt 501c(3) non-profit charitable organization. Every generous donation is tax deductible in the USA.
    </div>

</body>""";


    try {
      AmazonSimpleEmailService client = 
          AmazonSimpleEmailServiceClientBuilder.standard()
          // Replace US_WEST_2 with the AWS Region you're using for
          // Amazon SES.
            .withRegion(Regions.US_WEST_2).build();
      SendEmailRequest request = new SendEmailRequest()
          .withDestination(
              new Destination().withToAddresses(ec.context.emailAddress.toLowerCase()))
          .withMessage(new Message()
              .withBody(new Body()
                  .withHtml(new Content()
                      .withCharset("UTF-8").withData(HTMLBODY))
                  .withText(new Content()
                      .withCharset("UTF-8").withData(TEXTBODY)))
              .withSubject(new Content()
                  .withCharset("UTF-8").withData(SUBJECT)))
          .withSource(FROM)
          // Comment or remove the next line if you are not using a
          // configuration set
        //   .withConfigurationSetName(CONFIGSET);
      def result = client.sendEmail(request);
      System.out.println("Email sent!");
      Map<String,Object> testResultHash = new HashMap <String, Object>()
      testResultHash.put("messageId", result.getMessageId())
      println("###########${testResultHash.messageId}");
      return testResultHash
    } catch (Exception ex) {
      System.out.println("The email was not sent. Error message: " 
          + ex.getMessage());
    }
  }
}