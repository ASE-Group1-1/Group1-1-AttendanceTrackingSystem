var limitCount;
var setLimit = false;
$(document).ready(getAttendances());

$(document).on('change', '#group', function() {
    if (this.value != "all") {
        getAttendances();
    } else {
        getAttendances();
    }
});

$(document).on('change', '#limit', function() {
    limitCount = parseInt(this.value);
    if (this.value != "all") {
        setLimit = true;
        getAttendances();
    } else {
        setLimit = false;
        getAttendances();
    }
});

function getAttendances() {
    $("#container").empty();
    if (typeof group !== 'undefined' && group.value != "all") {
        $.ajax({
            type: "GET",
            dataType: "xml",
            url: "/rest/attendance/list/groups/" + group.value,
            success: xmlParser
        });
    } else {
        $.ajax({
            type: "GET",
            dataType: "xml",
            url: "/rest/attendance/list",
            success: xmlParser
        });
    }
};

function xmlParser(xml) {
    $(xml).find("attendance").each(function(i) {
        if (i == limitCount && setLimit == true) {
            return false;
        }
        $("#container").append('<div class="attendance"><p>Student: ' +
            $(this).find("studentId").text() +
            '<br> Group: ' +
            $(this).find("groupId").text() +
            '<br> Week: ' +
            $(this).find("weekId").text() +
            '<br> Presented: ' +
            $(this).find("presented").text() +
            '</p></div>');
        $(".attendance").fadeIn(50);
    });
};