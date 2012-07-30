// Created by Andrey Markelov 29-08-2012.
// Copyright Mail.Ru Group 2012. All rights reserved.

function addItem(baseUrl, projKey, cfKey) {
    var id = "#" + projKey + cfKey;
    var cfVal = AJS.$(id.replace(' ', '_')).val();

    if (!cfVal) {
        return;
    }

    JIRA.SmartAjax.makeRequest({
        url: baseUrl + "/rest/mailruselectsrv/1.0/mailruselectsrv/additem",
        type: "POST",
        dataType: "json",
        data: {"projKey": projKey, "cfKey": cfKey, "cfVal": cfVal},
        error: function(xhr, ajaxOptions, thrownError) {
            alert(xhr.responseText);
        },
        success: function(result) {
            window.location.reload();
        }
    });
}

function deleteItem(event, baseUrl, projKey, cfKey, cfVal) {
    event.preventDefault();
    JIRA.SmartAjax.makeRequest({
        url: baseUrl + "/rest/mailruselectsrv/1.0/mailruselectsrv/deleteitem",
        type: "POST",
        dataType: "json",
        data: {"projKey": projKey, "cfKey": cfKey, "cfVal": cfVal},
        error: function(xhr, ajaxOptions, thrownError) {
            alert(xhr.responseText);
        },
        success: function(result) {
            window.location.reload();
        }
    });
}
