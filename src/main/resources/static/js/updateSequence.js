var count = 0;
var fireCount = 0
var b = 0
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
                /*
                js Pollfires section
                * */
                var k = $(data).find('globalStates').find("localStates")
                var l = $(data).find('globalStates localStates').find("fire");
                var location="";
                for(var i = 0 ; i< l.length; i ++)
                {
                    if($(l[i]).text() == "true" && b == 0)
                    {
                        location = $(k[i]).find("location").text();
                        fireCount += 1;
                        console.log("Fire at  : "+ location)
                        alert("Fire at  : "+ location);

                    }
                }
                if(fireCount == 1 && b == 0)
                {
                    var retval = confirm("If verified as real fire,Press Ok to actuate")
                    if(retval == true)
                    {
                        setActState();
                        console.log("Successfully sent message : "+"true");
                    }
                    else
                    {
                        resetActState();
                        console.log("Successfully sent message : "+"false");
                    }
                    b += 1;
                }
                /*
                * */


                var card_ids = ['card1','card2','card3'];
                var act_card = ['act-card1','act-card2','act-card3'];

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


                        /*console.log(location);
                        console.log(temp);
                        console.log(flash);
                        console.log("Count : "+count);

                        console.log(card_ids[i]);*/
                        $('#'+card_ids[i]).find('#statename').text("Environment Measurements");
                        $('#'+card_ids[i]).find('#loc_id').text(location);
                        $('#'+card_ids[i]).find('#temp').text(temp);
                        $('#'+card_ids[i]).find('#hum').text(hum);
                        $('#'+card_ids[i]).find('#flash').text(flash);

                        $('#'+act_card[i]).find('#statename').text("Actuator States and Evaluation");
                        $('#'+act_card[i]).find('#loc_id').text(location);
                        $('#'+act_card[i]).find('#alarm').text(actState);
                        $('#'+act_card[i]).find('#isfire').text(isFire);



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
        timer = setTimeout(getGLobalStates,500);
    }
);
