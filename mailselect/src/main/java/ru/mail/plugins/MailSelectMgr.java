/*
 * Created by Andrey Markelov 29-08-2012.
 * Copyright Mail.Ru Groups 2012. All rights reserved.
 */
package ru.mail.plugins;

import java.util.Set;

/**
 * Mail.Ru select list data manager.
 * 
 * @author Andrey Markelov
 */
public interface MailSelectMgr
{
    /**
     * Get Mail.Ru select CF values from Jira DB.
     */
    Set<String> getValues(String projKey, String cfName);

    /**
     * Put Mail.Ru select CF values to Jira DB.
     */
    void setValue(String projKey, String cfName, Set<String> vals);
}
