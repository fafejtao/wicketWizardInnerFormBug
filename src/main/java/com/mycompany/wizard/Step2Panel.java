package com.mycompany.wizard;

import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: fafejtao
 * Date: 4.3.13
 * Time: 16:07
 */
public class Step2Panel extends WizardStep {
    private static final Logger LOGGER = LoggerFactory.getLogger(Step2Panel.class);

    public Step2Panel(String title, String summary) {
        super(title, summary);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form innerForm = new Form("innerForm") {
            @Override
            protected void onSubmit() {
                LOGGER.info("WRONG: Here we are - Step2 - inner form!");
            }
        };

        add(innerForm);

        innerForm.add(new TextField<String>("text", new Model<String>()));
    }
}
