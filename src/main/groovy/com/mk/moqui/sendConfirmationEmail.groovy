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
    
    String HTMLBODY = """<h3>Guru Nanak Dwara thanks you!</h3>
    <p>
    Dear ${ec.context.firstName} ${ec.context.lastName}, thank you so much for your generous donation of \$${ec.context.DonationAmount}!</p>""";

    try {
      AmazonSimpleEmailService client = 
          AmazonSimpleEmailServiceClientBuilder.standard()
          // Replace US_WEST_2 with the AWS Region you're using for
          // Amazon SES.
            .withRegion(Regions.US_WEST_2).build();
      SendEmailRequest request = new SendEmailRequest()
          .withDestination(
              new Destination().withToAddresses(ec.context.emailAddress))
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