$(document).ready(function () {

    $.ajax({
        type: 'GET',
        url: "catalog/category",
        success: function (data) {
            let categorySelect = $('#category-select');
            let categorySelectVal = categorySelect.val();

            categorySelect.empty();

            data.forEach(function (option) {
                $('#category-select').append($('<option>', {value: option.name, text: option.name}))
            })

            categorySelect.val(categorySelectVal);
        },
        error: function () {
            alert("Ошибка сервера")
        }
    })
        .then(function () {
            $.ajax({
                type: 'POST',
                url: "catalog/model",
                data: {'category': $('#category-select').val()},
                success: function (data) {
                    let modelSelect = $('#model-select');
                    let modelSelectVal = modelSelect.val();

                    modelSelect.empty();

                    data.forEach(function (option) {
                        modelSelect.append($('<option>', {value: option, text: option}))
                    })

                    modelSelect.val(modelSelectVal);
                }
            });
        })
        .then(updateFormSelects());


    $('#category-select').change(function () {
        let categoryVal = $('#category-select').val();
        $.ajax({
            type: 'POST',
            url: "catalog/model",
            data: {'category': categoryVal},
            success: function (data) {
                let modelSelect = $('#model-select');

                modelSelect.empty();

                data.forEach(function (option) {
                    modelSelect.append($('<option>', {value: option, text: option}))
                });

                modelSelect.val(modelSelect.find('option:first').val());
                modelSelect.trigger('change');
            }
        })
    });

    $('#model-select').change(function () {
        updateFormSelects();
    });

    $('.catalog').on('updateForm', function () {
        let categoryName = $('#category-select').val();
        let modelName = $('#model-select').val();
        let sort = $('#sort').val();
        let page = getPage();

        let filterParam = [];

        $('.param select').each(function () {
            let label = $(this).prev('label').text(); // получаем текст label
            let value = $(this).val(); // получаем значение select
            filterParam.push({'name': label, 'value': value});
        });

        let filterRequestDto = {
            'categoryName': categoryName,
            'modelName': modelName,
            'filterParam': filterParam,
            'page': page,
            'sort': sort,
        };
        console.log(filterRequestDto);

        $('.product-cards').append('<div class="loading-overlay"><div class="loading-spinner"></div></div>');

        $.ajax({
            type: 'POST',
            url: "catalog/product/filter",
            data: JSON.stringify(filterRequestDto),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (filterResponseDto) {
                console.log(filterResponseDto);

                updateProducts(filterResponseDto);
                updateNavItems(filterResponseDto);

                $('.loading-overlay').remove();
            },
        });

    });

    $('#sort').change(function () {
        $('.catalog').trigger('updateForm');
    });


});

function updateNavItems(filterResponseDto) {
    let page = filterResponseDto.page;
    let totalPage = filterResponseDto.totalPage;
    let pagination = $('.pagination');

    pagination.empty();

    for (let i = 1; i <= totalPage; i++) {
        let li = $('<li>', {class: 'page-item'});
        let button = $('<button>', {class: 'btn btn-link page-link', type: 'button', value: i, text: i});

        if (i === page) {
            li.addClass('active');
        }

        button.on('click', function () {
            $(".pagination .page-item").removeClass("active");
            li.addClass('active');

            $('.catalog').trigger('updateForm');
        });

        li.append(button);
        pagination.append(li);
    }
}

function getPage() {
    let numberPage = $('li.page-item.active button').val();

    if (numberPage === '') {
        return 1;
    }

    return numberPage;
}

function settingSelectValues() {
    $('form.catalog-form div.param select').each(function () {
        $(this).val($(this).find('option:first').val());
    })
}

function updateFormSelects() {
    return $.ajax({
        type: "POST",
        url: "/catalog/filter",
        data: {
            "category": $('#category-select').val(),
            "model": $('#model-select').val(),
        }, success: function (data) {
            createForm(data);

            settingSelectValues();

            $('.catalog').trigger('updateForm');
        },
        error: function () {
            alert("Ошибка сервера")
        }
    });
}

function updateModel() {
    return $.ajax({
        type: 'POST',
        url: "catalog/model",
        data: {'category': $('#category-select').val()},
        success: function (data) {
            let modelSelect = $('#model-select');
            let modelSelectVal = modelSelect.val();

            modelSelect.empty();

            data.forEach(function (option) {
                modelSelect.append($('<option>', {value: option, text: option}))
            })

            modelSelect.val(modelSelectVal);
        }
    });
}

function createForm(data) {
    $('div.param').remove();

    let form = $('form.catalog-form');

    for (let key in data) {
        let div = $('<div>', {class: 'mb-3 param'})
        let label = $('<label>', {class: 'form-label', text: key});
        let select = $('<select>', {class: 'form-select'})
        let values = data[key];

        for (let i = 0; i < values.length; i++) {
            let option = $('<option>', {value: values[i], text: values[i]});
            select.append(option);
        }

        select.change(function () {
            $('.catalog').trigger('updateForm');
        });

        div.append(label).append(select);
        form.append(div);
    }


}

function updateProducts(filterResponseDto) {
    // Очищаем текущее содержимое контейнера
    $('.product-cards').empty();
    let products = filterResponseDto.productList;
    // Создаем новые элементы карточек товаров и добавляем их в контейнер
    products.forEach(function (product) {
        let col = $('<div>', {class: 'col'});
        let card = $('<div>', {class: 'card product h-100'});

        let a = $('<a>', {href: `/product/${product.id}`});

        let images = () => {
            if (product.productImages === undefined) {
                return product.title;
            } else {
                return product.productImages[0].path;
            }
        }

        let img = $('<img>', {src: images, class: 'card-img-top', alt: product.title});
        let cardBody = $('<div>', {class: 'card-body'});

        let title = $('<h5>', {class: 'card-title', text: product.fullName});

        let button = $(`<button>`, {class: `btn btn-primary add-to-cart`, text: `Добавить в корзину`});

        button.on('click', function () {
            addProductBasket(product.id);
        });

        let price = $('<p>', {
            class: 'card-text',
            html: '<small class="text-muted">Цена: ' + numberFormatting(product.price) + '₽</small>'
        });

        a.append(img);
        cardBody.append(title).append(price).append(button);
        card.append(a).append(cardBody);
        col.append(card);

        $('.product-cards').append(col);
    });
}

function numberFormatting(number) {
    const priceStr = number.toString();
    const lastTwoDigits = priceStr.slice(-2);
    const firstDigits = priceStr.slice(0, -2);

    let formattedPrice = "";
    for (let i = 0; i < firstDigits.length; i++) {
        formattedPrice += firstDigits[i];
        if ((firstDigits.length - i - 1) % 3 === 0 && i !== firstDigits.length - 1) {
            formattedPrice += " ";
        }
    }

    formattedPrice += "." + lastTwoDigits;

    return formattedPrice;
}

function addProductBasket(idProduct) {
    let basket = JSON.parse(localStorage.getItem('basket'));

    if (!basket) {
        basket = {};
    }

    if (basket.hasOwnProperty(idProduct)) {
        let value = basket[idProduct] + 1;
        basket[idProduct] = value;
    } else {
        basket[idProduct] = 1;
    }

    localStorage.setItem('basket', JSON.stringify(basket));

    $('.navbar').trigger('updateCountProduct');
}