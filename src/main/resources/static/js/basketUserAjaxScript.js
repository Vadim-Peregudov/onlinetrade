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
                createBasket(response);
            },
        });
    }
})

function createBasket(basket) {
    let tableBody = $('.basket-product');
    tableBody.empty();

    $.each(basket, function (key, value) {
        let product = JSON.parse(key);
        console.log(product);

        let tr = $('<tr>');
        let tdName = $('<td>');
        let a = $('<a>', {href: `/product/${product.id}`, text: product.fullName});
        let tdImage = $('<td>');
        let img = $('<img>', {src: getFirstImage(product.productImages), style: 'width: 50px'});
        let tdInput = $('<td>');
        let input = $('<input>', {
            type: 'number',
            min: 1,
            'value': value,
            class: 'form-control',
            style: 'width: 60px'
        });
        let tdPrise = $('<td>', {'text': (product.price * value) / 100});
        let tdButton = $('<td>');
        let button = $('<button>', {class: 'btn btn-danger btn-sm', text: 'Удалить'});

        tdName.append(a);
        tdImage.append(img);
        tdInput.append(input);
        tdButton.append(button);

        tr.append(tdName).append(tdImage).append(tdInput).append(tdPrise).append(tdButton);

        tableBody.append(tr);

        input.change(function () {
            let newValue = $(this).val();
            tdPrise.text((product.price * newValue) / 100);

            console.log(localStorage.getItem('basket'));
        });
    });
}

function getFirstImage(images) {
    if (Array.isArray(images) && images.length > 0) {
        return images[0].path;
    } else {
        return null;
    }
}
