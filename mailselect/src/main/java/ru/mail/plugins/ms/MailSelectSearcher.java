/*
 * Created by Andrey Markelov 29-08-2012.
 * Copyright Mail.Ru Group 2012. All rights reserved.
 */
package ru.mail.plugins.ms;

import com.atlassian.jira.issue.customfields.searchers.TextSearcher;
import com.atlassian.jira.issue.customfields.searchers.transformer.CustomFieldInputHelper;
import com.atlassian.jira.jql.operand.JqlOperandResolver;
import com.atlassian.jira.web.FieldVisibilityManager;

/**
 * Searcher.
 * 
 * @author Andrey Markelov
 */
public class MailSelectSearcher
    extends TextSearcher
{
    public MailSelectSearcher(
        FieldVisibilityManager fieldVisibilityManager,
        JqlOperandResolver jqlOperandResolver,
        CustomFieldInputHelper customFieldInputHelper)
    {
        super(fieldVisibilityManager, jqlOperandResolver, customFieldInputHelper);
    }
}
