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
                $(data).find('globalStates localStates').each(
                    function(){
                        var location = $(this).find('location').text();
                        var temp = $(this).find('temperature').text();
                        var hum = $(this).find('humidity').text();
                        var flash = $(this).find('flash').text();

                        console.log(location);
                        console.log(temp);
                        console.log(flash);
                        console.log();

                        console.log(card_ids[i]);

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
        timer = setTimeout(getGLobalStates,500);
    }
);
