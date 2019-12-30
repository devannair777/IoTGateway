var count = 0;
function getGLobalStates()
{
    $.ajax(
        {
            url : "http://localhost:8080/data/globalStates",
            type: "GET",
            contentType: "application/xml",
            dataType: "xml",
            success:function (data)
            {
                var card_ids = ['card3','card2','card1']
                console.log(data);
                var i = 0;
                var fireLocation = "";

                $(data).find('globalStates localStates').each(
                    function(){

                        var location = $(this).find('location').text();
                        var temp = $(this).find('temperature').text();
                        var hum = $(this).find('humidity').text();
                        var flash = $(this).find('flash').text();
                        var actState =  $(this).find("actuatorState").text();
                        var isFire = $(this).find("fire").text();



                        if(isFire == "true" && count == 0)
                        {
                            fireLocation = location;
                            $("#msgBox").show();
                            $("#msgBox").find("#roomName").text(location);
                            count = 1;

                        }

                        $("#alarm").find("#alarmstate").text(actState);
                        /*console.log(location);
                        console.log(temp);
                        console.log(flash);
                        console.log("Count : "+count);

                        console.log(card_ids[i]);*/

                        $('#'+card_ids[i]).find('#loc_id').text(location);
                        $('#'+card_ids[i]).find('#temp').text(temp);
                        $('#'+card_ids[i]).find('#hum').text(hum);
                        $('#'+card_ids[i]).find('#flash').text(flash);

                        i = i + 1;
                    }
                )
            }
        }
    )

    setTimeout(getGLobalStates,500)
}

$(document).ready(function ()
    {
        count = 0;
        $("#msgBox").hide(),
        timer = setTimeout(getGLobalStates,500);
    }
);
