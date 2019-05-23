<#-- @ftlvariable name="description" type="java.lang.String" -->
<#-- @ftlvariable name="location" type="java.lang.String" -->
<#-- @ftlvariable name="timeValue" type="java.lang.String" -->
<#-- @ftlvariable name="missionName" type="java.lang.String" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<!doctype html public "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Fly me to Mars: a mission registration system.</title>

    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">

    <meta name="description" content="Rockets: a rocket information repository - Create Rocket">
</head>

<body>

<div id="title_pane">
    <h3>Delete Rocket</h3>
</div>

<p>${errorMsg!""}</p>

<div>
    <p>* Fields are required.</p>
</div>
<form name="delete_event" action="/rockets" method="POST">
    <div id="admin_left_pane" class="fieldset_without_border">
        <div><p>Delete Details</p></div>
        < <ul>
            <li>Name: ${rocket.name}</li>
            <li>Manufacturer:${rocket.manufacturer}</li>
            <li>Country: ${rocket.country}</li>s
        </ul>
    </div>

    <#if errorMsg?? && errorMsg?has_content>
        <div id="error">
            <p>Error: ${errorMsg}</p>
        </div>
    </#if>
    <div id="buttonwrapper">
        <button type="submit">Delete</button>
        <a href="/">Cancel</a>
    </div>
</form>

</body>