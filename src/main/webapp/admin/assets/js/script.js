$("body").on("click", "#exportPDF", function () {
    html2canvas($('#example2')[0], {
        onrendered: function (canvas) {
            var data = canvas.toDataURL();
            var docDefinition = {
                content: [{
                    image: data,
                    width: 500,
                }]
            };
            pdfMake.createPdf(docDefinition).download("products.pdf");
        }
    });
});
function exportTableToExcel(tableID, filename = '') {
    var downloadLink;
    var dataType = 'application/vnd.ms-excel';
    var tableSelect = document.getElementById(tableID);
    var tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');

    // Specify file name
    filename = filename ? filename + '.xlsx' : 'excel_data.xlsx';

    // Create download link element
    downloadLink = document.createElement("a");

    document.body.appendChild(downloadLink);

    if (navigator.msSaveOrOpenBlob) {
        var blob = new Blob(['\ufeff', tableHTML], {
            type: dataType
        });
        navigator.msSaveOrOpenBlob(blob, filename);
    } else {
        // Create a link to the file
        downloadLink.href = 'data:' + dataType + ', ' + tableHTML;

        // Setting the file name
        downloadLink.download = filename;

        //triggering the function
        downloadLink.click();
    }
}
// Get the modal
var modal = document.getElementById('id01');

// When the user clicks anywhere outside the modal, close it
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
// // Get the modal
var modal2 = document.getElementById('id02');

// When the user clicks anywhere outside the modal, close it
window.onclick = function (event) {
    if (event.target == modal2) {
        modal2.style.display = "none";
    }
}
$(document).ready(function () {
    $(".text-danger").click(function () {
        var href = $(this).attr("href")
        $("#deleteval").attr("value", href)
        document.getElementById('id01').style.display = 'block'
    });
    $("#linkupdate").click(function () {
        var href = $(this).attr("href")
        $("#updateval").attr("value", href)
        document.getElementById('id02').style.display = 'block'
    });
})
$(document).ready(function () {
    $("#btnDelete").click(function () {
        var id = $("#deleteval").attr("value");
        $.ajax({
            type: "GET",
            data: {
                action: "delete",
                id: id
            },
            url: "/project_BookStore/ListOrderAd"
        })
        $("#" + id).remove();
    });
    $("#btnUpdate").click(function () {
        var id = $("#updateval").attr("value");
        $.ajax({
            type: "GET",
            data: {
                action: "update",
                id: id
            },
            url: "/project_BookStore/ListOrderAd"
        })
        $("#" + id).children("#rowupdate").children().remove();
        $("#" + id).children("#orderstatus").text("Đã xử lý");
    });
})
