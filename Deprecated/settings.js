var count = 0;
function settingsScript() {
    $.ajax(
        {
            url:"http://localhost:8080/data/threshold",
            type:"GET",
            contentType:"application/xml",
            dataType:"xml",
            data:"<stateVariable>"+
                    "<temperature>"+""+"</temperature>"+
                    "<flash>"+""+"</flash>"+
                    "<humidity>"+""+"</humidity>"+
                    "</stateVariable>",
            success:function (data)
            {
                console.log("Settings Successfully Updated")
            }

        }
    )
}

$(document).ready()
{

}