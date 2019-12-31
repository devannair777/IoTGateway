var count = 0;
var b = 0;
function pollFire() {
    $.ajax(
        {
            url : "http://localhost:8080/data/globalStates",
            type: "GET",
            contentType: "application/xml",
            dataType: "xml",
            success:function (data)
            {
                var k = $(data).find('globalStates').find("localStates")
                var l = $(data).find('globalStates localStates').find("fire");
                /*console.log(l[0])
                console.log(l[1])
                console.log(l[2])
                console.log("length of l : " +l.length);*
                var status = $(l[0]).text();*/

                var location="";
                for(var i = 0 ; i< l.length; i ++)
                {
                    if($(l[i]).text() == "true" && b == 0)
                    {
                        location = $(k[i]).find("location").text();
                        count += 1;
                        alert("Fire at  : "+ location);

                    }
                }
                if(count == 1 && b == 0)
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

            }
        }
    )
        //setTimeout(pollFire(),1500);
}


$(document).ready(function ()
    {
        pollFire()
        //timer = setTimeout(pollFire(),1500);
    }
);
