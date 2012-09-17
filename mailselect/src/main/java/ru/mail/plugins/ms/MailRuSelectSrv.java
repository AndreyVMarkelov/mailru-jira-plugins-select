/*
 * Created by Andrey Markelov 29-08-2012.
 * Copyright Mail.Ru Group 2012. All rights reserved.
 */
package ru.mail.plugins.ms;

import java.util.Set;
import java.util.TreeSet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.ComponentManager;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

/**
 * This service manages store CF values.
 * 
 * @author Andrey Markelov
 */
@Path("/mailruselectsrv")
public class MailRuSelectSrv
{
    /**
     * Logger.
     */
    private final Logger log = Logger.getLogger(MailRuSelectSrv.class);

    /**
     * Mail.Ru select manager.
     */
    private final MailSelectMgr msMgr;

    /**
     * Permission manager.
     */
    private final PermissionManager perMgr;

    /**
     * Project manager.
     */
    private final ProjectManager prMgr;

    /**
     * Constructor.
     */
    public MailRuSelectSrv(
        MailSelectMgr msMgr,
        PermissionManager perMgr,
        ProjectManager prMgr)
    {
        this.msMgr = msMgr;
        this.perMgr = perMgr;
        this.prMgr = prMgr;
    }

    @POST
    @Path("/additem")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addItem(@Context HttpServletRequest req)
    {
        User user = ComponentManager.getInstance().getJiraAuthenticationContext().getLoggedInUser();
        if (user == null)
        {
            log.error("MailRuSelectSrv::addItem - User is not logged");
            return Response.serverError().build();
        }

        String projKey = req.getParameter("projKey");
        String cfKey = req.getParameter("cfKey");
        String cfVal = req.getParameter("cfVal");

        if (projKey == null || cfKey == null || cfVal == null ||
            projKey.length() == 0 || cfKey.length() == 0 || cfVal.length() == 0)
        {
            log.error("MailRuSelectSrv::addItem - Incorrect parameters");
            return Response.serverError().build();
        }

        if (!projKey.equals(Consts.GROBAL_CF_PROJ))
        {
            Project proj = prMgr.getProjectObjByKey(projKey);
            if (proj == null)
            {
                log.error("MailRuSelectSrv::addItem - Project doesn't exist");
                return Response.serverError().build();
            }

            if (!perMgr.hasPermission(Permissions.ADMINISTER, user) &&
                !perMgr.hasPermission(Permissions.PROJECT_ADMIN, proj, user))
            {
                log.error("MailRuSelectSrv::addItem - User has no permissions");
                return Response.serverError().build();
            }
        }
        else
        {
            if (!perMgr.hasPermission(Permissions.ADMINISTER, user))
            {
                log.error("MailRuSelectSrv::addItem - User has no permissions");
                return Response.serverError().build();
            }
        }

        Set<String> vals = msMgr.getValues(projKey, cfKey);
        vals.add(cfVal);
        msMgr.setValue(projKey, cfKey, vals);

        return Response.ok().build();
    }

    @POST
    @Path("/deleteitem")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteItem(@Context HttpServletRequest req)
    {
        User user = ComponentManager.getInstance().getJiraAuthenticationContext().getLoggedInUser();
        if (user == null)
        {
            log.error("MailRuSelectSrv::deleteItem - User is not logged");
            return Response.serverError().build();
        }

        String projKey = req.getParameter("projKey");
        String cfKey = req.getParameter("cfKey");
        String cfVal = req.getParameter("cfVal");

        if (projKey == null || cfKey == null || cfVal == null ||
            projKey.length() == 0 || cfKey.length() == 0 || cfVal.length() == 0)
        {
            log.error("MailRuSelectSrv::deleteItem - Incorrect parameters");
            return Response.serverError().build();
        }

        if (!projKey.equals(Consts.GROBAL_CF_PROJ))
        {
            Project proj = prMgr.getProjectObjByKey(projKey);
            if (proj == null)
            {
                log.error("MailRuSelectSrv::deleteItem - Project doesn't exist");
                return Response.serverError().build();
            }

            if (!perMgr.hasPermission(Permissions.ADMINISTER, user) &&
                !perMgr.hasPermission(Permissions.PROJECT_ADMIN, proj, user))
            {
                log.error("MailRuSelectSrv::deleteItem - User has no permissions");
                return Response.serverError().build();
            }
        }
        else
        {
            if (!perMgr.hasPermission(Permissions.ADMINISTER, user))
            {
                log.error("MailRuSelectSrv::deleteItem - User has no permissions");
                return Response.serverError().build();
            }
        }

        Set<String> vals = msMgr.getValues(projKey, cfKey);
        vals.remove(cfVal);
        msMgr.setValue(projKey, cfKey, vals);

        return Response.ok().build();
    }

    @GET
    @Path("/getinfo")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getFieldInfo(@Context HttpServletRequest req)
    throws JSONException
    {
        User user = ComponentManager.getInstance().getJiraAuthenticationContext().getLoggedInUser();
        if (user == null)
        {
            log.error("MailRuSelectSrv::getFieldInfo - User is not logged");
            return Response.serverError().build();
        }

        String cfIdStr = req.getParameter("cfid");
        String project = req.getParameter("project");

        try
        {
            Long.parseLong(cfIdStr);
        }
        catch (NumberFormatException nex)
        {
            log.error("MailRuSelectSrv::getFieldInfo - Incorrect parameters");
            return Response.serverError().build();
        }

        Set<String> values = new TreeSet<String>();
        if (project == null || project.length() == 0)
        {
            values.addAll(msMgr.getValues(Consts.GROBAL_CF_PROJ, "customfield_" + cfIdStr));
        }
        else
        {
            values.addAll(msMgr.getValues(project, "customfield_" + cfIdStr));
        }

        JSONObject json = new JSONObject();
        json.put("values", values);

        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setNoStore(true);
        cc.setMaxAge(0);
        return Response.ok(values.toString()).cacheControl(cc).build();
    }
}
