/*
 * Created by Andrey Markelov 29-08-2012.
 * Copyright Mail.Ru Groups 2012. All rights reserved.
 */
package ru.mail.plugins;

import java.util.HashMap;
import java.util.Map;

/**
 * This structure keeps custom fields of a project.
 * 
 * @author Andrey Markelov
 */
public class ProjCfs
{
    /**
     * Custom fields.
     */
    private Map<String, MailCF> cfs;

    private String projDesc;

    private String projKey;

    private String projName;

    private String projLead;

    /**
     * Constructor.
     */
    public ProjCfs(String projKey, String projName, String projDesc, String projLead)
    {
        this.projKey = projKey;
        this.projName = projName;
        this.projDesc = projDesc;
        this.projLead = projLead;
        this.cfs = new HashMap<String, MailCF>();
    }

    public MailCF getCf(String cfName)
    {
        return cfs.get(cfName);
    }

    public String getProjName()
    {
        return projName;
    }

    public Map<String, MailCF> getCfs()
    {
        return cfs;
    }

    public String getProjDesc()
    {
        return projDesc;
    }

    public String getProjKey()
    {
        return projKey;
    }

    public String getProjLead()
    {
        return projLead;
    }

    public void putCf(String cfName, MailCF cf)
    {
        cfs.put(cfName, cf);
    }

   @Override
   public String toString()
   {
       return "ProjCfs [cfs=" + cfs + ", projDesc=" + projDesc + ", projKey="
           + projKey + ", projName=" + projName + ", projLead=" + projLead
           + "]";
    }
}
