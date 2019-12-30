function setActState()
{
    $.ajax(
        {
            url:"http://localhost:8080/data/isAct",
            type:"POST",
            contentType: "application/xml",
            dataType: "xml",
            data:"<isAct><actEnable>" + "true" + "</actEnable></isAct>",
            success:function () {
                console.log("Successfully Posted")
                $("#msgBox").hide();
            }
        }
    )
}
$(document).ready(function ()
    {
        //setActState()
    }
);
