$(document).ready(function() {
    updateDate();
    setInterval(updateDate, 1000);
    updateInfos();
    //setInterval(updateInfos, 1000);

    $('input[name=slider]').each(function (index) {
        var id = $(this).val();
        $.ajax({
            type: 'GET',
            url: 'api/slider/' + id,
            success: function(msg) {
                console.log(msg);
            },
            error: function(msg) {

            }

        });
        $(this).slider({ value: 10 });
    });

});

function updateDate() {
    var m_names = new Array("January", "February", "March",
        "April", "May", "June", "July", "August", "September",
        "October", "November", "December");

    var d = new Date();
    var curr_date = d.getDate();
    var sup = "";
    if (curr_date == 1 || curr_date == 21 || curr_date ==31)
    {
        sup = "st";
    }
    else if (curr_date == 2 || curr_date == 22)
    {
        sup = "nd";
    }
    else if (curr_date == 3 || curr_date == 23)
    {
        sup = "rd";
    }
    else
    {
        sup = "th";
    }

    var curr_month = d.getMonth();
    var curr_year = d.getFullYear();

    $('span[name=today]').html(curr_date + "<SUP>" + sup + "</SUP> "
        + m_names[curr_month] + " " + curr_year);

    var curr_hours = d.getHours();
    var curr_minutes = d.getMinutes();
    var curr_seconds = d.getSeconds();

    if (curr_hours < 10)
        curr_hours = '0' + curr_hours;

    if (curr_minutes < 10)
        curr_minutes = '0' + curr_minutes;

    if (curr_seconds < 10)
        curr_seconds = '0' + curr_seconds;

    $('span[name=time]').html(curr_hours + ':' + curr_minutes + ':' + curr_seconds );
}

function updateInfos() {
    $('input[name=info]').each(function(index) {
        var id = $(this).val();
        //alert(id);
        $.ajax({
            type: 'GET',
            url: '/api/info/'+id,
            success: function (msg) {
                console.log(msg);
                $('span[name=' + id + ']').html(msg.value);
            },
            error: function (msg) {
                $('span[name=' + id + ']').html('null');
            }
        });
    });
}

function switchValue(id) {
    $.ajax({
        type: 'GET',
        url:  '/api/switch/'+id,
        success: function(msg) {
            var turnon = $('input[name=turnon]').val();
            var turnoff = $('input[name=turnoff]').val();

            var on = $('input[name=on]').val();
            var off = $('input[name=off]').val();

            if (msg == 'true') {
                $('span[name=switch' + id + ']').html(on);
                $('span[name=switch' + id + ']').attr('class','label label-success');
                $('button[name=switchBtn' + id + ']').html(turnoff);
            } else {
                $('span[name=switch' + id + ']').html(off);
                $('span[name=switch' + id + ']').attr('class','label label-danger');
                $('button[name=switchBtn' + id + ']').html(turnon);
            }
        },
        error: function(msg) {

        }
    });
}