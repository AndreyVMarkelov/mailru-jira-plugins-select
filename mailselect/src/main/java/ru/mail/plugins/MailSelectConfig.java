/*
 * Created by Andrey Markelov 29-08-2012.
 * Copyright Mail.Ru Group 2012. All rights reserved.
 */
package ru.mail.plugins;

import java.util.List;
import org.ofbiz.core.entity.GenericValue;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.ApplicationProperties;

/**
 * Mail.Ru select CF configuration.
 * 
 * @author Andrey Markelov
 */
public class MailSelectConfig
    extends JiraWebActionSupport
{
    /**
     * Unique ID.
     */
    private static final long serialVersionUID = 967833149365361046L;

    /**
     * Application properties.
     */
    private final ApplicationProperties applicationProperties;

    /**
     * Data.
     */
    private CfgDataStr cfgData;

    /**
     * Custom field manager.
     */
    private final CustomFieldManager cfMgr;

    /**
     * Mail.Ru select manager.
     */
    private final MailSelectMgr msMgr;

    /**
     * Permission manager.
     */
    private final PermissionManager perMgr;

    /**
     * Constructor.
     */
    public MailSelectConfig(
        CustomFieldManager cfMgr,
        ApplicationProperties applicationProperties,
        MailSelectMgr msMgr,
        PermissionManager perMgr)
    {
        this.cfMgr = cfMgr;
        this.applicationProperties = applicationProperties;
        this.msMgr = msMgr;
        this.perMgr = perMgr;
        this.cfgData = new CfgDataStr();

        List<CustomField> cgList = cfMgr.getCustomFieldObjects();
        for (CustomField cf : cgList)
        {
            if (cf.getCustomFieldType().getKey().equals("ru.mail.plugins.mailselect:mailru-select"))
            {
                List<GenericValue> projs = cf.getAssociatedProjects();
                for (GenericValue proj : projs)
                {
                    String projKey = (String) proj.get("key");
                    String projName = (String) proj.get("name");
                    String projDesc = (String) proj.get("description");
                    String projLead = (String) proj.get("lead");

                    if (!perMgr.hasPermission(Permissions.PROJECT_ADMIN, proj, getLoggedInUser()) &&
                        !perMgr.hasPermission(Permissions.ADMINISTER, getLoggedInUser()))
                    {
                        continue;
                    }

                    ProjCfs projCf;
                    if ((projCf = cfgData.getProj(projKey)) == null)
                    {
                        projCf = new ProjCfs(projKey, projName, projDesc, projLead);
                        cfgData.putProj(projKey, projCf);
                    }

                    MailCF mailCf = new MailCF(cf.getId(), cf.getNameKey(), cf.getName(), cf.getDescription(), msMgr.getValues(projKey, cf.getId()));
                    projCf.putCf(cf.getName(), mailCf);
                }
            }
        }
    }

    /**
     * Get context path.
     */
    public String getBaseUrl()
    {
        return applicationProperties.getBaseUrl();
    }

    public CfgDataStr getCfgData()
    {
        return cfgData;
    }

    public boolean hasAdminPermission()
    {
        User user = getLoggedInUser();
        if (user == null)
        {
            return false;
        }

        if (getPermissionManager().hasPermission(Permissions.ADMINISTER, getLoggedInUser()))
        {
            return true;
        }

        boolean isPerm = false;
        List<CustomField> cgList = cfMgr.getCustomFieldObjects();
        for (CustomField cf : cgList)
        {
            if (cf.getCustomFieldType().getKey().equals("ru.mail.plugins.mailselect:mailru-select"))
            {
                List<GenericValue> projs = cf.getAssociatedProjects();
                for (GenericValue proj : projs)
                {
                    if (!perMgr.hasPermission(Permissions.PROJECT_ADMIN, proj, getLoggedInUser()) &&
                        !perMgr.hasPermission(Permissions.ADMINISTER, getLoggedInUser()))
                    {
                        continue;
                    }
                    isPerm = true;
                }
            }
        }

        return isPerm;
    }
}
