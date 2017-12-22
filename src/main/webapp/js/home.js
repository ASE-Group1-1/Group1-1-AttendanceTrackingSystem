$(document).ready(getGroups());


function getGroups() {
    $.ajax({
        type: "GET",
        dataType: "xml",
        url: "/rest/group/list",
        success: groupXmlParser
    });
};

function groupXmlParser(xml) {
    $(xml).find("group").each(function() {
        $("#select-group").append('<div class="form-check"><label class="form-check-label"><input type="radio" name="joinGroupId" id="group-option-'
            + $(this).find("groupNumber").text()
            + '" class="group-option" value="'
            + $(this).find("id").text()
            + '"> Group Number: '
            + $(this).find("groupNumber").text()
            + ', Tutor: '
            + $(this).find("instructorName").text()
            + ', Meeting Time: '
            + $(this).find("meetingTime").text()
            + '</label></div>');
        $(".group-option").fadeIn(50);
    });
};




$('#join-group-button').click( function() {
    $.ajax({
        url: '/rest/group/join',
        type: 'post',
        dataType: 'text',
        data: $('#join-group-form').serialize(),
        success: function(data) {
                    location.reload();
                 }
    });
});