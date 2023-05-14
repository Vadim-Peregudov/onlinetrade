$(document).ready(function () {

    let footer = $('footer');

    function moveToBottom() {
        footer.css({
            position: 'fixed',
            bottom: '0',
            left: '0',
            right: '0'
        });
    }

    function attachToPage() {
        footer.css('position', 'relative');
    }

    if ($(document).height() <= $(window).height()) {
        moveToBottom();
    }

    $(window).resize(function () {
        if ($(document).height() > $(window).height()) {
            attachToPage();
        } else {
            moveToBottom();
        }
    });


    if (JSON.parse(localStorage.getItem('basket')) != null) {
        $.ajax({
            type: 'POST',
            url: "/basket/product",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: localStorage.getItem('basket'),
            success: function (response) {
                console.log(response);
      //        let products = JSON.parse(response);
      //        console.log(products);
                createBasket(response);
            },
        });
    }
})

function createBasket(basket) {
    let tableBody = $('.basket-product');
    tableBody.empty();

    Object.entries(basket).forEach(([key, value]) => {
       // const product = JSON.parse(key);
/*
        let tr = $('<tr>');

        let td = $('<td>');
        let a = $('<a>', {href: `product/${product.id}`, text: product.name});

        td.append(a);
        tr.append(td);
*/
        console.log(key.id + "= id");   // выведет ключ
        console.log(value); // выведет значение
    });
}
