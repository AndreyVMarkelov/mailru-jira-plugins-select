/*
 * Created by Andrey Markelov 29-08-2012.
 * Copyright Mail.Ru Group 2012. All rights reserved.
 */
package ru.mail.plugins.ms;

import java.util.Set;

/**
 * This structure keeps Mail.Ru select CF data.
 * 
 * @author Andrey Markelov
 */
public class MailCF
{
    private String id;

    private String descr;

    private String key;

    private String name;

    private Set<String> vals;

    public MailCF(String id, String key, String name, String descr, Set<String> vals)
    {
        this.id = id;
        this.key = key;
        this.name = name;
        this.descr = descr;
        this.vals = vals;
    }

    public String getDescr()
    {
        return descr;
    }

    public String getId()
    {
        return id;
    }

    public String getKey()
    {
        return key;
    }

    public String getName()
    {
        return name;
    }

    public Set<String> getVals()
    {
        return vals;
    }

    @Override
    public String toString()
    {
        return "MailCF[id=" + id + ", key=" + key + ", name=" + name + ", descr=" + descr + ", vals=" + vals + "]";
    }
}
