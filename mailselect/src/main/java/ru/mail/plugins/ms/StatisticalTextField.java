/*
 * Created by Andrey Markelov 29-08-2012.
 * Copyright Mail.Ru Group 2012. All rights reserved.
 */
package ru.mail.plugins.ms;

import com.atlassian.jira.issue.customfields.impl.TextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;

public class StatisticalTextField
    extends TextCFType
{
    /**
     * Constructor.
     */
    public StatisticalTextField(
        CustomFieldValuePersister customFieldValuePersister,
        GenericConfigManager genericConfigManager)
    {
        super(customFieldValuePersister, genericConfigManager);
    }
}
