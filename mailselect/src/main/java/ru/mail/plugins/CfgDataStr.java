/*
 * Created by Andrey Markelov 29-08-2012.
 * Copyright Mail.Ru Groups 2012. All rights reserved.
 */
package ru.mail.plugins;

import java.util.HashMap;
import java.util.Map;

/**
 * This structure keeps list of projects that contains Mail.Ru select CF.
 * 
 * @author Andrey Markelov
 */
public class CfgDataStr
{
    /**
     * It is list of projects that contains Mail.Ru select CF. 
     */
    private Map<String, ProjCfs> projs;

    /**
     * Constructor.
     */
    public CfgDataStr()
    {
        projs = new HashMap<String, ProjCfs>();
    }

    public ProjCfs getProj(String projKey)
    {
        return projs.get(projKey);
    }

    public Map<String, ProjCfs> getProjs()
    {
        return projs;
    }

    public void putProj(String projKey, ProjCfs projCf)
    {
        projs.put(projKey, projCf);
    }

    @Override
    public String toString()
    {
        return "CfgDataStr(projs=" + projs + ")";
    }
}
