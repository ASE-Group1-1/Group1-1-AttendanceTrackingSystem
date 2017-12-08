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
        $("#group").append('<option class="group-option" value="' + $(this).find("id").text() + '">' +
            $(this).find("groupNumber").text() +
            '</option>');
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