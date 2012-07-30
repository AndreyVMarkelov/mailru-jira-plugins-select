/*
 * Created by Andrey Markelov 29-08-2012.
 * Copyright Mail.Ru Group 2012. All rights reserved.
 */
package ru.mail.plugins;

import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

/**
 * Inmplementation of <code>MailSelectMgr</code>.
 * 
 * @author Andrey Markelov
 */
public class MailSelectMgrImpl
    implements MailSelectMgr
{
    /**
     * Plugin data key.
     */
    private static final String PLUGIN_KEY = "mailru_select";

    /**
     * Value separator.
     */
    private static final String VAL_SEPARATOR = "&&";

    /**
     * Plug-In settings factory.
     */
    private final PluginSettingsFactory pluginSettingsFactory;

    /**
     * Constructor.
     */
    public MailSelectMgrImpl(PluginSettingsFactory pluginSettingsFactory)
    {
        this.pluginSettingsFactory = pluginSettingsFactory;
    }

    private String createPropKey(String projKey, String cfName)
    {
        return (projKey + VAL_SEPARATOR + cfName);
    }

    private String getStringProperty(String key)
    {
        return (String) pluginSettingsFactory.createSettingsForKey(PLUGIN_KEY).get(key);
    }

    @Override
    public Set<String> getValues(String projKey, String cfName)
    {
        Set<String> vals = new TreeSet<String>();

        String val = getStringProperty(createPropKey(projKey, cfName));
        if (val != null && val.length() > 0)
        {
            StringTokenizer st = new StringTokenizer(val, VAL_SEPARATOR);
            while(st.hasMoreTokens())
            {
                vals.add(st.nextToken());
            }
        }

        return vals;
    }

    private void setStringProperty(String key, String value)
    {
        pluginSettingsFactory.createSettingsForKey(PLUGIN_KEY).put(key, value);
    }

    @Override
    public void setValue(String projKey, String cfName, Set<String> vals)
    {
        StringBuilder sb = new StringBuilder();
        if (vals != null)
        {
            for (String val : vals)
            {
                sb.append(val).append("&");
            }
        }

        setStringProperty(createPropKey(projKey, cfName), sb.toString());
    }
}
