package com.mycompany.wizard;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.wizard.*;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: fafejtao
 * Date: 5.3.13
 * Time: 8:59
 */
public class WizardPage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        WizardModel wizardModel = new WizardModel();
        wizardModel.add(new Step1Panel("Step 1", "Step 1 summary"));
        wizardModel.add(new Step2Panel("Step 2", "Step 2 summary"));
        wizardModel.add(new Step3Panel("Step 3", "Step 3 summary"));

        add(new Wizard("wizard", wizardModel) {
            @Override
            protected Component newButtonBar(String id) {
                return new MyWizardButtonBar(id, this);
            }
        });
    }

    /*
     * Wizard buttons override ...
     * see org.apache.wicket.extensions.wizard.WizardButtonBar cannot extend
     */
    private static class MyWizardButtonBar extends Panel implements IDefaultButtonProvider {
        private static final Logger LOGGER = LoggerFactory.getLogger(MyWizardButtonBar.class);

        public MyWizardButtonBar(final String id, final IWizard wizard) {
            super(id);

            add(new PreviousButton("previous", wizard));
            add(new NextButton("next", wizard) {
                @Override
                public void onClick() {
                    LOGGER.info("HERE WE ARE - next button.");
                    // getForm().process(null); // innerForm.onSubmit() from Step2Panel is called twice
                    super.onClick();
                }
            });
            add(new LastButton("last", wizard));
            add(new CancelButton("cancel", wizard));
            add(new FinishButton("finish", wizard));
        }


        /**
         * @see org.apache.wicket.extensions.wizard.IDefaultButtonProvider#getDefaultButton(org.apache.wicket.extensions.wizard.IWizardModel)
         */
        @Override
        public IFormSubmittingComponent getDefaultButton(final IWizardModel model) {
            if (model.isNextAvailable()) {
                return (IFormSubmittingComponent) get("next");
            } else if (model.isLastAvailable()) {
                return (IFormSubmittingComponent) get("last");
            } else if (model.isLastStep(model.getActiveStep())) {
                return (IFormSubmittingComponent) get("finish");
            }
            return null;
        }
    }
}
