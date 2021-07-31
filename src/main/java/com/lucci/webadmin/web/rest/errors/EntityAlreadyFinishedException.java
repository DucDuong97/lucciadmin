package com.lucci.webadmin.web.rest.errors;

import com.lucci.webadmin.web.rest.errors.BadRequestAlertException;
import com.lucci.webadmin.web.rest.errors.ErrorConstants;

public class EntityAlreadyFinishedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EntityAlreadyFinishedException() {
        super(ErrorConstants.ENTITY_ALREADY_FINISHED_TYPE, "This entity is already finished!", "treatment", "entityFinished");
    }
}
