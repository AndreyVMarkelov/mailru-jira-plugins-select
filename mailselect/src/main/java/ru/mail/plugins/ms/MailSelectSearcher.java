/*
 * Created by Andrey Markelov 29-08-2012.
 * Copyright Mail.Ru Group 2012. All rights reserved.
 */
package ru.mail.plugins.ms;

import com.atlassian.jira.issue.customfields.searchers.ExactTextSearcher;
import com.atlassian.jira.issue.customfields.searchers.transformer.CustomFieldInputHelper;
import com.atlassian.jira.jql.operand.JqlOperandResolver;

/**
 * Searcher.
 * 
 * @author Andrey Markelov
 */
public class MailSelectSearcher
    extends ExactTextSearcher
{
    public MailSelectSearcher(
        JqlOperandResolver jqlOperandResolver,
        CustomFieldInputHelper customFieldInputHelper)
    {
        super(jqlOperandResolver, customFieldInputHelper);
    }
}
