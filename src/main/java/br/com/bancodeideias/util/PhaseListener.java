package br.com.bancodeideias.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

public class PhaseListener implements javax.faces.event.PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        event.getFacesContext().getExternalContext();
        System.out.println("AFTER: " + event.getPhaseId());
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        event.getFacesContext().getExternalContext();
        System.out.println("BEFORE: " + event.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

}
