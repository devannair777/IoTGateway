var count = 0;
var fireCount = 0;
var smokeCount = 0;
var b = 0;
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
                        //console.log("Fire at  : "+ location)
                        alert("Fire at  : "+ location);

                    }
                }
                var retval;
                if(fireCount == 1 && b == 0)
                {
                    retval = confirm("If verified as real fire,Press Ok to actuate")
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
                else if(fireCount > 1)
                {
                    setActState();
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
                        var smoke = $(this).find('smoke').text();
                        var flash = $(this).find('flash').text();
                        var actState =  $(this).find("actuatorState").text();
                        var isFire = $(this).find("fire").text();
                        var smokeAlert = $(this).find("smokeAlert").text();

                        $('#'+card_ids[i]).find('#statename').text("Environment Measurements");
                        $('#'+card_ids[i]).find('#loc_id').text(location);
                        $('#'+card_ids[i]).find('#temp').text(temp);
                        $('#'+card_ids[i]).find('#flash').text(flash);

                        $('#'+act_card[i]).find('#statename').text("Actuator States and Evaluation");
                        $('#'+act_card[i]).find('#loc_id').text(location);


                        if(isFire == "true")
                        {
                            $('#'+card_ids[i]).find(".card-header").css("backgroundColor","#dc3545");
                            $('#'+act_card[i]).find('#isfire').text("Yes");
                            $('#'+act_card[i]).find('#isfire').css("color","red");

                            if(smokeAlert == "true")
                            {
                                $('#'+card_ids[i]).find('#smoke').text("Intense");
                                $('#'+act_card[i]).find("#smoke").css("color","red");
                                $('#'+act_card[i]).find('#smoke').text("Yes");
                            }
                            else  if(smokeAlert == "false")
                            {
                                $('#'+card_ids[i]).find('#smoke').text("Minimal");
                                $('#'+act_card[i]).find("#smoke").css("color","black");
                                $('#'+act_card[i]).find('#smoke').text("No");
                            }

                        }
                        else
                        {
                            $('#'+card_ids[i]).find(".card-header").css("backgroundColor","#f8f9fa");
                            $('#'+act_card[i]).find('#isfire').text("No");
                            $('#'+act_card[i]).find('#isfire').css("color","black");

                            if(smokeAlert == "true")
                            {
                                $('#'+card_ids[i]).find('#smoke').text("Intense");
                                $('#'+card_ids[i]).find(".card-header").css("backgroundColor","#ffc107");
                                $('#'+act_card[i]).find("#smoke").css("color","red");
                                $('#'+act_card[i]).find('#smoke').text("Yes");
                            }
                            else if(smokeAlert == "false")
                            {
                                $('#'+card_ids[i]).find('#smoke').text("Minimal");
                                $('#'+card_ids[i]).find(".card-header").css("backgroundColor","#f8f9fa");
                                $('#'+act_card[i]).find("#smoke").css("color","black");
                                $('#'+act_card[i]).find('#smoke').text("No");
                            }
                        }

                        if(actState == "true")
                        {
                            $('#'+act_card[i]).find('#alarm').text("Active");
                            $('#'+act_card[i]).find('#emDoor').text("Enabled");
                        }
                        else
                        {
                            $('#'+act_card[i]).find('#alarm').text("Inactive");
                            $('#'+act_card[i]).find('#emDoor').text("Disabled");
                        }
                        i = i + 1;
                    }
                )
            }
        }
    )
    //setTimeout(getGLobalStates,500)
}

$(document).ready(function ()
    {
        count = 0;
        //timer = setTimeout(getGLobalStates,500);
        timer = setInterval(getGLobalStates,500)
    }
);