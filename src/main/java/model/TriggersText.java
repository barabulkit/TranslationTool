package model;

import util.TextUtil;

import java.util.Map;

public class TriggersText {

    String activatedByTrigger;
    String text;
    String activateTrigger;

    public TriggersText(String text) {
        Map<Integer, String> triggers = TextUtil.getTriggers(text);
        for(Integer triggerIndex : triggers.keySet()) {
            if(triggerIndex == 0) {
                activatedByTrigger = triggers.get(triggerIndex);
            } else {
                activateTrigger = triggers.get(triggerIndex);
            }
        }

        this.text = TextUtil.removeTriggers(text);
    }

    public String getActivatedByTrigger() {
        return activatedByTrigger;
    }

    public void setActivatedByTrigger(String activatedByTrigger) {
        this.activatedByTrigger = activatedByTrigger;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getActivateTrigger() {
        return activateTrigger;
    }

    public void setActivateTrigger(String activateTrigger) {
        this.activateTrigger = activateTrigger;
    }

    @Override
    public String toString() {
        String result = "";
        if(activatedByTrigger != null) {
            result = result + activatedByTrigger;
        }
        result = result + text;
        if(activateTrigger != null) {
            result = result + activateTrigger;
        }
        return result;
    }
}
