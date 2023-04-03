package com.qycr.framework.boot.autoconfigure;


import com.qycr.framework.boot.office.excel.core.handler.selector.SolarExcelBox;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = SolarExcelProperties.SOLAR_EXCEL_PREFIX)
public class SolarExcelProperties {


    public static final String SOLAR_EXCEL_PREFIX= "solar.excel";

    /**
     * Whether to enable the solar Excel operation
     */
    private boolean enabled;



    /**
     * Sets the title and text for the prompt box . Prompt box is displayed when
     * the user selects a cell which belongs to this validation object . In
     * order for a prompt box to be displayed you should also use method
     * setShowPromptBox( boolean show )
     *  title The prompt box's title ,The prompt box's text
     */
    private PromptBox promptBox = new PromptBox();


    /**
     * Sets the title and text for the error box . Error box is displayed when
     * the user enters an invalid value int o a cell which belongs to this
     * validation object . In order for an error box to be displayed you should
     * also use method setShowErrorBox( boolean show )
     * title The error box's title , text The error box's text
     */
    private ErrorBox errorBox = new ErrorBox();


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public PromptBox getPromptBox() {
        return promptBox;
    }

    public void setPromptBox(PromptBox promptBox) {
        this.promptBox = promptBox;
    }

    public ErrorBox getErrorBox() {
        return errorBox;
    }

    public void setErrorBox(ErrorBox errorBox) {
        this.errorBox = errorBox;
    }

    public static  class PromptBox implements SolarExcelBox {

        private String title = "Filling instructions";

        private String text = "Enter the elements in the drop-down data set. Other elements cannot be imported";

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }
    public static class ErrorBox implements SolarExcelBox{

        private String title = "prompt";

        private String text = "Does not match the value of the cell";

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

}
