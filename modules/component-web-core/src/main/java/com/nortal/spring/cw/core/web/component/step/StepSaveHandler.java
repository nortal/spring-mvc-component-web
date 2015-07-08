package com.nortal.spring.cw.core.web.component.step;

import java.io.Serializable;

/**
 * 
 * @author Alrik Peets
 * 
 */
public interface StepSaveHandler extends Serializable {

   void saveStep(int stepNr);

}
