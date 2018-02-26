package com.sivitsky.service;

import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;
import com.sparkpost.model.*;
import com.sparkpost.model.responses.Response;
import com.sparkpost.resources.ResourceTransmissions;
import com.sparkpost.transport.RestConnection;

import java.util.ArrayList;
import java.util.List;

public class SparkPostClient extends Client {

    public SparkPostClient(String key) {
        super.setAuthKey(key);
    }

    public Response sendMessage(String from, List<String> recipients, String subject, String text, String html) throws SparkPostException {
        TransmissionWithRecipientArray transmission = new TransmissionWithRecipientArray();

        List<RecipientAttributes> recipientArray = new ArrayList<RecipientAttributes>();
        for (String recpient : recipients) {

            RecipientAttributes recipientAttribs = new RecipientAttributes();
            recipientAttribs.setAddress(new AddressAttributes(recpient));
            recipientArray.add(recipientAttribs);
        }
        transmission.setRecipientArray(recipientArray);

        TemplateContentAttributes contentAttributes = new TemplateContentAttributes();

        contentAttributes.setFrom(new AddressAttributes(from));

        contentAttributes.setSubject(subject);
        contentAttributes.setHtml(html);
        contentAttributes.setText(text);
        transmission.setContentAttributes(contentAttributes);

        OptionsAttributes options = new OptionsAttributes();
        options.setSandbox(true);
        transmission.setOptions(options);

        RestConnection connection = new RestConnection(this);
        Response response = ResourceTransmissions.create(connection, 0, transmission);

        return response;
    }

}
