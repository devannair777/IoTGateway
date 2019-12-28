var timestep = 0;
function executeQuery() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/data/locn_3",
        contentType: "application/xml",
        dataType: "xml",
        success: function (xmlData) {
            console.log(xmlData);
            $("#temp").text($(xmlData).find("temperature").text());
            $("#hum").text($(xmlData).find("humidity").text());
            $("#lum").text($(xmlData).find("luminosity").text());
            timestep += 1;
            if(timestep >= 50)
            {
                clearTimeout(timer);
            }
        },
        error: function (xhr) {
            alert("Some Error");
        }

    });
    timer = setTimeout(executeQuery,500);
}
$(document).ready(function ()
    {
        timer = setTimeout(executeQuery,500);
    }
);